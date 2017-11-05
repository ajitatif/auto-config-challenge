package org.turkisi.config.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Entity
@Table(name = "configuration")
public class ConfigurationModel implements Serializable {

    private Long id;
    private String name;
    private ConfigurationItemType type;
    private String value;
    private Boolean active;
    private String applicationName;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sq_configuration_id", sequenceName = "sq_configuration_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public ConfigurationItemType getType() {
        return type;
    }

    public void setType(ConfigurationItemType type) {
        this.type = type;
    }

    @Column
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column
    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column
    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigurationModel that = (ConfigurationModel) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
