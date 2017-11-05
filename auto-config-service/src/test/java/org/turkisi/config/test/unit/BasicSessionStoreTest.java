package org.turkisi.config.test.unit;

import org.junit.Test;
import org.turkisi.config.ConfigurationServiceSecurityParams;
import org.turkisi.config.security.BasicSessionStore;
import org.turkisi.config.security.SessionState;
import org.turkisi.config.security.SessionValidityStatus;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
public class BasicSessionStoreTest {

    @Test
    public void testSessionValidity() throws Exception {

        BasicSessionStore hourLongSessionStore = new BasicSessionStore(getConfigurationServiceSecurityParams(3600L));
        hourLongSessionStore.addSession("ABCDEF", "ASD");
        assertThat(SessionState.getStatus(hourLongSessionStore.getSessionState("ABCDEF")), equalTo(SessionValidityStatus.VALID));
        assertThat(SessionState.getStatus(hourLongSessionStore.getSessionState("QWERTY")), equalTo(SessionValidityStatus.INVALID));
    }

    private ConfigurationServiceSecurityParams getConfigurationServiceSecurityParams(long sessionTimeout) {
        ConfigurationServiceSecurityParams configurationServiceSecurityParams = new ConfigurationServiceSecurityParams();
        configurationServiceSecurityParams.setSessionTimeoutSeconds(sessionTimeout);
        return configurationServiceSecurityParams;
    }

    @Test
    public void testSessionTimeout() throws Exception {

        BasicSessionStore secondLongSessionStore = new BasicSessionStore(getConfigurationServiceSecurityParams(1L));
        secondLongSessionStore.addSession("ABCDEF", "ASD");
        assertThat(SessionState.getStatus(secondLongSessionStore.getSessionState("ABCDEF")), equalTo(SessionValidityStatus.VALID));
        Thread.sleep(1500);
        assertThat(SessionState.getStatus(secondLongSessionStore.getSessionState("ABCDEF")), equalTo(SessionValidityStatus.TIMEOUT));
        // after first "timeout" call, the session should be removed from the service,
        // hence should return "invalid" as if it never existed
        assertThat(SessionState.getStatus(secondLongSessionStore.getSessionState("ABCDEF")), equalTo(SessionValidityStatus.INVALID));
    }

    @Test
    public void testSessionExtension() throws Exception {

        BasicSessionStore sessionStore = new BasicSessionStore(getConfigurationServiceSecurityParams(2L));
        sessionStore.addSession("ABCDEF", "ASD");
        assertThat(SessionState.getStatus(sessionStore.getSessionState("ABCDEF")), equalTo(SessionValidityStatus.VALID));
        Thread.sleep(1500);
        assertThat(SessionState.getStatus(sessionStore.getSessionState("ABCDEF")), equalTo(SessionValidityStatus.VALID));
        sessionStore.addSession("ABCDEF", "ASD");
        Thread.sleep(1000);
        // the initial session time out is exceeded, but the second .addSession should start the watch over
        assertThat(SessionState.getStatus(sessionStore.getSessionState("ABCDEF")), equalTo(SessionValidityStatus.VALID));
        Thread.sleep(1000);
        // this time the session should be timed-out
        assertThat(SessionState.getStatus(sessionStore.getSessionState("ABCDEF")), equalTo(SessionValidityStatus.TIMEOUT));
        assertThat(SessionState.getStatus(sessionStore.getSessionState("ABCDEF")), equalTo(SessionValidityStatus.INVALID));
    }
}
