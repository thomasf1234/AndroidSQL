package com.abstractx1.androidsql.tests;

import android.support.test.runner.AndroidJUnit4;

import com.abstractx1.androidsql.BaseInstrumentedTest;
import com.abstractx1.androidsql.BaseModel;
import com.abstractx1.androidsql.TableName;
import com.abstractx1.androidsql.models.TestModel;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BaseModelTest extends BaseInstrumentedTest {
    @TableName("my_table")
    class MyModel extends BaseModel {
        public void setId(long id) {
            this.id = id;
        }
    }

    @Test
    public void testGetTableName() {
        MyModel myModel = new MyModel();
        assertEquals("my_table", myModel.getTableName());
    }

    @Test
    public void hasId() throws NoSuchFieldException, IllegalAccessException {
        MyModel myModel = new MyModel();
        assertEquals(false, myModel.hasId());
        myModel.setId(1L);
        assertEquals(true, myModel.hasId());
    }

}
