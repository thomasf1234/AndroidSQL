package com.abstractx1.androidsql.factories;

import android.database.Cursor;

import com.abstractx1.androidsql.BaseModel;
import com.abstractx1.androidsql.ColumnInfo;
import com.abstractx1.androidsql.Column;
import com.abstractx1.androidsql.SQLite;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by tfisher on 29/12/2016.
 */
//https://www.tutorialspoint.com/design_pattern/factory_pattern.htm
public class ModelFactory extends Factory {
    public static <T extends BaseModel> T build(Class<T> modelClazz, ColumnInfo columnInfo, Cursor cursor) throws IllegalAccessException, InstantiationException, ParseException, NoSuchFieldException {
        T model = modelClazz.newInstance();

        for (String columnName : columnInfo.getColumnNames()) {
            String columnType = columnInfo.getTypeName(columnName);

            Field field = findFieldForColumnName(modelClazz, columnName);
            if (field == null) { throw new NoSuchFieldException(String.format("Could not find an associated field on class: '%s', column: '%s'", modelClazz.toString(), columnName)); }
            boolean origAccessibility = field.isAccessible();
            field.setAccessible(true);

            try {
                if (cursor.isNull(cursor.getColumnIndex(columnName))) {
                    field.set(model, null);
                }
                else if (columnType.contains(SQLite.TYPENAME_INT)) {
                    if(columnType.equals(SQLite.TYPENAME_TINYINT)) {
                        setByteField(model, field, columnName, cursor);
                    } else if(columnType.equals(SQLite.TYPENAME_SMALLINT)) {
                        setShortField(model, field, columnName, cursor);
                    } else if(columnType.equals(SQLite.TYPENAME_MEDIUMINT)) {
                        setIntField(model, field, columnName, cursor);
                    } else if(columnType.equals(SQLite.TYPENAME_INT)) {
                        setIntField(model, field, columnName, cursor);
                    } else if(columnType.equals(SQLite.TYPENAME_BIGINT)) {
                        setLongField(model, field, columnName, cursor);
                    } else {
                        setLongField(model, field, columnName, cursor);
                    }
                }
                else if (columnType.contains(SQLite.TYPENAME_BLOB) || columnType.isEmpty()) {
                    setBytesField(model, field, columnName, cursor);
                }
                else if(columnType.contains("REA") || columnType.contains("FLOA") || columnType.contains("DOUB")) {
                    if (columnType.equals(SQLite.TYPENAME_FLOAT)) {
                        setFloatField(model, field, columnName, cursor);
                    } else {
                        setDoubleField(model, field, columnName, cursor);
                    }
                }
                else if(columnType.contains("CHAR") || columnType.contains("CLOB") || columnType.contains("TEXT")) {
                    setStringField(model, field, columnName, cursor);
                }
                else {
                    if (columnType.equals(SQLite.TYPENAME_DATE)) {
                        setDateField(model, field, columnName, cursor);
                    } else if (columnType.equals(SQLite.TYPENAME_DATETIME)) {
                        setDateTimeField(model, field, columnName, cursor);
                    } else if (columnType.equals(SQLite.TYPENAME_BOOLEAN)) {
                        setBoolField(model, field, columnName, cursor);
                    }
                }
            } finally {
                field.setAccessible(origAccessibility);
            }
        }

        return model;
    }

    private static void setByteField(BaseModel model, Field field, String columnName, Cursor cursor)
            throws IllegalAccessException {
        byte value = (byte) cursor.getInt(cursor.getColumnIndex(columnName));
        field.setByte(model, value);
    }

    private static void setShortField(BaseModel model, Field field, String columnName, Cursor cursor)
            throws IllegalAccessException {
        short value = cursor.getShort(cursor.getColumnIndex(columnName));
        field.setShort(model, value);
    }

    private static void setIntField(BaseModel model, Field field, String columnName, Cursor cursor)
            throws IllegalAccessException {
        int value = cursor.getInt(cursor.getColumnIndex(columnName));
        field.setInt(model, value);
    }

    private static void setLongField(BaseModel model, Field field, String columnName, Cursor cursor)
            throws IllegalAccessException {
        long value = cursor.getLong(cursor.getColumnIndex(columnName));
        field.setLong(model, value);
    }

    private static void setBytesField(BaseModel model, Field field, String columnName, Cursor cursor)
            throws IllegalAccessException {
        byte[] value = cursor.getBlob(cursor.getColumnIndex(columnName));
        field.set(model, value);
    }

    private static void setFloatField(BaseModel model, Field field, String columnName, Cursor cursor)
            throws IllegalAccessException {
        float value = cursor.getFloat(cursor.getColumnIndex(columnName));
        field.setFloat(model, value);
    }

    private static void setDoubleField(BaseModel model, Field field, String columnName, Cursor cursor)
            throws IllegalAccessException {
        double value = cursor.getDouble(cursor.getColumnIndex(columnName));
        field.setDouble(model, value);
    }

    private static void setStringField(BaseModel model, Field field, String columnName, Cursor cursor)
            throws IllegalAccessException {
        String value = cursor.getString(cursor.getColumnIndex(columnName));
        field.set(model, value);
    }

    private static void setDateField(BaseModel model, Field field, String columnName, Cursor cursor)
            throws IllegalAccessException, ParseException {
        String rawValue = cursor.getString(cursor.getColumnIndex(columnName));
        SimpleDateFormat format = new SimpleDateFormat(SQLite.DATE_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone(SQLite.GMT));
        Date value = format.parse(rawValue);
        field.set(model, value);
    }

    private static void setDateTimeField(BaseModel model, Field field, String columnName, Cursor cursor)
            throws IllegalAccessException, ParseException {
        String rawValue = cursor.getString(cursor.getColumnIndex(columnName));
        SimpleDateFormat format = new SimpleDateFormat(SQLite.DATETIME_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone(SQLite.GMT));
        Date value = format.parse(rawValue);
        field.set(model, value);
    }

    private static void setBoolField(BaseModel model, Field field, String columnName, Cursor cursor)
            throws IllegalAccessException {
        int rawValue = cursor.getInt(cursor.getColumnIndex(columnName));
        boolean value = rawValue > 0;
        field.setBoolean(model, value);
    }
}
