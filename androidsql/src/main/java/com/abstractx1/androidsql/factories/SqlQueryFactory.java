package com.abstractx1.androidsql.factories;

import com.abstractx1.androidsql.Column;
import com.abstractx1.androidsql.ColumnInfo;
import com.abstractx1.androidsql.SQLite;
import com.abstractx1.androidsql.BaseModel;

import java.lang.reflect.Field;

/**
 * Created by tfisher on 03/01/2017.
 */

public class SqlQueryFactory extends Factory {
    public static String buildInsert(BaseModel model, ColumnInfo columnInfo) throws IllegalAccessException {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        boolean isFirst = true;
        for (String columnName : columnInfo.getColumnNames()) {
            Field field = findFieldForColumnName(model.getClass(), columnName);

            if (isColumnReadOnly(field)) {
                continue;
            } else {
                String typeName = columnInfo.getTypeName(columnName);
                boolean origAccessibility = field.isAccessible();
                Object fieldValue;

                try {
                    field.setAccessible(true);
                    fieldValue = field.get(model);
                } catch (IllegalAccessException e) {
                    throw e;
                } finally {
                    field.setAccessible(origAccessibility);
                }

                if (fieldValue == null) {
                    continue;
                } else {
                    String value = SQLite.toString(fieldValue, typeName);

                    if (isFirst) {
                        isFirst = false;
                        columns.append(columnName);
                        values.append(value);
                    } else {
                        columns.append(",");
                        columns.append(columnName);
                        values.append(",");
                        values.append(value);
                    }
                }
            }
        }

        String query = "INSERT INTO " + model.getTableName() + " (" + columns + ") " + "VALUES (" + values + ")";
        return query;
    }

    public static String buildUpdate(BaseModel model, ColumnInfo columnInfo) throws IllegalAccessException {
        StringBuilder updates = new StringBuilder();

        boolean isFirst = true;
        for (String columnName : columnInfo.getColumnNames()) {
            Field field = findFieldForColumnName(model.getClass(), columnName);

            if (isColumnReadOnly(field)) {
                continue;
            } else {
                String typeName = columnInfo.getTypeName(columnName);
                boolean origAccessibility = field.isAccessible();
                Object fieldValue;

                try {
                    field.setAccessible(true);
                    fieldValue = field.get(model);
                } catch (IllegalAccessException e) {
                    throw e;
                } finally {
                    field.setAccessible(origAccessibility);
                }

                String value = SQLite.toString(fieldValue, typeName);

                if (isFirst) {
                    isFirst = false;
                    updates.append(String.format("%s = %s", columnName, value));
                } else {
                    updates.append(String.format(", %s = %s", columnName, value));
                }
            }

        }

        String idString = SQLite.toString(model.getId(), SQLite.TYPENAME_BIGINT);

        String query = "UPDATE " + model.getTableName() + " SET " + updates + " WHERE id = " + idString;
        return query;
    }

    public static String buildFindAll(String tableName) {
        String query = "SELECT * FROM " + tableName;
        return query;
    }

    public static String buildRawQuery(String tableName, String rawClauses) {
        String query = "SELECT * FROM " + tableName + " " + rawClauses;
        return query;
    }

    public static String buildRawWhere(String tableName, String rawWhereClause) {
        String query = buildRawQuery(tableName, "WHERE " + rawWhereClause);
        return query;
    }

    public static String buildFindById(String tableName, long id) {
        String query = buildRawWhere(tableName, String.format("id = %d LIMIT 1", id));
        return query;
    }

    public static String buildDeleteById(String tableName, long id) {
        String query = String.format("DELETE FROM " + tableName + " WHERE id = %d", id);
        return query;
    }

    public static String buildTableInfo(String tableName) {
        return String.format("PRAGMA table_info(%s)", tableName);
    }

    private static boolean isColumnReadOnly(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        return columnAnnotation.readOnly();
    }
}
