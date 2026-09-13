package org.reflector.util;

/**
 * Shared literals used while resolving class files and package names.
 */
public final class ReflectionConstant {

    /** File extension of a compiled class file. */
    public static final String CLASS = ".class";

    /** Package separator as a string. */
    public static final String DOT = ".";

    /** Package separator as a character. */
    public static final char DOT_SYMBOL = '.';

    /** Path separator used inside classpath resources. */
    public static final char SLASH = '/';

    /** Length of {@link #CLASS}, used to strip the extension from a file name. */
    public static final int CLASS_NAME_CONSTANT = CLASS.length();

    private ReflectionConstant() {
    }
}
