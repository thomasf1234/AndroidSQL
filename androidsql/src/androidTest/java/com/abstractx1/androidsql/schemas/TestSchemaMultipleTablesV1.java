package com.abstractx1.androidsql.schemas;

import android.database.sqlite.SQLiteDatabase;

import com.abstractx1.androidsql.db.Schema;

/**
 * Created by tfisher on 17/01/2017.
 */

public class TestSchemaMultipleTablesV1 extends Schema {
    @Override
    public void setDbVersion() {
        this.dbVersion = 1;
    }

    @Override
    public void setOrderedStatements() {
        orderedStatements = new String[] {
                "CREATE TABLE projects(\n" +
                        "   id INTEGER PRIMARY KEY AUTOINCREMENT     NOT NULL,\n" +
                        "   name           TEXT    NOT NULL,\n" +
                        "   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
                        ");",

                "CREATE TABLE tasks(\n" +
                        "   id INTEGER PRIMARY KEY AUTOINCREMENT     NOT NULL,\n" +
                        "   number INT NOT NULL,\n" +
                        "   title TEXT NOT NULL,\n" +
                        "   description           TEXT,\n" +
                        "   type          VARCHAR(255)    NOT NULL,\n" +
                        "   status        VARCHAR(255)    NOT NULL,\n" +
                        "   project_id BIGINT NOT NULL,\n" +
                        "   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                        "   CONSTRAINT uq_project_id_task_number UNIQUE(project_id, number),\n" +
                        "   FOREIGN KEY(project_id) REFERENCES projects(id) ON DELETE CASCADE\n" +
                        ");"
        };
    }

    @Override
    public void upgradeFromPrevious(SQLiteDatabase db) {}

    @Override
    public Schema previousSchema() {
        return null;
    }
}
