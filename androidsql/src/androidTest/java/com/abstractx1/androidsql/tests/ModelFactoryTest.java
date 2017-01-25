package com.abstractx1.androidsql.tests;

import android.database.Cursor;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.abstractx1.androidsql.BaseInstrumentedTest;
import com.abstractx1.androidsql.TableInfo;
import com.abstractx1.androidsql.factories.ModelFactory;
import com.abstractx1.androidsql.models.Project;

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
    @Test
    public void testSchemaCreation() {
        getSqLiteDAO().insert("INSERT INTO projects (name) VALUES ('MyDiary')");
        Cursor cursor = getSqLiteDAO().query("SELECT * FROM projects WHERE name = 'MyDiary';");
        TableInfo tableInfo = new TableInfo(getSqLiteDAO());
        try {
            Project project = ModelFactory.build(Project.class, tableInfo.getColumnInfo("projects"), cursor);
            assertNotNull(project);
            assertEquals("MyDiary", project.getName());
            assertEquals(1, project.getId());
        } catch (Exception e) {
            fail(e.getMessage() + Log.getStackTraceString(e));
        }
    }
}
