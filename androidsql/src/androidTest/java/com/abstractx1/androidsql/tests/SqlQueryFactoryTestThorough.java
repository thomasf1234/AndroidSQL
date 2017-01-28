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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    public void testBuildInsert_success() throws IllegalAccessException, ParseException {
        TableInfo tableInfo = new TableInfo(sqLiteDAO);
        TestModel testModel = new TestModel();
        testModel.setByteField(Byte.MAX_VALUE);
        testModel.setShortField(Short.MAX_VALUE);
        testModel.setIntFieldA(Short.MAX_VALUE + 1);
        testModel.setIntFieldB(Integer.MAX_VALUE);
        testModel.setLongField(Long.MAX_VALUE);

        testModel.setStringFieldA("letter a");
        testModel.setStringFieldB("letter b");
        testModel.setStringFieldC("letter c");
        testModel.setStringFieldD("letter d");
        testModel.setStringFieldE("letter e");
        testModel.setStringFieldF("letter f");
        testModel.setStringFieldG("letter g");
        testModel.setStringFieldH("letter h");

        testModel.setByteArrayField(new byte[] {0x00, 0x0A, 0x7F});

        testModel.setDoubleFieldA(12.666428727762771);
        testModel.setDoubleFieldB(12.666428727762772);
        testModel.setDoubleFieldC(12.666428727762773);
        testModel.setFloatField(12.666429f);

        testModel.setBooleanField(true);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = dateFormat.parse("2017-01-25");
        testModel.setDateField(date);

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date dateTime = dateTimeFormat.parse("2017-01-25 08:32:01");
        testModel.setDateTimeField(dateTime);

        try {
            String sql = SqlQueryFactory.buildInsert(testModel, tableInfo.getColumnInfo(testModel.getTableName()));
            sqLiteDAO.insert(sql);
            ModelDAO modelDAO = new ModelDAO(InstrumentationRegistry.getTargetContext(), DB_NAME, new TestSchemaAllTypesV1());
            TestModel testModel1 = modelDAO.findById(TestModel.class, 1);
            assertEquals(Byte.MAX_VALUE, testModel1.getByteField());
            assertEquals(Short.MAX_VALUE, testModel1.getShortField());
            assertEquals(Short.MAX_VALUE + 1, testModel1.getIntFieldA());
            assertEquals(Integer.MAX_VALUE, testModel1.getIntFieldB());
            assertEquals(Long.MAX_VALUE, testModel1.getLongField());

            assertEquals("letter a", testModel1.getStringFieldA());
            assertEquals("letter b", testModel1.getStringFieldB());
            assertEquals("letter c", testModel1.getStringFieldC());
            assertEquals("letter d", testModel1.getStringFieldD());
            assertEquals("letter e", testModel1.getStringFieldE());
            assertEquals("letter f", testModel1.getStringFieldF());
            assertEquals("letter g", testModel1.getStringFieldG());
            assertEquals("letter h", testModel1.getStringFieldH());

            assertNotNull(testModel1.getByteArrayField());
            byte[] byteArray = testModel1.getByteArrayField();
            assertEquals((byte) 0x00, byteArray[0]);
            assertEquals((byte) 0x0A, byteArray[1]);
            assertEquals((byte) 0x7F, byteArray[2]);

            assertEquals(12.666428727762771, testModel1.getDoubleFieldA(), DELTA_DOUBLE);
            assertEquals(12.666428727762772, testModel1.getDoubleFieldB(), DELTA_DOUBLE);
            assertEquals(12.666428727762773, testModel1.getDoubleFieldC(), DELTA_DOUBLE);
            assertEquals(12.666429f, testModel1.getFloatField(), DELTA_FLOAT);

            assertEquals(true, testModel1.getBooleanField());

            assertEquals(date, testModel1.getDateField());
            assertEquals(dateTime, testModel1.getDateTimeField());
        } catch (Exception e) {
            fail(Log.getStackTraceString(e));
        }
    }
}
