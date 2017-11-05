package org.turkisi.config.test.unit;

import org.turkisi.config.domain.ConfigurationItemType;
import org.turkisi.config.domain.ConfigurationModel;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
class TestHelper {

    static ConfigurationModel createModel(long id, String serviceName, String name, ConfigurationItemType type, boolean isActive, String value) {
        ConfigurationModel model = new ConfigurationModel();
        model.setId(id);
        model.setActive(true);
        model.setApplicationName(serviceName);
        model.setName(name);
        model.setType(type);
        model.setActive(isActive);
        model.setValue(value);
        return model;
    }
}
