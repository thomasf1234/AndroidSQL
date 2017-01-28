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
import com.abstractx1.androidsql.models.Project;
import com.abstractx1.androidsql.models.ProjectFieldIncorrectType;
import com.abstractx1.androidsql.models.ProjectMissingField;
import com.abstractx1.androidsql.schemas.TestSchemaV1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ModelFactoryTest extends BaseInstrumentedTest {
    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Schema schema = new TestSchemaV1();
        this.sqLiteDAO = new SQLiteDAO(appContext, DB_NAME, schema);
        sqLiteDAO.initializeDatabase();
        sqLiteDAO.insert("INSERT INTO projects (name) VALUES ('MyDiary')");
    }

    @Test
    public void testModelBuild_success() {
        Cursor cursor = sqLiteDAO.query("SELECT * FROM projects WHERE name = 'MyDiary';");
        TableInfo tableInfo = new TableInfo(sqLiteDAO);
        try {
            Project project = ModelFactory.build(Project.class, tableInfo.getColumnInfo("projects"), cursor);
            assertNotNull(project);
            assertEquals("MyDiary", project.getName());
            assertEquals(1, project.getId());
        } catch (Exception e) {
            fail(Log.getStackTraceString(e));
        }
    }

    @Test
    public void testModelBuild_missingField() {
        Cursor cursor = sqLiteDAO.query("SELECT * FROM projects WHERE name = 'MyDiary';");
        TableInfo tableInfo = new TableInfo(sqLiteDAO);
        try {
            ModelFactory.build(ProjectMissingField.class, tableInfo.getColumnInfo("projects"), cursor);
            fail("Should not have been able to produce incomplete model");
        } catch (Exception e) {
            assertEquals(NoSuchFieldException.class, e.getClass());
            assertEquals("Could not find an associated field on class: 'class com.abstractx1.androidsql.models.ProjectMissingField', column: 'name'", e.getMessage());
        }
    }

    @Test
    public void testModelBuild_fieldIncorrectType() {
        Cursor cursor = sqLiteDAO.query("SELECT * FROM projects WHERE name = 'MyDiary';");
        TableInfo tableInfo = new TableInfo(sqLiteDAO);
        try {
            ModelFactory.build(ProjectFieldIncorrectType.class, tableInfo.getColumnInfo("projects"), cursor);
            fail("Should not have been able to produce incomplete model");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("field com.abstractx1.androidsql.models.ProjectFieldIncorrectType.name has type int, got java.lang.String", e.getMessage());
        }
    }
}
