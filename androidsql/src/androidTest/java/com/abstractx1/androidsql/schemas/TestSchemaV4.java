package com.abstractx1.androidsql.schemas;

import android.database.sqlite.SQLiteDatabase;

import com.abstractx1.androidsql.db.Schema;

/**
 * Created by tfisher on 17/01/2017.
 */

public class TestSchemaV4 extends Schema {
    @Override
    public void setDbVersion() {
        this.dbVersion = 4;
    }

    @Override
    public void setOrderedStatements() {
        orderedStatements = new String[] {
                "CREATE TABLE projects(\n" +
                        "   id INTEGER PRIMARY KEY AUTOINCREMENT     NOT NULL,\n" +
                        "   name           TEXT    NOT NULL,\n" +
                        "   number    INTEGER,\n" +
                        "   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
                        ");"
        };
    }

    @Override
    public void upgradeFromPrevious(SQLiteDatabase db) {
        db.execSQL("DROP TABLE projects;");
        create(db);
    }

    @Override
    public Schema previousSchema() {
        return new TestSchemaV1();
    }
}
