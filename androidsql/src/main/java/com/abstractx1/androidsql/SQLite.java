package com.abstractx1.androidsql;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tfisher on 04/01/2017.
 */

public final class SQLite {
    public static final String GMT = "GMT";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

    public static final String TABLE_SQLITE_MASTER = "sqlite_master";
    public static final String TABLE_SQLITE_MASTER_COLUMN_NAME = "name";

    public static final String TABLE_INFO_COLUMN_NAME = "name";
    public static final String TABLE_INFO_COLUMN_TYPE = "type";


    public static final String TYPENAME_TINYINT = "TINYINT";
    public static final String TYPENAME_SMALLINT = "SMALLINT";
    public static final String TYPENAME_MEDIUMINT = "MEDIUMINT";
    public static final String TYPENAME_INT = "INT";
    public static final String TYPENAME_BIGINT = "BIGINT";
    public static final String TYPENAME_INTEGER = "INTEGER";

    public static final String TYPENAME_BLOB = "BLOB";

    public static final String TYPENAME_FLOAT = "FLOAT";
    public static final String TYPENAME_DOUBLE = "DOUBLE";
    public static final String TYPENAME_REAL = "REAL";

    public static final String TYPENAME_TEXT = "TEXT";

    public static final String TYPENAME_DATE = "DATE";
    public static final String TYPENAME_DATETIME = "DATETIME";
    public static final String TYPENAME_BOOLEAN = "BOOLEAN";

    public static final String ESCAPE_CHAR = "'";
    public static final String BOOLEAN_TRUE = "1";
    public static final String BOOLEAN_FALSE = "0";



    public static String toString(Object value, String columnType) {
        if (columnType.contains(SQLite.TYPENAME_INT)) {
            return String.format("%d", value);
        }
        else if (columnType.contains(SQLite.TYPENAME_BLOB) || columnType.isEmpty()) {
            byte[] byteArray = (byte[]) value;
            StringBuilder builder = new StringBuilder();
            for(byte b : byteArray) {
                builder.append(String.format("%02x", b));
            }
            return String.format("X'%s'", builder.toString());
        }
        else if(columnType.contains("REA") || columnType.contains("FLOA") || columnType.contains("DOUB")) {
            if (columnType.equals(SQLite.TYPENAME_FLOAT)) {
                return Float.toString((float) value);
            } else {
                return Double.toString((double) value);
            }
        }
        else if(columnType.contains("CHAR") || columnType.contains("CLOB") || columnType.contains("TEXT")) {
            return String.format("'%s'", ((String) value).replaceAll("'", ESCAPE_CHAR + "'"));
        }
        else {
            if (columnType.equals(SQLite.TYPENAME_DATE)) {
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                return String.format("'%s'", formatter.format((Date) value));
            } else if (columnType.equals(SQLite.TYPENAME_DATETIME)) {
                SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
                return String.format("'%s'", formatter.format((Date) value));
            } else if (columnType.equals(SQLite.TYPENAME_BOOLEAN)) {
                return ((boolean) value) ? BOOLEAN_TRUE : BOOLEAN_FALSE;
            } else {
                return String.valueOf(value);
            }
        }
    }

}
