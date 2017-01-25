package com.abstractx1.androidsql.models;

import com.abstractx1.androidsql.BaseModel;
import com.abstractx1.androidsql.ColumnName;
import com.abstractx1.androidsql.TableName;

import java.util.Date;

/**
 * Created by tfisher on 25/01/2017.
 */

@TableName("projects")
public class ProjectFieldIncorrectType extends BaseModel {
    @ColumnName("id") private long id;
    @ColumnName("name") private int name;
    @ColumnName("created_at") private Date createdAt;

    public ProjectFieldIncorrectType() {}

    public long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
