package com.abstractx1.androidsql;

/**
 * Created by tfisher on 04/01/2017.
 */

public final class SQLite {
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

//    public static String toString(Object value, String typeName) {
//        String returnValue;
//
//        if (typeName.equals(SQLite.TYPENAME_INTEGER)) {
//            returnValue = value.toString();
//        } else if (typeName.equals(SQLite.TYPENAME_TEXT)) {
//            returnValue = String.format("'%s'", value.toString().replaceAll("'", ESCAPE_CHAR + "'"));
//        } else if (typeName.equals(SQLite.TYPENAME_BOOLEAN)) {
//            returnValue = ((boolean) value) ? "1" : "0";
//        } else if (typeName.equals(SQLite.TYPENAME_VARCHAR_255)) {
//            returnValue = String.format("'%s'", value.toString().replaceAll("'", ESCAPE_CHAR + "'"));
//        } else if (typeName.equals(SQLite.TYPENAME_DATETIME)) {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            returnValue = formatter.format(value);
//        } else {
//            throw new UnsupportedOperationException(String.format("String conversion for SQLite type name '%s' is not supported", typeName));
//        }
//
//        return returnValue;
//    }
}
