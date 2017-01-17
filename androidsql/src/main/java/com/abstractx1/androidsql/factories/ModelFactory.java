package com.abstractx1.androidsql.factories;

import android.database.Cursor;

import com.abstractx1.androidsql.SQLite;
import com.abstractx1.androidsql.db.SQLiteSession;
import com.abstractx1.androidsql.ModelTableInfo;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tfisher on 29/12/2016.
 */

public class ModelFactory {
    public static Object findById(SQLiteSession sqLiteSession, ModelTableInfo modelTableInfo, int id) throws NoSuchFieldException, InstantiationException, ParseException, IllegalAccessException {
        String selectQuery = SqlQueryFactory.findById(modelTableInfo, id);

        Cursor cursor = sqLiteSession.query(selectQuery);
        Object model = null;

        if (cursor != null) {
            model = build(modelTableInfo, cursor);
            cursor.close();
        }

        return model;
    }

    //TODO : boolean flag if connection required.
    public static Object build(ModelTableInfo modelTableInfo, Cursor cursor) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ParseException {
        Object model = modelTableInfo.getModelClass().newInstance();

        for (String column : modelTableInfo.getColumns()) {
            String columnTypeName = modelTableInfo.getColumnTypeName(column);

            if (columnTypeName.equals(SQLite.TYPENAME_INTEGER)) {
                setIntField(model, column, cursor.getInt(cursor.getColumnIndex(column)));
            } else if (columnTypeName.equals(SQLite.TYPENAME_BOOLEAN)) {
                setBoolField(model, column, cursor.getInt(cursor.getColumnIndex(column)));
            } else if (columnTypeName.equals(SQLite.TYPENAME_TEXT)) {
                setStringField(model, column, cursor.getString(cursor.getColumnIndex(column)));
            } else if (columnTypeName.equals(SQLite.TYPENAME_VARCHAR_255)) {
                setStringField(model, column, cursor.getString(cursor.getColumnIndex(column)));
            } else if (columnTypeName.equals(SQLite.TYPENAME_DATETIME)) {
                setDateField(model, column, cursor.getString(cursor.getColumnIndex(column)));
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
