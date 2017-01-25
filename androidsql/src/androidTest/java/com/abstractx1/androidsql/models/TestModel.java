package com.abstractx1.androidsql.models;

import com.abstractx1.androidsql.BaseModel;
import com.abstractx1.androidsql.ColumnName;
import com.abstractx1.androidsql.TableName;

import java.util.Date;

/**
 * Created by tfisher on 25/01/2017.
 */

@TableName("test_table")
public class TestModel extends BaseModel {
    @ColumnName("id") private long id;
    @ColumnName("byte_field") private byte byteField;
    @ColumnName("short_field") private short shortField;
    @ColumnName("int_field_a") private int intFieldA;
    @ColumnName("int_field_b") private int intFieldB;
    @ColumnName("long_field") private long longField;

    @ColumnName("string_field_a") private String stringFieldA;
    @ColumnName("string_field_b") private String stringFieldB;
    @ColumnName("string_field_c") private String stringFieldC;
    @ColumnName("string_field_d") private String stringFieldD;
    @ColumnName("string_field_e") private String stringFieldE;
    @ColumnName("string_field_f") private String stringFieldF;
    @ColumnName("string_field_g") private String stringFieldG;
    @ColumnName("string_field_h") private String stringFieldH;

    @ColumnName("byte_array_field") private byte[] byteArrayField;

    @ColumnName("double_field_a") private double doubleFieldA;
    @ColumnName("double_field_b") private double doubleFieldB;
    @ColumnName("double_field_c") private double doubleFieldC;
    @ColumnName("float_field") private float floatField;

    @ColumnName("boolean_field") private boolean booleanField;
    @ColumnName("date_field") private Date dateField;
    @ColumnName("datetime_field") private Date dateTimeField;

    public TestModel() {}

    public long getId() {
        return id;
    }

    public byte getByteField() {
        return byteField;
    }

    public short getShortField() {
        return shortField;
    }

    public int getIntFieldA() {
        return intFieldA;
    }

    public int getIntFieldB() {
        return intFieldB;
    }

    public long getLongField() {
        return longField;
    }

    public String getStringFieldA() {
        return stringFieldA;
    }

    public String getStringFieldB() {
        return stringFieldB;
    }

    public String getStringFieldC() {
        return stringFieldC;
    }

    public String getStringFieldD() {
        return stringFieldD;
    }

    public String getStringFieldE() {
        return stringFieldE;
    }

    public String getStringFieldF() {
        return stringFieldF;
    }

    public String getStringFieldG() {
        return stringFieldG;
    }

    public String getStringFieldH() {
        return stringFieldH;
    }

    public byte[] getByteArrayField() {
        return byteArrayField;
    }

    public double getDoubleFieldA() {
        return doubleFieldA;
    }

    public double getDoubleFieldB() {
        return doubleFieldB;
    }

    public double getDoubleFieldC() {
        return doubleFieldC;
    }

    public float getFloatField() {
        return floatField;
    }

    public boolean getBooleanField() {
        return booleanField;
    }

    public Date getDateField() {
        return dateField;
    }

    public Date getDateTimeField() {
        return dateTimeField;
    }
}
