package com.abstractx1.androidsql.models;

import com.abstractx1.androidsql.BaseModel;
import com.abstractx1.androidsql.Column;
import com.abstractx1.androidsql.TableName;

import java.util.Date;

/**
 * Created by tfisher on 25/01/2017.
 */

@TableName("projects")
public class ProjectFieldIncorrectType extends BaseModel {
    @Column(name = "name") private int name;
    @Column(name = "created_at", readOnly = true) private Date createdAt;

    public ProjectFieldIncorrectType() {}

    public long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
