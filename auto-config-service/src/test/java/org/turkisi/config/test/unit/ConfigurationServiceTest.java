package org.turkisi.config.test.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.turkisi.config.domain.ConfigurationItemType;
import org.turkisi.config.domain.ConfigurationModel;
import org.turkisi.config.repository.ConfigurationModelRepository;
import org.turkisi.config.service.ConfigurationAccessService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;

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

        Mockito.when(configurationModelRepository.findByApplicationName(Mockito.any())).then(invocation -> {
            String serviceName = invocation.getArgument(0);

            return mockList.stream().filter(configurationModel ->
                    configurationModel.getApplicationName().equals(serviceName) &&
                            configurationModel.isActive()).collect(Collectors.toList());
        });
    }

    @Test
    public void testConfigurationServiceGetValue() throws Exception {
        assertNotNull(configurationModelRepository);
        ConfigurationAccessService configurationAccessService = new ConfigurationAccessService(configurationModelRepository);
        List<ConfigurationModel> serviceAConfiguration = configurationAccessService.getValuesForService("SERVICE-A");
        assertThat(serviceAConfiguration.size(), equalTo(1));
        assertThat(serviceAConfiguration.stream().
                filter(configurationModel -> configurationModel.getName().equals("SiteName"))
                .findAny().get().getValue(),
            equalTo("trendyol.com"));
        assertThat(configurationAccessService.getValuesForService("SERVICE-B").get(0).getValue(), equalTo("true"));
    }
}
