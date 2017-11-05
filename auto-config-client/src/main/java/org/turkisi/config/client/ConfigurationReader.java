package org.turkisi.config.client;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.turkisi.config.client.cache.ConfigurationCacheProvider;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ConfigurationReader {

    private String serviceName;
    private String configurationServiceUrl;

    private AtomicReference<String> cookie = new AtomicReference<>();
    private AtomicBoolean configurationPopulated = new AtomicBoolean(false);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ScheduledExecutorService scheduledExecutorService;

    private ConfigurationCacheProvider cacheProvider;

    public ConfigurationReader(ConfigurationCacheProvider cacheProvider) {

        this.cacheProvider = cacheProvider;
    }

    public ConfigurationReader initialize(String serviceName, String configurationServiceUrl, int refreshTimeoutMillis) {
        this.serviceName = serviceName;
        this.configurationServiceUrl = configurationServiceUrl;

        scheduledExecutorService = Executors.newScheduledThreadPool(1, r -> new Thread(r, this.serviceName + "-ConfigReaderScheduler"));
        scheduledExecutorService.scheduleAtFixedRate(this::updateKeys, 0, refreshTimeoutMillis, TimeUnit.MILLISECONDS);
        cookie.set(null);

        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String key) throws IOException {
        for (int i = 0; i < 5; i++) {
            if (!configurationPopulated.get()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.err.println("Configuration Reader getValue thread wait interrupted!");
                }
            } else {
                break;
            }
        }

        if (!configurationPopulated.get()) {
            throw new IOException("Wait for configuration population failed, check earlier log messages for details");
        }

        ConfigurationResponseModel value = cacheProvider.get(key);
        if (value != null) {
            if ("STRING".equalsIgnoreCase(value.getType())) {
                return (T) value.getValue();
            } else if ("INT".equalsIgnoreCase(value.getType())) {
                return (T) Integer.valueOf(value.getValue());
            } else if ("BOOLEAN".equalsIgnoreCase(value.getType())) {
                return (T) Boolean.valueOf(value.getValue());
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void updateKeys() {
        try {
            cacheProvider.clear();
            if (cookie.get() == null) {
                establishConnection();
            }
            HttpURLConnection serviceConnection = null;
            try {
                serviceConnection = createConnection();
                serviceConnection.setRequestMethod("GET");
                serviceConnection.setRequestProperty("cookie", cookie.get());
                serviceConnection.connect();

                int responseCode = serviceConnection.getResponseCode();
                if (responseCode == 200) {
                    try (InputStream inputStream = serviceConnection.getInputStream()) {
                        CollectionType valueType = objectMapper.getTypeFactory().constructCollectionType(List.class, ConfigurationResponseModel.class);
                        List<ConfigurationResponseModel> values = objectMapper.readValue(inputStream, valueType);
                        cacheProvider.put(values);
                    }
                    configurationPopulated.set(true);
                } else if (responseCode == 401) {
                    cookie.set(null);
                    System.err.println("Session timed out, will try again on next update");
                }
            } finally {
                if (serviceConnection != null) {
                    serviceConnection.disconnect();
                }
            }
        } catch (IOException e) {
            System.err.println("Exception while updating keys - " + e.toString());
        }
    }

    private void establishConnection() throws IOException {

        HttpURLConnection serviceConnection = null;
        try {
            serviceConnection = createConnection();
            serviceConnection.setRequestMethod("POST");
            serviceConnection.setRequestProperty("Content-Type", "application/json");
            serviceConnection.connect();
            int responseCode = serviceConnection.getResponseCode();
            if (responseCode == 200) {
                cookie.set(serviceConnection.getHeaderField("Set-Cookie").split(";")[0]);
            }
        } finally {
            if (serviceConnection != null) {
                serviceConnection.disconnect();
            }
        }
    }

    private HttpURLConnection createConnection() throws IOException {
        return (HttpURLConnection) URI.create(configurationServiceUrl + "/"+ serviceName).toURL().openConnection();
    }
}
