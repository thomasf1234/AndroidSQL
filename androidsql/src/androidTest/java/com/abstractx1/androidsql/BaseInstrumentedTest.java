package com.abstractx1.androidsql;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abstractx1.androidsql.db.SQLiteSession;
import com.abstractx1.androidsql.db.Schema;
import com.abstractx1.androidsql.schemas.TestSchemaV1;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BaseInstrumentedTest {
    private static final String DB_NAME = "com.abstractx1.sqlitemodel.test.db";
    protected SQLiteSession sqLiteSession;

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.deleteDatabase(DB_NAME);
        Schema schema = new TestSchemaV1();
        sqLiteSession = new SQLiteSession(appContext, DB_NAME, schema);
        getSqLiteSession().initializeDatabase();
    }

    protected SQLiteSession getSqLiteSession() { return sqLiteSession; }
}