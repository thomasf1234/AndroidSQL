package com.abstractx1.androidsql.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tfisher on 29/12/2016.
 */

//On application new instantiate SQLiteSession with newest schema
//On upgrade will look for previous schemas until find the old, and then upgrade recursively until the current.
public class SQLiteSession extends SQLiteOpenHelper {
    private Schema schema;

    public SQLiteSession(Context context, String dbName, Schema schema) {
        super(context, dbName, null, schema.getDbVersion());
        this.schema = schema;
    }

    //move to Application
//    public static synchronized SQLiteSession getInstance(Context context) {
//        if (ourInstance == null) {
//            ourInstance = new SQLiteSession(context.getApplicationContext());
//        }
//        return ourInstance;
//    }



    //rawQuery() is for SQL statements that return a result set. Use execSQL() for SQL statements, like INSERT, that do not return a result set.
    public Cursor query(String sql) {
        Log.v("DEBUG", String.format("Querying SQL: %s", sql));
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            return cursor;
        } else {
            return null;
        }
    }

    public synchronized int insert(String sql) {
        exec(sql);
        Cursor cursor = query("SELECT LAST_INSERT_ROWID() AS id");
        if (cursor == null) {
            throw new RuntimeException("Unable to query LAST_INSERT_ROWID()");
        } else {
            int id = (int) cursor.getLong(cursor.getColumnIndex("id"));
            Log.v("DEBUG", String.format("Last insert_rowid: %d", id));
            return id;
        }
    }

    public synchronized void initializeDatabase() { getWritableDatabase().close(); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        schema.create(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            ArrayList<Schema> newSchemasOrdered = new ArrayList<>();

            Schema _schema = schema;
            while (_schema.getDbVersion() != oldVersion) {
                newSchemasOrdered.add(_schema);
                _schema = _schema.previousSchema();
            }
            Collections.reverse(newSchemasOrdered);

            for (Schema newSchema : newSchemasOrdered) {
                newSchema.upgradeFromPrevious(db);
            }
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        } else {
            db.execSQL("PRAGMA foreign_keys=ON");
        }
    }

    private synchronized void exec(String sql) {
        Log.v("DEBUG", String.format("Executing SQL: %s", sql));
        getWritableDatabase().execSQL(sql);
    }
}

