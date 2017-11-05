package org.turkisi.config.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class KeyNotFoundException extends Exception {

    public KeyNotFoundException() {
    }

    public KeyNotFoundException(String message) {
        super(message);
    }

    public KeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyNotFoundException(Throwable cause) {
        super(cause);
    }

    public KeyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
