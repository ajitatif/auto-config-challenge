package org.turkisi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@ConfigurationProperties("service.security")
public class ConfigurationServiceSecurityParams {

    private Long sessionTimeoutSeconds;


    public Long getSessionTimeoutSeconds() {
        return sessionTimeoutSeconds;
    }

    public void setSessionTimeoutSeconds(Long sessionTimeoutSeconds) {
        this.sessionTimeoutSeconds = sessionTimeoutSeconds;
    }
}
