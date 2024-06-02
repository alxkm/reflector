package org.reflector;

import org.reflector.util.ReflectionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class PackageUtils {

    private PackageUtils() {

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(PackageUtils.class);
    private static final ClassLoader CLASSLOADER;

    static {
        final ClassLoader threadClassLoader = Thread.currentThread().getContextClassLoader();
        CLASSLOADER = (threadClassLoader != null) ? threadClassLoader : ReflectionUtils.class.getClassLoader();
    }

    public static List<Class<?>> getClassesByPackage(final String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
        String path = packageName.replace(ReflectionConstant.DOT_SYMBOL, ReflectionConstant.SLASH);
        Enumeration<URL> resources = CLASSLOADER.getResources(path);
        List<File> directories = new ArrayList<>();
        while (resources.hasMoreElements()) {
            directories.add(new File(new URI(resources.nextElement().toString()).getPath()));
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : directories) {
            classes.addAll(getClassesByDirectoryAndPackage(directory, packageName));
        }
        return classes;
    }

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
