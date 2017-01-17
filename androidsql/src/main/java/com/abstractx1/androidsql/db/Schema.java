package com.abstractx1.androidsql.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tfisher on 29/12/2016.
 */

//http://stackoverflow.com/questions/25117972/what-is-the-use-of-db-version-attribute-in-sq-lite-database-in-android/25118406
public abstract class Schema {
    protected int dbVersion;
    protected String[] orderedStatements;

    public Schema() {
        setDbVersion();
        setOrderedStatements();
    }

    public void create(SQLiteDatabase db) {
        for (String statement : getOrderedStatements()) {
            System.out.println(String.format("Creating Schema: %n%s%n", statement));
            db.execSQL(statement);
        }
    }

    public int getDbVersion() { return dbVersion; }
    public String[] getOrderedStatements() { return orderedStatements; }

    public abstract void setDbVersion();
    public abstract void setOrderedStatements();
    public abstract void upgradeFromPrevious(SQLiteDatabase db);
    public abstract Schema previousSchema();

}
