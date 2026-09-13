package org.reflector.exception;

/**
 * Thrown when a field cannot be read or written reflectively.
 */
public class FieldAccessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FieldAccessException(final String message) {
        super(message);
    }

    public FieldAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
