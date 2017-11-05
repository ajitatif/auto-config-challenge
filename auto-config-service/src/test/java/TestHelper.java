import org.turkisi.config.ConfigurationItemType;
import org.turkisi.config.ConfigurationModel;

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
