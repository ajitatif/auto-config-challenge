package org.turkisi.config.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SessionNotFoundException extends SecurityException {

    public SessionNotFoundException() {
    }

    public SessionNotFoundException(String s) {
        super(s);
    }

    public SessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionNotFoundException(Throwable cause) {
        super(cause);
    }
}
