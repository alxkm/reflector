package org.reflector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class SecurityUtils {

    private SecurityUtils() {}

    /**
     * Sets a method to be accessible.
     *
     * @param method the method to be set accessible
     * @throws NullPointerException if the method is null
     */
    public static void setMethodAccessible(Method method) {
        if (method == null) {
            throw new NullPointerException("Method cannot be null");
        }
        method.setAccessible(true);
    }

    /**
     * Sets a constructor to be accessible.
     *
     * @param constructor the constructor to be set accessible
     * @throws NullPointerException if the constructor is null
     */
    public static void setConstructorAccessible(Constructor<?> constructor) {
        if (constructor == null) {
            throw new NullPointerException("Constructor cannot be null");
        }
        constructor.setAccessible(true);
    }
}
