package org.turkisi.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@SpringBootApplication
public class ConfigurationServiceStarter {

    public static void main(String[] args) {
        SpringApplication.run((ConfigurationServiceStarter.class), args);
    }
}
