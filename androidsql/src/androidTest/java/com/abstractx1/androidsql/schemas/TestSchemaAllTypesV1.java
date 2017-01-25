package com.abstractx1.androidsql.schemas;

import android.database.sqlite.SQLiteDatabase;

import com.abstractx1.androidsql.db.Schema;

/**
 * Created by tfisher on 17/01/2017.
 */

public class TestSchemaAllTypesV1 extends Schema {
    @Override
    public void setDbVersion() {
        this.dbVersion = 1;
    }

    @Override
    public void setOrderedStatements() {
        orderedStatements = new String[] {
                "CREATE TABLE test_table(\n" +
                        "   id INTEGER PRIMARY KEY AUTOINCREMENT     NOT NULL,\n" +
                        "   byte_field TINYINT,\n" +
                        "   short_field SMALLINT,\n" +
                        "   int_field_a MEDIUMINT,\n" +
                        "   int_field_b INT,\n" +
                        "   long_field BIGINT,\n" +
                        "   string_field_a CHARACTER(20),\n" +
                        "   string_field_b VARCHAR(255),\n" +
                        "   string_field_c VARYING CHARACTER(255),\n" +
                        "   string_field_d NCHAR(55),\n" +
                        "   string_field_e NATIVE CHARACTER(70),\n" +
                        "   string_field_f NVARCHAR(100),\n" +
                        "   string_field_g TEXT,\n" +
                        "   string_field_h CLOB,\n" +
                        "   byte_array_field BLOB,\n" +
                        "   double_field_a REAL,\n" +
                        "   double_field_b DOUBLE,\n" +
                        "   double_field_c DOUBLE_PRECISION,\n" +
                        "   float_field FLOAT,\n" +
                        "   boolean_field BOOLEAN,\n" +
                        "   date_field DATE,\n" +
                        "   datetime_field DATETIME\n" +
                        ");"
        };
    }

    @Override
    public void upgradeFromPrevious(SQLiteDatabase db) {}

    @Override
    public Schema previousSchema() {
        return null;
    }
}
