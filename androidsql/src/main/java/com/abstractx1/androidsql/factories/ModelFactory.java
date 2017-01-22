package com.abstractx1.androidsql.factories;

import android.database.Cursor;

import com.abstractx1.androidsql.BaseModel;
import com.abstractx1.androidsql.ColumnInfo;
import com.abstractx1.androidsql.SQLite;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tfisher on 29/12/2016.
 */
//https://www.tutorialspoint.com/design_pattern/factory_pattern.htm
public class ModelFactory {
    //TODO : boolean flag if connection required.
    public static BaseModel build(Class<? extends BaseModel> modelClazz, ColumnInfo columnInfo, Cursor cursor) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ParseException {
        BaseModel model = modelClazz.newInstance();

        for (String columnName : columnInfo.getColumnNames()) {
            String columnType = columnInfo.getTypeName(columnName);

            if (columnType.equals(SQLite.TYPENAME_INTEGER)) {
                setIntField(model, columnName, cursor.getInt(cursor.getColumnIndex(columnName)));
            } else if (columnType.equals(SQLite.TYPENAME_BOOLEAN)) {
                setBoolField(model, columnName, cursor.getInt(cursor.getColumnIndex(columnName)));
            } else if (columnType.equals(SQLite.TYPENAME_TEXT)) {
                setStringField(model, columnName, cursor.getString(cursor.getColumnIndex(columnName)));
            } else if (columnType.equals(SQLite.TYPENAME_VARCHAR_255)) {
                setStringField(model, columnName, cursor.getString(cursor.getColumnIndex(columnName)));
            } else if (columnType.equals(SQLite.TYPENAME_DATETIME)) {
                setDateField(model, columnName, cursor.getString(cursor.getColumnIndex(columnName)));
            }
        }

        return model;
    }

    private static void setIntField(Object object, String fieldName, int value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(object.getClass(), fieldName);
        field.setInt(object, value);
    }

    private static void setBoolField(Object object, String fieldName, int value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(object.getClass(), fieldName);
        boolean bool = value > 0;
        field.setBoolean(object, bool);
    }

    private static void setStringField(Object object, String fieldName, String value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(object.getClass(), fieldName);
        field.set(object, value);
    }

    private static void setDateField(Object object, String fieldName, String value)
            throws NoSuchFieldException, IllegalAccessException, ParseException {
        Field field = getField(object.getClass(), fieldName);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = format.parse(value);
        field.set(object, date);
    }

    public static Field getField(Class<?> type, String fieldName) throws NoSuchFieldException {
        for (Class<?> clazz = type; clazz != null; clazz = clazz.getSuperclass()) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getName().equals(fieldName)) {
                    return field;
                }
            }
        }

        throw new NoSuchFieldException();
    }
}
