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
import com.abstractx1.androidsql.models.Project;
import com.abstractx1.androidsql.schemas.TestSchemaMultipleTablesV1;
import com.abstractx1.androidsql.schemas.TestSchemaV1;
import com.abstractx1.androidsql.schemas.TestSchemaV2;

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
        Schema schema = new TestSchemaMultipleTablesV1();
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

    @Test
    public void testBuildUpdate_projectSuccess() throws IllegalAccessException {
        long id = sqLiteDAO.insert("INSERT INTO projects (name) VALUES ('MyProject')");
        TableInfo tableInfo = new TableInfo(sqLiteDAO);
        Project project = new Project();
        project.setId(id);
        project.setName("MyUpdatedProject");

        try {
            String sql = SqlQueryFactory.buildUpdate(project, tableInfo.getColumnInfo(project.getTableName()));
            assertEquals(String.format("UPDATE projects SET name = 'MyUpdatedProject' WHERE id = %d", id), sql);
        } catch (Exception e) {
            fail(Log.getStackTraceString(e));
        }
    }

    @Test
    public void testBuildUpdate_taskSuccess() throws IllegalAccessException {
//        Mode
//        TableInfo tableInfo = new TableInfo(sqLiteDAO);
//        Project project = new Project();
//        project.setId(id);
//        project.setName("MyUpdatedProject");
//
//        try {
//            String sql = SqlQueryFactory.buildUpdate(project, tableInfo.getColumnInfo(project.getTableName()));
//            assertEquals(String.format("UPDATE projects SET name = 'MyUpdatedProject' WHERE id = %d", id), sql);
//        } catch (Exception e) {
//            fail(Log.getStackTraceString(e));
//        }
    }
}
