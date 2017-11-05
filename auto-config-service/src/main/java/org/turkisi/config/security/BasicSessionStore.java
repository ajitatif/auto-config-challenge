package org.turkisi.config.security;

import org.springframework.stereotype.Component;
import org.turkisi.config.ConfigurationServiceSecurityParams;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Component("basicSessionStore")
public class BasicSessionStore implements SessionStore {

    private Long sessionTimeoutSeconds;

    private HashMap<String, SessionState> sessionMap = new HashMap<>();

    public BasicSessionStore(ConfigurationServiceSecurityParams configurationServiceSecurityParams) {
        this.sessionTimeoutSeconds = configurationServiceSecurityParams.getSessionTimeoutSeconds();
    }

    @Override
    public void addSession(String sessionId, String applicationName) {

        sessionMap.put(sessionId, SessionState.create(sessionId, applicationName, LocalDateTime.now().plus(sessionTimeoutSeconds, ChronoUnit.SECONDS)));
    }

    @Override
    public SessionState getSessionState(String sessionId) {
        SessionState sessionState = sessionMap.get(sessionId);
        if (SessionState.getStatus(sessionState).equals(SessionValidityStatus.TIMEOUT)) {
            sessionMap.remove(sessionId);
        }
        return sessionState;
    }
}
