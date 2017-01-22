package com.abstractx1.androidsql;

import android.content.Context;
import android.database.Cursor;

import com.abstractx1.androidsql.db.SQLiteDAO;
import com.abstractx1.androidsql.db.Schema;
import com.abstractx1.androidsql.factories.ModelFactory;
import com.abstractx1.androidsql.factories.SqlQueryFactory;

import java.text.ParseException;

/**
 * Created by tfisher on 19/01/2017.
 */

public class ModelDAO {
    private SQLiteDAO sqLiteDAO;
    private TableInfo tableInfo;

    public ModelDAO(Context context, String DB_NAME, Schema schema) {
        this.sqLiteDAO = new SQLiteDAO(context.getApplicationContext(), DB_NAME, schema);
        this.tableInfo = new TableInfo(getSqLiteDAO());
    }

    public BaseModel findById(Class<? extends BaseModel> modelClazz, int id) throws NoSuchFieldException, InstantiationException, IllegalAccessException, ParseException {
        String tableName = modelClazz.getAnnotation(TableName.class).value();
        String selectQuery = SqlQueryFactory.buildFindById(tableName, id);
        Cursor cursor = getSqLiteDAO().query(selectQuery);

        if (cursor == null) {
            return null;
        } else {
            BaseModel model = ModelFactory.build(modelClazz, getColumnInfo(modelClazz), cursor);
            cursor.close();
            return  model;
        }
    }

    protected void populateModelTableInfoMap() {

    }

    protected ColumnInfo getColumnInfo(Class<? extends BaseModel> modelClazz) {
        TableName tableNameAnnotation = modelClazz.getAnnotation(TableName.class);

        return tableInfo.getColumnInfo(tableNameAnnotation.value());
    }

    protected SQLiteDAO getSqLiteDAO() {
        return sqLiteDAO;
    }
}
