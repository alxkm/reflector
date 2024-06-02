package org.reflector.exception;

public class FieldAccessException extends RuntimeException {
    public FieldAccessException(String cause) {
        super(cause);
    }
    public FieldAccessException(String cause, ReflectiveOperationException e) {
        super(cause, e);
    }
}
