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

    public <T extends BaseModel> T findById(Class<T> modelClazz, long id) throws NoSuchFieldException, InstantiationException, IllegalAccessException, ParseException {
        String tableName = getTableName(modelClazz);
        String selectQuery = SqlQueryFactory.buildFindById(tableName, id);
        Cursor cursor = getSqLiteDAO().query(selectQuery);

        if (cursor == null) {
            return null;
        } else {
            T model = ModelFactory.build(modelClazz, getColumnInfo(tableName), cursor);
            cursor.close();
            return  model;
        }
    }

    public <T extends BaseModel> T save(T model) throws IllegalAccessException, ParseException, NoSuchFieldException, InstantiationException {
        String tableName = getTableName(model.getClass());

        if (hasRecord(model)) {
            //String updateSql = SqlQueryFactory.buildUpdate(model, getColumnInfo(tableName));
            //getSqLiteDAO().insert(updateSql);
            return null; //Not implemented
        } else {
            String insertSql = SqlQueryFactory.buildInsert(model, getColumnInfo(tableName));
            long id = getSqLiteDAO().insert(insertSql);
            T savedModel = (T) findById(model.getClass(), id);
            return savedModel;
        }
    }

    private boolean hasRecord(BaseModel model) {
        return model.hasId();
    }

    private String getTableName(Class<? extends BaseModel>  modelClazz) {
        return modelClazz.getAnnotation(TableName.class).value();
    }

    private ColumnInfo getColumnInfo(String tableName) {
        return tableInfo.getColumnInfo(tableName);
    }

    private SQLiteDAO getSqLiteDAO() {
        return sqLiteDAO;
    }
}
