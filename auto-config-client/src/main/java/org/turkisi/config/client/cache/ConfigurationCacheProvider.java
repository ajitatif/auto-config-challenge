package org.turkisi.config.client.cache;

import org.turkisi.config.client.ConfigurationResponseModel;

import java.util.List;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
public interface ConfigurationCacheProvider {

    void clear();
    void put(List<ConfigurationResponseModel> list);
    ConfigurationResponseModel get(String key);
}
