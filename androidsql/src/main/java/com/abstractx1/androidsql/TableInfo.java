package com.abstractx1.androidsql;

import android.database.Cursor;
import android.util.Log;

import com.abstractx1.androidsql.db.SQLiteDAO;
import com.abstractx1.androidsql.factories.SqlQueryFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tfisher on 18/01/2017.
 */

public class TableInfo {
    private Map<String, ColumnInfo> tableColumnInfosMap;

    public TableInfo(SQLiteDAO sqLiteDAO) {
        tableColumnInfosMap = new HashMap<>();

        for (String tableName : getTableNames(sqLiteDAO)) {
            Map<String, String> columnNameTypeNameMap = new HashMap<>();

            Cursor cursor = sqLiteDAO.query(SqlQueryFactory.buildTableInfo(tableName));

            try {
                if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                    do {
                        String columnName = cursor.getString(cursor.getColumnIndexOrThrow(SQLite.TABLE_INFO_COLUMN_NAME));
                        String typeName = cursor.getString(cursor.getColumnIndexOrThrow(SQLite.TABLE_INFO_COLUMN_TYPE));
                        columnNameTypeNameMap.put(columnName, typeName);
                    } while (cursor.moveToNext());
                } else {
                    throw new RuntimeException(String.format("No column_infos found for %s.", tableName));
                }
            } finally {
                cursor.close();
            }

            ColumnInfo columnInfo = new ColumnInfo(columnNameTypeNameMap);
            tableColumnInfosMap.put(tableName, columnInfo);
        }
    }


    private List<String> getTableNames(SQLiteDAO sqLiteDAO) {
        Cursor cursor = sqLiteDAO.query(String.format("SELECT %s FROM %s WHERE type ='table'", SQLite.TABLE_SQLITE_MASTER_COLUMN_NAME, SQLite.TABLE_SQLITE_MASTER));

        try {
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                List<String> tableNames = new ArrayList<>();

                do {
                    tableNames.add(cursor.getString(cursor.getColumnIndex(SQLite.TABLE_SQLITE_MASTER_COLUMN_NAME)));
                } while (cursor.moveToNext());

                return tableNames;
            } else {
                throw new RuntimeException("No table_infos found.");
            }
        } finally {
            cursor.close();
        }
    }

    public ColumnInfo getColumnInfo(String tableName) {
        return tableColumnInfosMap.get(tableName);
    }
}
