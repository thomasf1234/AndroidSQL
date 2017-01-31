package com.abstractx1.androidsql.tests;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abstractx1.androidsql.BaseInstrumentedTest;
import com.abstractx1.androidsql.db.SQLiteDAO;
import com.abstractx1.androidsql.db.Schema;
import com.abstractx1.androidsql.schemas.TestSchemaV1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class SQLiteSessionTest extends BaseInstrumentedTest {
    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Schema schema = new TestSchemaV1();
        this.sqLiteDAO = new SQLiteDAO(appContext, DB_NAME, schema);
        sqLiteDAO.initializeDatabase();
    }

    @Test
    public void testInsert() {
        int id = (int) sqLiteDAO.insert("INSERT INTO projects (name) VALUES ('MyOther''s Project')");
        Cursor cursor = sqLiteDAO.query("SELECT COUNT(*) FROM projects");
        cursor.moveToFirst();
        assertEquals(1, cursor.getInt(0));
        cursor.close();
        cursor = sqLiteDAO.query(String.format("SELECT * FROM projects WHERE id = %d", id));
        cursor.moveToFirst();
        assertEquals("MyOther's Project", cursor.getString(cursor.getColumnIndex("name")));
        cursor.close();
    }

    @Test
    public void testInsertMultiThread() throws InterruptedException {
        ThreadTest thread1 = new ThreadTest("Thread1", sqLiteDAO);
        ThreadTest thread2 = new ThreadTest("Thread2", sqLiteDAO);
        ThreadTest thread3 = new ThreadTest("Thread3", sqLiteDAO);
        ThreadTest[] threadTests = new ThreadTest[]{thread1, thread2, thread3};

        for (ThreadTest threadTest : threadTests) {
            threadTest.start();
        }
        for (ThreadTest threadTest : threadTests) {
            threadTest.join();
        }
        for (ThreadTest threadTest : threadTests) {
            for (Map.Entry<Integer, String> entry : threadTest.getProjectIdNameMap().entrySet()) {
                int id = entry.getKey();
                String name = entry.getValue();
                Cursor cursor = sqLiteDAO.query(String.format("SELECT name FROM projects WHERE id = %d", id));
                cursor.moveToFirst();
                assertEquals(name, cursor.getString(cursor.getColumnIndex("name")));
                cursor.close();
            }
        }
    }

    private class ThreadTest extends Thread {
        private SQLiteDAO sqLiteDAO;
        private Map<Integer,String> projectIdNameMap;
        private String name;

        public ThreadTest(String str, SQLiteDAO sqLiteDAO) {
            super(str);
            this.name = str;
            this.sqLiteDAO = sqLiteDAO;
            projectIdNameMap = new HashMap<>();
        }

        public void run() {
            for (int i = 0; i < 100; i++) {
                String projectName = String.format("%s_%d", name, i);
                int id = (int) sqLiteDAO.insert(String.format("INSERT INTO projects (name) VALUES ('%s')", projectName));
                projectIdNameMap.put(id, projectName);
            }
        }

        public Map<Integer, String> getProjectIdNameMap() {
            return projectIdNameMap;
        }
    }
}