package org.turkisi.config.service;

import org.springframework.stereotype.Component;
import org.turkisi.config.domain.ConfigurationModel;
import org.turkisi.config.repository.ConfigurationModelRepository;

import java.util.List;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Component
public class ConfigurationAccessService {

    private ConfigurationModelRepository configurationModelRepository;

    public ConfigurationAccessService(ConfigurationModelRepository configurationModelRepository) {
        this.configurationModelRepository = configurationModelRepository;
    }

    public List<ConfigurationModel> getValuesForService(String serviceName) {

        return configurationModelRepository.findByApplicationName(serviceName);
    }
}
