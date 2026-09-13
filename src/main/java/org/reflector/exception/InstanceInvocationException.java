package org.reflector.exception;

/**
 * Thrown when an instance cannot be created reflectively.
 */
public class InstanceInvocationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InstanceInvocationException(final String message) {
        super(message);
    }

    public InstanceInvocationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
