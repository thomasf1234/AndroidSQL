package com.abstractx1.androidsql.tests;

import android.database.Cursor;
import android.support.test.runner.AndroidJUnit4;

import com.abstractx1.androidsql.BaseInstrumentedTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SchemaTest extends BaseInstrumentedTest {
    @Test
    public void testSchemaCreation() {
        Cursor cursor = getSqLiteSession().query("SELECT name FROM sqlite_master WHERE type ='table'");
        assertNotNull(cursor);
        List<String> tableNames = new ArrayList<>();

        do {
            tableNames.add(cursor.getString(0));
        } while (cursor.moveToNext());
        cursor.close();
        assertEquals(true, tableNames.contains("projects"));

        List<String> actualColumns = new ArrayList<>();
        cursor = getSqLiteSession().query("PRAGMA table_info(projects)");
        do {
            actualColumns.add(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        } while (cursor.moveToNext());
        cursor.close();

        ArrayList<String> expectedColumns = new ArrayList<>();
        expectedColumns.add("id");
        expectedColumns.add("name");
        expectedColumns.add("created_at");

        Collections.sort(expectedColumns);
        Collections.sort(actualColumns);
        assertEquals(true, actualColumns.equals(expectedColumns));


        assertEquals(true, tableNames.contains("tasks"));
        assertEquals(true, tableNames.contains("sub_tasks"));

        cursor.close();
    }
}
