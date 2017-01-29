package com.abstractx1.androidsql;

/**
 * Created by tfisher on 29/12/2016.
 */

public abstract class BaseModel {
    public final static String COLUMN_ID = "id";

    @Column(name = COLUMN_ID, readOnly = true) protected long id;

    public String getTableName() {
        return this.getClass().getAnnotation(TableName.class).value();
    }

    public boolean hasId() {
        return id != 0;
    }

    public long getId() {
        return id;
    }
}
