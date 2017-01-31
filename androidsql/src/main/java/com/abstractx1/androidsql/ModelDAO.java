package com.abstractx1.androidsql;

import android.content.Context;
import android.database.Cursor;

import com.abstractx1.androidsql.db.SQLiteDAO;
import com.abstractx1.androidsql.db.Schema;
import com.abstractx1.androidsql.factories.ModelFactory;
import com.abstractx1.androidsql.factories.SqlQueryFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

    //try, catch, finally close db. Cursor not return null, just check if cursor.isEmpty(); cursor.moveToFirst();
    public <T extends BaseModel> T findById(Class<T> modelClazz, long id) throws NoSuchFieldException, InstantiationException, IllegalAccessException, ParseException {
        String tableName = getTableName(modelClazz);
        String query = SqlQueryFactory.buildFindById(tableName, id);
        List<T> models = rawQuery(modelClazz, query);

        if (models.size() == 0) {
            return null;
        } else {
            return models.get(0);
        }
    }

    public <T extends BaseModel> T save(T model) throws IllegalAccessException, ParseException, NoSuchFieldException, InstantiationException {
        String tableName = getTableName(model.getClass());

        if (hasRecord(model)) {
            String updateSql = SqlQueryFactory.buildUpdate(model, getColumnInfo(tableName));
            getSqLiteDAO().exec(updateSql);
            T savedModel = (T) findById(model.getClass(), model.getId());
            return savedModel;
        } else {
            String insertSql = SqlQueryFactory.buildInsert(model, getColumnInfo(tableName));
            long id = getSqLiteDAO().insert(insertSql);
            T savedModel = (T) findById(model.getClass(), id);
            return savedModel;
        }
    }

    public <T extends BaseModel> List<T> findAll(Class<T> modelClazz) throws NoSuchFieldException, InstantiationException, ParseException, IllegalAccessException {
        String tableName = getTableName(modelClazz);
        String query = SqlQueryFactory.buildFindAll(tableName);
        List<T> models = rawQuery(modelClazz, query);

        return models;
    }

    public <T extends BaseModel> List<T> where(Class<T> modelClazz, String rawWhereClause) throws NoSuchFieldException, InstantiationException, ParseException, IllegalAccessException {
        String tableName = getTableName(modelClazz);
        String query = SqlQueryFactory.buildRawWhere(tableName, rawWhereClause);
        List<T> models = rawQuery(modelClazz, query);

        return models;
    }

    private <T extends BaseModel> List<T> rawQuery(Class<T> modelClazz, String rawQuery) throws NoSuchFieldException, InstantiationException, ParseException, IllegalAccessException {
        String tableName = getTableName(modelClazz);

        List<T> models = new ArrayList<T>();
        Cursor cursor = getSqLiteDAO().query(rawQuery);

        try {
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    T model = ModelFactory.build(modelClazz, getColumnInfo(tableName), cursor);
                    models.add(model);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return models;
    }

    public void delete(BaseModel model) {
        String tableName = getTableName(model.getClass());

        if (hasRecord(model)) {
            String deleteSql = SqlQueryFactory.buildDeleteById(tableName, model.getId());
            getSqLiteDAO().exec(deleteSql);
        }
    }

    public void finish() {
        sqLiteDAO.close();
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
