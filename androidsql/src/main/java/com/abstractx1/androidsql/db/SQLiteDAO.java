package com.abstractx1.androidsql.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;


import com.abstractx1.androidsql.TableInfo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tfisher on 29/12/2016.
 */

//Data Access Object Pattern
public class SQLiteDAO {
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    public SQLiteDAO(Context context, String dbName, Schema schema) {
        this.mySQLiteOpenHelper = new MySQLiteOpenHelper(context.getApplicationContext(), dbName, schema);
        initializeDatabase();
    }

    //rawQuery() is for SQL statements that return a result set. Use execSQL() for SQL statements, like INSERT, that do not return a result set.
    public Cursor query(String sql) {
        Log.v("DEBUG", String.format("Querying SQL: %s", sql));
        Cursor cursor = mySQLiteOpenHelper.getReadableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            return cursor;
        } else {
            return null;
        }
    }

    //synchronized here is required to ensure correct id is retrieved.
    //http://stackoverflow.com/questions/15010761/how-to-check-if-a-cursor-is-empty
    public synchronized long insert(String sql) {
        exec(sql);
        Cursor cursor = query("SELECT LAST_INSERT_ROWID() AS id");
        if (cursor == null) {
            throw new RuntimeException("Unable to query LAST_INSERT_ROWID()");
        } else {
            long id = cursor.getLong(cursor.getColumnIndex("id"));
            Log.v("DEBUG", String.format("Last insert_rowid: %d", id));
            return id;
        }
    }

    public synchronized void initializeDatabase() {
        mySQLiteOpenHelper.getWritableDatabase();
    }

    private synchronized void exec(String sql) {
        Log.v("DEBUG", String.format("Executing SQL: %s", sql));
        mySQLiteOpenHelper.getWritableDatabase().execSQL(sql);
    }

    private class MySQLiteOpenHelper extends SQLiteOpenHelper {
        private Schema schema;

        public MySQLiteOpenHelper(Context context, String dbName, Schema schema) {
            super(context, dbName, null, schema.getDbVersion());
            this.schema = schema;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            schema.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion) {
                ArrayList<Schema> newSchemasOrdered = new ArrayList<>();
                System.out.println(String.format("oldVersion: %d, newVersion %d", oldVersion, newVersion));
                Schema _schema = schema;
                while (_schema.getDbVersion() != oldVersion) {
                    newSchemasOrdered.add(_schema);
                    _schema = _schema.previousSchema();
                }
                Collections.reverse(newSchemasOrdered);

                for (Schema newSchema : newSchemasOrdered) {
                    System.out.println(String.format("Running the upgrades for newSchema version %d", newSchema.dbVersion));
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
    }
}

