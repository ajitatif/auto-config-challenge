package org.turkisi.config;

import org.springframework.stereotype.Component;
import org.turkisi.config.repository.ConfigurationModelRepository;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Component
public class ConfigurationAccessService {

    private ConfigurationModelRepository configurationModelRepository;

    public ConfigurationAccessService(ConfigurationModelRepository configurationModelRepository) {
        this.configurationModelRepository = configurationModelRepository;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String serviceName, String key) {

        ConfigurationModel config = configurationModelRepository.findByApplicationNameAndName(serviceName, key);
        if (config != null) {

            switch (config.getType()) {
                case INT:
                    return (T) Integer.valueOf(config.getValue());
                case STRING:
                    return (T) config.getValue();
                case BOOLEAN:
                    return (T) Boolean.valueOf(config.getValue());
                default:
                    break;
            }
        }
        return null;
    }
}
