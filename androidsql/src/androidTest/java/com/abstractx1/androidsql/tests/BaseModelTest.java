package com.abstractx1.androidsql.tests;

import android.support.test.runner.AndroidJUnit4;

import com.abstractx1.androidsql.BaseInstrumentedTest;
import com.abstractx1.androidsql.models.TestModel;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BaseModelTest extends BaseInstrumentedTest {
    @Test
    public void testGetTableName() {
        TestModel testModel = new TestModel();
        assertEquals("test_table", testModel.getTableName());
    }
}
