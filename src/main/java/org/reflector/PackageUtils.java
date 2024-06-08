package org.reflector;

import org.reflector.util.ReflectionConstant;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class PackageUtils {

    private static final ClassLoader CLASSLOADER;

    static {
        final ClassLoader threadClassLoader = Thread.currentThread().getContextClassLoader();
        CLASSLOADER = (threadClassLoader != null) ? threadClassLoader : PackageUtils.class.getClassLoader();
    }

    private PackageUtils() {}

    /**
     * Retrieves all classes within a package.
     *
     * @param packageName the name of the package
     * @return a list of classes within the specified package
     * @throws ClassNotFoundException if a class cannot be found
     * @throws IOException            if an I/O error occurs
     * @throws URISyntaxException     if a URI syntax error occurs
     */
    public static List<Class<?>> getClassesByPackage(final String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
        // Convert package name to directory path
        String path = packageName.replace(ReflectionConstant.DOT_SYMBOL, ReflectionConstant.SLASH);
        // Get resources within the package
        Enumeration<URL> resources = CLASSLOADER.getResources(path);
        List<File> directories = new ArrayList<>();
        // Store directories containing resources
        while (resources.hasMoreElements()) {
            directories.add(new File(new URI(resources.nextElement().toString()).getPath()));
        }
        // Store classes found in directories
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : directories) {
            classes.addAll(getClassesByDirectoryAndPackage(directory, packageName));
        }
        return classes;
    }

    /**
     * Retrieves all classes within a directory and its subdirectories.
     *
     * @param directory    the directory to search for classes
     * @param packageName  the name of the package
     * @return a list of classes within the specified directory and package
     * @throws ClassNotFoundException if a class cannot be found
     */
    public static List<Class<?>> getClassesByDirectoryAndPackage(final File directory, final String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(getClassesByDirectoryAndPackage(file, packageName + ReflectionConstant.DOT + file.getName()));
                } else if (file.getName().endsWith(ReflectionConstant.CLASS)) {
                    classes.add(Class.forName(packageName + ReflectionConstant.DOT + file.getName().substring(0, file.getName().length() - ReflectionConstant.CLASS_NAME_CONSTANT)));
                }
            }
        }
        return classes;
    }

    /**
     * Retrieves all classes within a package that are annotated with a specific annotation.
     *
     * @param packageName the name of the package
     * @param annotation  the annotation to filter classes by
     * @return a list of classes within the specified package that are annotated with the specified annotation
     * @throws IOException            if an I/O error occurs
     * @throws URISyntaxException     if a URI syntax error occurs
     * @throws ClassNotFoundException if a class cannot be found
     */
    public static List<Class<?>> getAllAnnotatedClassesByPackage(final String packageName, final Class annotation) throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class<?>> classesByPackage = getClassesByPackage(packageName);
        List<Class<?>> classes = new ArrayList<>();
        for (Class<?> aClass : classesByPackage) {
            if (aClass.isAnnotationPresent(annotation)) {
                classes.add(aClass);
            }
        }
        return classes;
    }
}
