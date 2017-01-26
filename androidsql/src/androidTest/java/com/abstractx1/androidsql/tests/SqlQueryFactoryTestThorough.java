package com.abstractx1.androidsql.tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.abstractx1.androidsql.BaseInstrumentedTest;
import com.abstractx1.androidsql.ModelDAO;
import com.abstractx1.androidsql.TableInfo;
import com.abstractx1.androidsql.db.SQLiteDAO;
import com.abstractx1.androidsql.db.Schema;
import com.abstractx1.androidsql.factories.SqlQueryFactory;
import com.abstractx1.androidsql.models.TestModel;
import com.abstractx1.androidsql.schemas.TestSchemaAllTypesV1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SqlQueryFactoryTestThorough extends BaseInstrumentedTest {
    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Schema schema = new TestSchemaAllTypesV1();
        this.sqLiteDAO = new SQLiteDAO(appContext, DB_NAME, schema);
        sqLiteDAO.initializeDatabase();
    }

    @Test
    public void testBuildInsert_success() throws IllegalAccessException {
        TableInfo tableInfo = new TableInfo(sqLiteDAO);
        TestModel testModel = new TestModel();
        testModel.setByteField((byte) 0x7F);

        try {
            String sql = SqlQueryFactory.buildInsert(testModel, tableInfo.getColumnInfo(testModel.getTableName()));
            sqLiteDAO.insert(sql);
            ModelDAO modelDAO = new ModelDAO(InstrumentationRegistry.getTargetContext(), DB_NAME, new TestSchemaAllTypesV1());
            TestModel testModel1 = modelDAO.findById(TestModel.class, 1);
            assertEquals((byte) 0x7F, testModel1.getByteField());
            assertEquals(false, testModel1.getBooleanField());
        } catch (Exception e) {
            fail(Log.getStackTraceString(e));
        }
    }
}
