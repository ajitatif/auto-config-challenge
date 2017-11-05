package org.turkisi.config.security;


/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
public interface SessionStore {
    void addSession(String sessionId, String applicationName);
    SessionState getSessionState(String sessionId);
}
