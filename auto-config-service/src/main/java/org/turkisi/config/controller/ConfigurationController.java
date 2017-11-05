package org.turkisi.config.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.turkisi.config.error.KeyNotFoundException;
import org.turkisi.config.error.SessionExpiredException;
import org.turkisi.config.error.SessionNotFoundException;
import org.turkisi.config.security.SessionState;
import org.turkisi.config.security.SessionStore;
import org.turkisi.config.security.SessionValidityStatus;
import org.turkisi.config.service.ConfigurationAccessService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Controller
@RequestMapping("/")
public class ConfigurationController {

    private ConfigurationAccessService configurationAccessService;
    private SessionStore sessionStore;

    public ConfigurationController(ConfigurationAccessService configurationAccessService, SessionStore sessionStore) {
        this.configurationAccessService = configurationAccessService;
        this.sessionStore = sessionStore;
    }

    @RequestMapping(value = "/{applicationName}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> initializeSession(HttpServletRequest request, @PathVariable("applicationName") String applicationName) {
        String sessionId = request.getSession().getId();
        sessionStore.addSession(sessionId, applicationName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{applicationName}/{key}", method = RequestMethod.GET)
    public @ResponseBody <T> T getValue(HttpServletRequest request,
                   @PathVariable("applicationName") String applicationName,
                   @PathVariable("key") String key) throws KeyNotFoundException {

        SessionState sessionState = sessionStore.getSessionState(request.getSession().getId());
        if (sessionState == null || !sessionState.getApplicationName().equalsIgnoreCase(applicationName)) {
            throw new SessionNotFoundException("Session not found");
        }

        if (SessionState.getStatus(sessionState).equals(SessionValidityStatus.TIMEOUT)) {
            throw new SessionExpiredException("Session expired");
        }

        T value = configurationAccessService.getValue(applicationName, key);
        if (value == null) {
            throw new KeyNotFoundException();
        }
        return value;
    }
}
