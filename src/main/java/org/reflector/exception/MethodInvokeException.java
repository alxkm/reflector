package org.reflector.exception;

/**
 * Thrown when a method cannot be resolved or invoked reflectively.
 */
public class MethodInvokeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MethodInvokeException(final String message) {
        super(message);
    }

    public MethodInvokeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
