package org.common.reflector.utils;

import org.common.reflector.data.CustomTestClassForType;
import org.common.reflector.util.TestConstant;
import org.junit.jupiter.api.Test;
import org.reflector.FieldsExtraUtils;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldsExtraUtilsTest {
    @Test
    void getAllPrivateFieldsTest() {

        List<Field> fields = FieldsExtraUtils.getAllPrivateFields(CustomTestClassForType.class);

        assertAll("privateFields",
                () -> assertEquals(fields.get(0).getName(), TestConstant.STRING_FIELD),
                () -> assertEquals(fields.get(1).getName(), TestConstant.OBJECT_FIELD),
                () -> assertEquals(fields.get(2).getName(), TestConstant.FLOAT_FIELD),
                () -> assertEquals(fields.size(), 3)
        );
    }
}
