package com.abstractx1.androidsql.models;

import com.abstractx1.androidsql.BaseModel;
import com.abstractx1.androidsql.Column;
import com.abstractx1.androidsql.TableName;

import java.util.Date;

/**
 * Created by tfisher on 25/01/2017.
 */

@TableName("projects")
public class Project extends BaseModel {
    @Column(name = "id", readOnly = true) private long id;
    @Column(name = "name") private String name;
    @Column(name = "created_at") private Date createdAt;

    public Project() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
