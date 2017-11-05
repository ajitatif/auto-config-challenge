import org.junit.Test;
import org.turkisi.config.ConfigurationItemType;
import org.turkisi.config.ConfigurationModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
public class ConfigurationModelTest {

    @Test
    public void testEquals() throws Exception {
        ConfigurationModel modelOne = TestHelper.createModel(1L, "SERVICE-A", "SiteName", ConfigurationItemType.STRING, true, "trendyol.com");
        ConfigurationModel modelTwo = TestHelper.createModel(2L, "SERVICE-A", "SiteName", ConfigurationItemType.STRING, true, "trendyol.com");
        ConfigurationModel modelThree = TestHelper.createModel(1L, "SERVICE-B", "Site", ConfigurationItemType.INT, true, "40");
        ConfigurationModel modelFour = TestHelper.createModel(1L, "SERVICE-A", "SiteName", ConfigurationItemType.STRING, false, "trendyol.com");
        String modelFive = "what ?";

        assertThat(modelOne, not(equalTo(modelTwo)));
        assertThat(modelOne, equalTo(modelThree));
        assertThat(modelOne, equalTo(modelFour));
        assertThat(modelOne, not(equalTo(modelFive)));
    }

}
