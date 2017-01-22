package com.abstractx1.androidsql;

import java.lang.annotation.Inherited;
import java.util.Date;

/**
 * Created by tfisher on 29/12/2016.
 */

@TableName("")
public abstract class BaseModel {
    public long id;
    public Date created_at;

    public boolean hasRecord() {
        return id != 0;
    }

    public long getId() { return id; }
    public Date getCreatedAt() { return created_at; }
}
