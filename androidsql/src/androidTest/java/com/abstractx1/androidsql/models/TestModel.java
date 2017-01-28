package com.abstractx1.androidsql.models;

import com.abstractx1.androidsql.BaseModel;
import com.abstractx1.androidsql.Column;
import com.abstractx1.androidsql.TableName;

import java.util.Date;

/**
 * Created by tfisher on 25/01/2017.
 */

@TableName("test_table")
public class TestModel extends BaseModel {
    @Column(name = "byte_field") private byte byteField;
    @Column(name = "short_field") private short shortField;
    @Column(name = "int_field_a") private int intFieldA;
    @Column(name = "int_field_b") private int intFieldB;
    @Column(name = "long_field") private long longField;

    @Column(name = "string_field_a") private String stringFieldA;
    @Column(name = "string_field_b") private String stringFieldB;
    @Column(name = "string_field_c") private String stringFieldC;
    @Column(name = "string_field_d") private String stringFieldD;
    @Column(name = "string_field_e") private String stringFieldE;
    @Column(name = "string_field_f") private String stringFieldF;
    @Column(name = "string_field_g") private String stringFieldG;
    @Column(name = "string_field_h") private String stringFieldH;

    @Column(name = "byte_array_field") private byte[] byteArrayField;

    @Column(name = "double_field_a") private double doubleFieldA;
    @Column(name = "double_field_b") private double doubleFieldB;
    @Column(name = "double_field_c") private double doubleFieldC;
    @Column(name = "float_field") private float floatField;

    @Column(name = "boolean_field") private boolean booleanField;
    @Column(name = "date_field") private Date dateField;
    @Column(name = "datetime_field") private Date dateTimeField;

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

    public void setByteField(byte byteField) {
        this.byteField = byteField;
    }

    public void setShortField(short shortField) {
        this.shortField = shortField;
    }

    public void setIntFieldA(int intFieldA) {
        this.intFieldA = intFieldA;
    }

    public void setIntFieldB(int intFieldB) {
        this.intFieldB = intFieldB;
    }

    public void setLongField(long longField) {
        this.longField = longField;
    }

    public void setStringFieldA(String stringFieldA) {
        this.stringFieldA = stringFieldA;
    }

    public void setStringFieldB(String stringFieldB) {
        this.stringFieldB = stringFieldB;
    }

    public void setStringFieldC(String stringFieldC) {
        this.stringFieldC = stringFieldC;
    }

    public void setStringFieldD(String stringFieldD) {
        this.stringFieldD = stringFieldD;
    }

    public void setStringFieldE(String stringFieldE) {
        this.stringFieldE = stringFieldE;
    }

    public void setStringFieldF(String stringFieldF) {
        this.stringFieldF = stringFieldF;
    }

    public void setStringFieldG(String stringFieldG) {
        this.stringFieldG = stringFieldG;
    }

    public void setStringFieldH(String stringFieldH) {
        this.stringFieldH = stringFieldH;
    }

    public void setByteArrayField(byte[] byteArrayField) {
        this.byteArrayField = byteArrayField;
    }

    public void setDoubleFieldA(double doubleFieldA) {
        this.doubleFieldA = doubleFieldA;
    }

    public void setDoubleFieldB(double doubleFieldB) {
        this.doubleFieldB = doubleFieldB;
    }

    public void setDoubleFieldC(double doubleFieldC) {
        this.doubleFieldC = doubleFieldC;
    }

    public void setFloatField(float floatField) {
        this.floatField = floatField;
    }

    public void setBooleanField(boolean booleanField) {
        this.booleanField = booleanField;
    }

    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }

    public void setDateTimeField(Date dateTimeField) {
        this.dateTimeField = dateTimeField;
    }
}
