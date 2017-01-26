package com.abstractx1.androidsql.factories;

import com.abstractx1.androidsql.BaseModel;
import com.abstractx1.androidsql.Column;

import java.lang.reflect.Field;

/**
 * Created by tfisher on 03/01/2017.
 */

public class Factory {
    protected static Field findFieldForColumnName(Class<? extends BaseModel> modelClazz, String columnName) {
        Field[] fields = modelClazz.getDeclaredFields();
        for (Field field : fields) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null && columnAnnotation.name().equals(columnName)) {
                return field;
            }
        }

        return null;
    }
}
