package org.turkisi.config.test.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.turkisi.config.service.ConfigurationAccessService;
import org.turkisi.config.domain.ConfigurationItemType;
import org.turkisi.config.domain.ConfigurationModel;
import org.turkisi.config.repository.ConfigurationModelRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
public class ConfigurationServiceTest {

    private ConfigurationModelRepository configurationModelRepository = null;

    @Before
    public void setUp() throws Exception {

        configurationModelRepository = Mockito.mock(ConfigurationModelRepository.class);
        List<ConfigurationModel> mockList = new ArrayList<>();
        mockList.add(TestHelper.createModel(1L, "SERVICE-A", "SiteName", ConfigurationItemType.STRING, true, "trendyol.com"));
        mockList.add(TestHelper.createModel(2L, "SERVICE-B", "IsBasketEnabled", ConfigurationItemType.BOOLEAN, true, "true"));
        mockList.add(TestHelper.createModel(3L, "SERVICE-A", "MaxItemCount", ConfigurationItemType.INT, false, "50"));

        Mockito.when(configurationModelRepository.findByApplicationNameAndName(Mockito.any(), Mockito.any())).then(invocation -> {
            String serviceName = invocation.getArgument(0);
            String name = invocation.getArgument(1);

            return mockList.stream().filter(configurationModel ->
                    configurationModel.getApplicationName().equals(serviceName) &&
                            configurationModel.getName().equals(name) &&
                            configurationModel.isActive())
                    .findAny().orElse(null);
        });
    }

    @Test
    public void testConfigurationServiceGetValue() throws Exception {
        assertNotNull(configurationModelRepository);
        ConfigurationAccessService configurationAccessService = new ConfigurationAccessService(configurationModelRepository);
        assertThat(configurationAccessService.getValue("SERVICE-A", "SiteName"), equalTo("trendyol.com"));
        assertThat(configurationAccessService.getValue("SERVICE-B", "IsBasketEnabled"), equalTo(true));
        assertNull(configurationAccessService.getValue("SERVICE-A", "MaxItemCount"));
    }
}
