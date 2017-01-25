package com.abstractx1.androidsql;

/**
 * Created by tfisher on 29/12/2016.
 */

public abstract class BaseModel {
    public String getTableName() {
        return this.getClass().getAnnotation(TableName.class).value();
    }
}
