package com.abstractx1.androidsql.tests;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abstractx1.androidsql.BaseInstrumentedTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends BaseInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.abstractx1.androidsql.test", appContext.getPackageName());
    }

    @Test
    public void testInsert() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        int id = getSqLiteSession().insert("INSERT INTO projects (name) VALUES ('MyOther''s Project')");
        Cursor cursor = getSqLiteSession().query("SELECT COUNT(*) FROM projects");
        assertEquals(1, cursor.getInt(0));
        cursor.close();
        cursor = getSqLiteSession().query(String.format("SELECT * FROM projects WHERE id = %d", id));
        assertNotNull(cursor);
        assertEquals("MyOther's Project", cursor.getString(cursor.getColumnIndex("name")));
        cursor.close();
    }
}
