package com.abstractx1.androidsql.tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.abstractx1.androidsql.BaseInstrumentedTest;
import com.abstractx1.androidsql.TableInfo;
import com.abstractx1.androidsql.db.SQLiteDAO;
import com.abstractx1.androidsql.db.Schema;
import com.abstractx1.androidsql.factories.SqlQueryFactory;
import com.abstractx1.androidsql.models.Project;
import com.abstractx1.androidsql.schemas.TestSchemaV1;

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
public class SqlQueryFactoryTest extends BaseInstrumentedTest {
    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Schema schema = new TestSchemaV1();
        this.sqLiteDAO = new SQLiteDAO(appContext, DB_NAME, schema);
        sqLiteDAO.initializeDatabase();
    }

    @Test
    public void testBuildInsert_success() throws IllegalAccessException {
        TableInfo tableInfo = new TableInfo(sqLiteDAO);
        Project project = new Project();
        project.setName("MyProject");

        try {
            String sql = SqlQueryFactory.buildInsert(project, tableInfo.getColumnInfo(project.getTableName()));
            assertEquals("INSERT INTO projects (name) VALUES ('MyProject')", sql);
        } catch (Exception e) {
            fail(Log.getStackTraceString(e));
        }
    }
}
