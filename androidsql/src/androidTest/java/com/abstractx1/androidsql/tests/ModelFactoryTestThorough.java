package com.abstractx1.androidsql.tests;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.abstractx1.androidsql.BaseInstrumentedTest;
import com.abstractx1.androidsql.TableInfo;
import com.abstractx1.androidsql.db.SQLiteDAO;
import com.abstractx1.androidsql.db.Schema;
import com.abstractx1.androidsql.factories.ModelFactory;
import com.abstractx1.androidsql.models.TestModel;
import com.abstractx1.androidsql.schemas.TestSchemaAllTypesV1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
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
public class ModelFactoryTestThorough extends BaseInstrumentedTest {
    static final byte MAX_TINYINT = (byte) 127;
    static final short MAX_SMALLINT= (short) 32767;
    static final int MAX_MEDIUMINT = 8388607;
    static final int MAX_INT = 2147483647;
    static final long MAX_BIGINT = 9223372036854775807L;

    static final byte MIN_TINYINT = (byte) -128;
    static final short MIN_SMALLINT= (short) -32768 ;
    static final int MIN_MEDIUMINT = -8388608;
    static final int MIN_INT = -2147483648;
    static final long MIN_BIGINT = -9223372036854775808L;

    static final byte NULL_TERMINATOR = (byte) 0x00;

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Schema schema = new TestSchemaAllTypesV1();
        this.sqLiteDAO = new SQLiteDAO(appContext, DB_NAME, schema);
        sqLiteDAO.initializeDatabase();
    }

    @Test
    public void testModelBuild_success1() {
        String insertSql = new StringBuilder()
                .append("INSERT INTO test_table \n")
                .append("(byte_field, short_field, int_field_a, int_field_b, long_field,")
                .append("string_field_a, string_field_b, string_field_c, string_field_d, string_field_e, string_field_f, string_field_g, string_field_h,")
                .append("byte_array_field, double_field_a, double_field_b, double_field_c, float_field, boolean_field, date_field, datetime_field)")
                .append("VALUES (127, 32767, 8388607, 2147483647, 9223372036854775807,")
                .append("'is twenty chars long'," +
                        "'This is a column for VARCHAR(255)'," +
                        "'This is a column for VARYING CHARACTER(255)'," +
                        "'This is a column for NCHAR(55)'," +
                        "'This is a column for NATIVE CHARACTER(70)'," +
                        "'This is a column for NVARCHAR(100)'," +
                        "'This is a column for TEXT'," +
                        "'This is a column for CLOB',")
                .append("'abc',")
                .append("12.666428727762771," +
                        "12.666428727762772," +
                        "12.666428727762773," +
                        "12.666429,")
                .append("1, '2017-01-25', '2017-01-25 13:21:08')").toString();

        sqLiteDAO.insert(insertSql);

        Cursor cursor = sqLiteDAO.query("SELECT * FROM test_table LIMIT 1;");
        TableInfo tableInfo = new TableInfo(sqLiteDAO);
        try {
            TestModel testModel = ModelFactory.build(TestModel.class, tableInfo.getColumnInfo("test_table"), cursor);
            assertNotNull(testModel);

            assertEquals(MAX_TINYINT, testModel.getByteField() );
            assertEquals(MAX_SMALLINT, testModel.getShortField() );
            assertEquals(MAX_MEDIUMINT, testModel.getIntFieldA() );
            assertEquals(MAX_INT, testModel.getIntFieldB() );
            assertEquals(MAX_BIGINT, testModel.getLongField() );

            assertEquals("is twenty chars long", testModel.getStringFieldA() );
            assertEquals("This is a column for VARCHAR(255)", testModel.getStringFieldB() );
            assertEquals("This is a column for VARYING CHARACTER(255)", testModel.getStringFieldC() );
            assertEquals("This is a column for NCHAR(55)", testModel.getStringFieldD() );
            assertEquals("This is a column for NATIVE CHARACTER(70)", testModel.getStringFieldE() );
            assertEquals("This is a column for NVARCHAR(100)", testModel.getStringFieldF() );
            assertEquals("This is a column for TEXT", testModel.getStringFieldG() );
            assertEquals("This is a column for CLOB", testModel.getStringFieldH() );

            assertEquals(4, testModel.getByteArrayField().length );
            assertEquals((byte) 0x61, testModel.getByteArrayField()[0] );
            assertEquals((byte) 0x62, testModel.getByteArrayField()[1] );
            assertEquals((byte) 0x63, testModel.getByteArrayField()[2] );
            assertEquals(NULL_TERMINATOR, testModel.getByteArrayField()[3] );

            assertEquals(12.666428727762771, testModel.getDoubleFieldA(), DELTA_DOUBLE);
            assertEquals(12.666428727762772, testModel.getDoubleFieldB(), DELTA_DOUBLE);
            assertEquals(12.666428727762773, testModel.getDoubleFieldC(), DELTA_DOUBLE);
            assertEquals(12.666429f, testModel.getFloatField(), DELTA_FLOAT);

            assertEquals(true, testModel.getBooleanField() );

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            assertEquals(dateFormat.parse("2017-01-25"), testModel.getDateField() );

            SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            datetimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            assertEquals(datetimeFormat.parse("2017-01-25 13:21:08"), testModel.getDateTimeField() );
        } catch (Exception e) {
            fail(Log.getStackTraceString(e));
        }
    }

    @Test
    public void testModelBuild_success2() {
        String insertSql = new StringBuilder()
                .append("INSERT INTO test_table \n")
                .append("(byte_field, short_field, int_field_a, int_field_b, long_field,")
                .append("string_field_a, string_field_b, string_field_c, string_field_d, string_field_e, string_field_f, string_field_g, string_field_h,")
                .append("byte_array_field, double_field_a, double_field_b, double_field_c, float_field, boolean_field, date_field, datetime_field)")
                .append("VALUES (-128, -32768, -8388608, -2147483648, -9223372036854775808,")
                .append("'is twenty chars long'," +
                        "'This is a column for VARCHAR(255)'," +
                        "'This is a column for VARYING CHARACTER(255)'," +
                        "'This is a column for NCHAR(55)'," +
                        "'This is a column for NATIVE CHARACTER(70)'," +
                        "'This is a column for NVARCHAR(100)'," +
                        "'This is a column for TEXT'," +
                        "'This is a column for CLOB',")
                .append("'abc',")
                .append("12.666428727762771," +
                        "12.666428727762772," +
                        "12.666428727762773," +
                        "12.666429,")
                .append("1, '2017-01-25', '2017-01-25 13:21:08')").toString();

        sqLiteDAO.insert(insertSql);

        Cursor cursor = sqLiteDAO.query("SELECT * FROM test_table LIMIT 1;");
        TableInfo tableInfo = new TableInfo(sqLiteDAO);
        try {
            TestModel testModel = ModelFactory.build(TestModel.class, tableInfo.getColumnInfo("test_table"), cursor);
            assertNotNull(testModel);

            assertEquals(MIN_TINYINT, testModel.getByteField() );
            assertEquals(MIN_SMALLINT, testModel.getShortField() );
            assertEquals(MIN_MEDIUMINT, testModel.getIntFieldA() );
            assertEquals(MIN_INT, testModel.getIntFieldB() );
            assertEquals(MIN_BIGINT, testModel.getLongField() );

            assertEquals("is twenty chars long", testModel.getStringFieldA() );
            assertEquals("This is a column for VARCHAR(255)", testModel.getStringFieldB() );
            assertEquals("This is a column for VARYING CHARACTER(255)", testModel.getStringFieldC() );
            assertEquals("This is a column for NCHAR(55)", testModel.getStringFieldD() );
            assertEquals("This is a column for NATIVE CHARACTER(70)", testModel.getStringFieldE() );
            assertEquals("This is a column for NVARCHAR(100)", testModel.getStringFieldF() );
            assertEquals("This is a column for TEXT", testModel.getStringFieldG() );
            assertEquals("This is a column for CLOB", testModel.getStringFieldH() );

            assertEquals(4, testModel.getByteArrayField().length );
            assertEquals((byte) 0x61, testModel.getByteArrayField()[0] );
            assertEquals((byte) 0x62, testModel.getByteArrayField()[1] );
            assertEquals((byte) 0x63, testModel.getByteArrayField()[2] );
            assertEquals(NULL_TERMINATOR, testModel.getByteArrayField()[3] );

            assertEquals(12.666428727762771, testModel.getDoubleFieldA(), DELTA_DOUBLE);
            assertEquals(12.666428727762772, testModel.getDoubleFieldB(), DELTA_DOUBLE);
            assertEquals(12.666428727762773, testModel.getDoubleFieldC(), DELTA_DOUBLE);
            assertEquals(12.666429f, testModel.getFloatField(), DELTA_FLOAT);

            assertEquals(true, testModel.getBooleanField() );

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            assertEquals(dateFormat.parse("2017-01-25"), testModel.getDateField() );

            SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            datetimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            assertEquals(datetimeFormat.parse("2017-01-25 13:21:08"), testModel.getDateTimeField() );
        } catch (Exception e) {
            fail(Log.getStackTraceString(e));
        }
    }
}
