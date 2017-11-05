package org.turkisi.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.turkisi.config.event.ServiceStateListener;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@SpringBootApplication
public class ConfigurationServiceStarter {

    public static void main(String[] args, ServiceStateListener... listeners) {
        SpringApplication.run((ConfigurationServiceStarter.class), args).addApplicationListener(event -> {
            if (event instanceof ContextRefreshedEvent) {
                ConfigurationServiceStarter.onApplicationInitialized(listeners);
            }
        });
    }

    private static void onApplicationInitialized(ServiceStateListener[] listeners) {
        if (listeners != null && listeners.length > 0) {
            for (ServiceStateListener listener : listeners) {
                listener.onServiceInitialized();
            }
        }
    }
}
