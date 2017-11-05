package org.turkisi.config.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SessionExpiredException extends SecurityException {

    public SessionExpiredException() {
    }

    public SessionExpiredException(String s) {
        super(s);
    }

    public SessionExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionExpiredException(Throwable cause) {
        super(cause);
    }
}
