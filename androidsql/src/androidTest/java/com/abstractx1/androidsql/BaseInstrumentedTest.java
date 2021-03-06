package com.abstractx1.androidsql;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abstractx1.androidsql.db.SQLiteDAO;
import com.abstractx1.androidsql.db.Schema;
import com.abstractx1.androidsql.schemas.TestSchemaV1;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public abstract class BaseInstrumentedTest {
    public static final double DELTA_DOUBLE = 0.000000000000001;
    public static final float DELTA_FLOAT = 0.000001f;
    protected static final String DB_NAME = "com.abstractx1.sqlitemodel.test.db";
    protected SQLiteDAO sqLiteDAO;

    @Before
    public void setUpDeleteDatabase() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.deleteDatabase(DB_NAME);
    }

    public static <T> boolean listEqualsNoOrder(List<T> l1, List<T> l2) {
        final Set<T> s1 = new HashSet<>(l1);
        final Set<T> s2 = new HashSet<>(l2);

        return s1.equals(s2);
    }
}
