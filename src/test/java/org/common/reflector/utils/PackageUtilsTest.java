package org.common.reflector.utils;

import org.common.reflector.data.annotation.ClassAnnotation;
import org.common.reflector.data.annotation.CustomMethodAnnotation;
import org.common.reflector.util.TestConstant;
import org.junit.jupiter.api.Test;
import org.reflector.PackageUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackageUtilsTest {
    @Test
    public void findAllClassesByPackageTest() throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class<?>> classesByPackage = PackageUtils.getClassesByPackage(TestConstant.REFLECTOR_PACKAGE);
        assertTrue(classesByPackage.size() >= 7);
    }

    @Test
    public void getAnnotatedClassesTest() throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class<?>> classes = PackageUtils.getAllAnnotatedClassesByPackage(TestConstant.REFLECTOR_DATA_PACKAGE, ClassAnnotation.class);
        int expectedAnnotationClassesQuantity = 2;
        assertEquals(expectedAnnotationClassesQuantity, classes.size());
    }

    @Test
    public void getNotAnnotatedClassesTest() throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class<?>> classes = PackageUtils.getAllAnnotatedClassesByPackage(TestConstant.REFLECTOR_DATA_PACKAGE, CustomMethodAnnotation.class);
        int expectedAnnotationClassesQuantity = 0;
        assertEquals(expectedAnnotationClassesQuantity, classes.size());
    }
}
