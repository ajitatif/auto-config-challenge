package org.turkisi.config.client;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
public class ConfigurationClientTest {

//    private volatile static boolean serviceInitialized = false;
//
//    @BeforeClass
//    public static void startConfigurationService() {
//
//        Thread serviceStarterThread = new Thread(() -> ConfigurationServiceStarter.main(new String[0], (ServiceStateListener) () -> {
//            serviceInitialized = true;
//        }));
//
//        serviceStarterThread.start();
//    }

    @Test
    public void testConfigurationClient() throws Exception {

//        while (!serviceInitialized) {
//            Thread.sleep(500);
//
//        }

        ConfigurationReader clientA = new ConfigurationReader("SERVICE-A", "http://localhost:8080", 5000);
        assertThat(clientA.getValue("SiteName"), equalTo("trendyol.com"));
        assertNull(clientA.getValue("MaxItemCount"));

        ConfigurationReader clientB = new ConfigurationReader("SERVICE-B", "http://localhost:8080", 5000);
        assertTrue(clientB.getValue("IsBasketEnabled"));
    }
}
