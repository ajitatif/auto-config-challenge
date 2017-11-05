package org.turkisi.config.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("application-config.xml")
public class ConfigurationClientTest {

    @Autowired
    private ConfigurationReader clientA;

    @Autowired
    private ConfigurationReader clientB;

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
        clientA.initialize("SERVICE-A", "http://localhost:8080", 5000);
        assertThat(clientA.getValue("SiteName"), equalTo("trendyol.com"));
        assertNull(clientA.getValue("MaxItemCount"));

        clientB.initialize("SERVICE-B", "http://localhost:8080", 5000);
        assertTrue(clientB.getValue("IsBasketEnabled"));
    }
}
