package com.abstractx1.androidsql.tests;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abstractx1.androidsql.BaseInstrumentedTest;
import com.abstractx1.androidsql.db.SQLiteSession;

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
class ThreadTest extends Thread {
    private SQLiteSession sqLiteSession;
    private Map<Integer,String> projectIdNameMap;
    private String name;

    public ThreadTest(String str, SQLiteSession sqLiteSession) {
        super(str);
        this.name = str;
        this.sqLiteSession = sqLiteSession;
        projectIdNameMap = new HashMap<>();
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            String projectName = String.format("%s_%d", name, i);
            int id = sqLiteSession.insert(String.format("INSERT INTO projects (name) VALUES ('%s')", projectName));
            projectIdNameMap.put(id, projectName);
        }
    }

    public Map<Integer, String> getProjectIdNameMap() {
        return projectIdNameMap;
    }
}

@RunWith(AndroidJUnit4.class)
public class SQLiteSessionTest extends BaseInstrumentedTest {
    @Test
    public void testInsert() {
        int id = getSqLiteSession().insert("INSERT INTO projects (name) VALUES ('MyOther''s Project')");
        Cursor cursor = getSqLiteSession().query("SELECT COUNT(*) FROM projects");
        assertEquals(1, cursor.getInt(0));
        cursor.close();
        cursor = getSqLiteSession().query(String.format("SELECT * FROM projects WHERE id = %d", id));
        assertNotNull(cursor);
        assertEquals("MyOther's Project", cursor.getString(cursor.getColumnIndex("name")));
        cursor.close();
    }

    @Test
    public void testInsertMultiThread() throws InterruptedException {
        ThreadTest thread1 = new ThreadTest("Thread1", getSqLiteSession());
        ThreadTest thread2 = new ThreadTest("Thread2", getSqLiteSession());
        ThreadTest thread3 = new ThreadTest("Thread3", getSqLiteSession());
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
                Cursor cursor = getSqLiteSession().query(String.format("SELECT name FROM projects WHERE id = %d", id));
                assertNotNull(cursor);
                assertEquals(name, cursor.getString(cursor.getColumnIndex("name")));
                cursor.close();
            }
        }
    }
}