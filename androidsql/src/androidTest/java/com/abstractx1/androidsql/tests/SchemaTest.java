package com.abstractx1.androidsql.tests;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abstractx1.androidsql.BaseInstrumentedTest;
import com.abstractx1.androidsql.db.SQLiteDAO;
import com.abstractx1.androidsql.db.Schema;
import com.abstractx1.androidsql.schemas.TestSchemaV2;
import com.abstractx1.androidsql.schemas.TestSchemaV3;
import com.abstractx1.androidsql.schemas.TestSchemaV4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SchemaTest extends BaseInstrumentedTest {
    @Test
    public void testSchemaCreation() {
        List<String> tableNames = getTableNames();
        List<String> expectedTableNames = Arrays.asList("projects");
        assertEquals(true, listEqualsNoOrder(expectedTableNames, tableNames));

        List<String> actualColumns = getColumnsNames("projects");
        List<String> expectedColumns = Arrays.asList("id", "name", "created_at");
        assertEquals(true, listEqualsNoOrder(expectedColumns, actualColumns));
    }

    @Test
    public void testSchemaSingleUpgrade() {
        testSchemaCreation();

        Context appContext = InstrumentationRegistry.getTargetContext();
        Schema newSchema = new TestSchemaV2();
        sqLiteDAO = new SQLiteDAO(appContext, DB_NAME, newSchema);

        List<String> tableNames = getTableNames();
        List<String> expectedTableNames = Arrays.asList("projects");
        assertEquals(true, listEqualsNoOrder(expectedTableNames, tableNames));

        List<String> actualColumns = getColumnsNames("projects");
        List<String> expectedColumns = Arrays.asList("id", "name", "description", "created_at");
        assertEquals(true, listEqualsNoOrder(expectedColumns, actualColumns));
    }

    @Test
    public void testSchemaDoubleUpgrade() {
        testSchemaCreation();

        Context appContext = InstrumentationRegistry.getTargetContext();
        Schema newSchema = new TestSchemaV3();
        sqLiteDAO = new SQLiteDAO(appContext, DB_NAME, newSchema);

        List<String> tableNames = getTableNames();
        List<String> expectedTableNames = Arrays.asList("projects");
        assertEquals(true, listEqualsNoOrder(expectedTableNames, tableNames));

        List<String> actualColumns = getColumnsNames("projects");
        List<String> expectedColumns = Arrays.asList("id", "name", "description", "number", "created_at");
        assertEquals(true, listEqualsNoOrder(expectedColumns, actualColumns));
    }

    @Test
    public void testSchemaSingleUpgradeReplaceTable() {
        testSchemaCreation();

        Context appContext = InstrumentationRegistry.getTargetContext();
        Schema newSchema = new TestSchemaV4();
        sqLiteDAO = new SQLiteDAO(appContext, DB_NAME, newSchema);

        List<String> tableNames = getTableNames();
        List<String> expectedTableNames = Arrays.asList("projects");
        assertEquals(true, listEqualsNoOrder(expectedTableNames, tableNames));

        List<String> actualColumns = getColumnsNames("projects");
        List<String> expectedColumns = Arrays.asList("id", "name", "number", "created_at");
        assertEquals(true, listEqualsNoOrder(expectedColumns, actualColumns));
    }

    public List<String> getTableNames() {
        Cursor cursor = getSqLiteDAO().query("SELECT name FROM sqlite_master WHERE type ='table'");
        List<String> tableNames = new ArrayList<>();

        if (cursor != null) {
            do {
                tableNames.add(cursor.getString(0));
            } while (cursor.moveToNext());

            cursor.close();
        }

        tableNames.remove("android_metadata");
        tableNames.remove("sqlite_sequence");

        return tableNames;
    }

    public List<String> getColumnsNames(String tableName) {
        List<String> actualColumns = new ArrayList<>();

        Cursor cursor = getSqLiteDAO().query(String.format("PRAGMA table_info(%s)", tableName));
        do {
            actualColumns.add(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        } while (cursor.moveToNext());
        cursor.close();

        return  actualColumns;
    }
}
