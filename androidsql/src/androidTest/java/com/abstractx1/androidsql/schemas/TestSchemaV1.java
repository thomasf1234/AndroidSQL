package com.abstractx1.androidsql.schemas;

import android.database.sqlite.SQLiteDatabase;

import com.abstractx1.androidsql.db.Schema;

/**
 * Created by tfisher on 17/01/2017.
 */

public class TestSchemaV1 extends Schema {
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
                        "   number INTEGER NOT NULL,\n" +
                        "   title TEXT NOT NULL,\n" +
                        "   description           TEXT    NOT NULL,\n" +
                        "   type          VARCHAR(255)    NOT NULL,\n" +
                        "   status        VARCHAR(255)    NOT NULL,\n" +
                        "   project_id INTEGER NOT NULL,\n" +
                        "   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                        "   CONSTRAINT uq_project_id_task_number UNIQUE(project_id, number),\n" +
                        "   FOREIGN KEY(project_id) REFERENCES projects(id) ON DELETE CASCADE\n" +
                        ");",

                "CREATE TABLE sub_tasks(\n" +
                        "   id INTEGER PRIMARY KEY AUTOINCREMENT     NOT NULL,\n" +
                        "   number INTEGER NOT NULL,\n" +
                        "   description           TEXT    NOT NULL,\n" +
                        "   completed BOOLEAN NOT NULL DEFAULT 0,\n" +
                        "   task_id INTEGER NOT NULL,\n" +
                        "   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                        "   FOREIGN KEY(task_id) REFERENCES tasks(id) ON DELETE CASCADE\n" +
                        ");",

                "CREATE INDEX ix_tasks_project_id ON tasks (project_id);",
                "CREATE INDEX ix_sub_tasks_task_id ON sub_tasks (task_id);"
        };
    }

    @Override
    public void upgradeFromPrevious(SQLiteDatabase db) {}

    @Override
    public Schema previousSchema() {
        return null;
    }
}
