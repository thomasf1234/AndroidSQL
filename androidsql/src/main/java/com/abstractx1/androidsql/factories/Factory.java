package com.abstractx1.androidsql.factories;

import com.abstractx1.androidsql.BaseModel;
import com.abstractx1.androidsql.Column;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tfisher on 03/01/2017.
 */

public class Factory {
    protected static Field findFieldForColumnName(Class<? extends BaseModel> modelClazz, String columnName) {
        List<Field> fields = getAllModelFields(modelClazz);
        for (Field field : fields) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null && columnAnnotation.name().equals(columnName)) {
                return field;
            }
        }

        return null;
    }

    protected static List<Field> getAllModelFields(Class aClass) {
        List<Field> fields = new ArrayList<>();
        do {
            Collections.addAll(fields, aClass.getDeclaredFields());
            aClass = aClass.getSuperclass();
        } while (aClass != null);
        return fields;
    }
}
