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
@SuppressWarnings("unused")
public class ConfigurationClientTest {

    @Autowired
    private ConfigurationReader clientA;

    @Autowired
    private ConfigurationReader clientB;

    @Autowired
    private ConfigurationReader clientC;

    @Test
    public void testConfigurationClient() throws Exception {

        clientA.initialize("SERVICE-A", "http://localhost:8080", 5000);
        assertThat(clientA.getValue("SiteName"), equalTo("trendyol.com"));
        assertNull(clientA.getValue("MaxItemCount"));

        clientB.initialize("SERVICE-B", "http://localhost:8080", 5000);
        assertTrue(clientB.getValue("IsBasketEnabled"));
    }

    @Test(expected = IllegalStateException.class)
    public void testUninitializedConfigurationClient() throws Exception {
        clientC.getValue("ABCD");
    }
}
