package org.turkisi.config.security;

import java.time.LocalDateTime;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
public class SessionState {

    private final String sessionId;
    private final String applicationName;
    private final LocalDateTime endTime;

    private SessionState(String sessionId, String applicationName, LocalDateTime endTime) {

        this.sessionId = sessionId;
        this.applicationName = applicationName;
        this.endTime = endTime;
    }

    public static SessionState create(String sessionId, String applicationName, LocalDateTime endTime) {
        return new SessionState(sessionId, applicationName, endTime);
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public static SessionValidityStatus getStatus(SessionState sessionState) {
        return sessionState == null ? SessionValidityStatus.INVALID :
                sessionState.getEndTime().isBefore(LocalDateTime.now()) ? SessionValidityStatus.TIMEOUT : SessionValidityStatus.VALID;
    }
}
