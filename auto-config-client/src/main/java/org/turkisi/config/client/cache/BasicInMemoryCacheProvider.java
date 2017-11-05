package org.turkisi.config.client.cache;

import org.springframework.stereotype.Component;
import org.turkisi.config.client.ConfigurationResponseModel;

import java.util.HashMap;
import java.util.List;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Component
public class BasicInMemoryCacheProvider implements ConfigurationCacheProvider {

    private HashMap<String, ConfigurationResponseModel> params = new HashMap<>();

    @Override
    public void clear() {
        params.clear();
    }

    @Override
    public void put(List<ConfigurationResponseModel> list) {
        list.forEach(model -> params.put(model.getName(), model));
    }

    @Override
    public ConfigurationResponseModel get(String key) {
        return params.get(key);
    }
}
