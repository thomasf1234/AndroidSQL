package com.abstractx1.androidsql.models;

import com.abstractx1.androidsql.BaseModel;
import com.abstractx1.androidsql.ColumnName;
import com.abstractx1.androidsql.TableName;

import java.util.Date;

/**
 * Created by tfisher on 25/01/2017.
 */

@TableName("projects")
public class ProjectPrimaryKeyInt extends BaseModel {
    @ColumnName("id") private int id;
    @ColumnName("name") private String name;
    @ColumnName("created_at") private Date createdAt;

    public ProjectPrimaryKeyInt() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
