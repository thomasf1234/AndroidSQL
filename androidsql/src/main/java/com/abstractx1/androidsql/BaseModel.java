package com.abstractx1.androidsql;

import java.util.Date;

/**
 * Created by tfisher on 29/12/2016.
 */

public abstract class BaseModel {
    public int id;
    public Date created_at;

    public boolean hasRecord() {
        return id != 0;
    }

    public int getId() { return id; }
    public Date getCreatedAt() { return created_at; }
}
