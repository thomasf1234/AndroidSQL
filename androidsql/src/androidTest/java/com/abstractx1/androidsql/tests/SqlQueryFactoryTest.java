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
import com.abstractx1.androidsql.models.Task;
import com.abstractx1.androidsql.schemas.TestSchemaMultipleTablesV1;
import com.abstractx1.androidsql.schemas.TestSchemaV1;
import com.abstractx1.androidsql.schemas.TestSchemaV2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SqlQueryFactoryTest extends BaseInstrumentedTest {
    public ModelDAO modelDAO;

    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Schema schema = new TestSchemaMultipleTablesV1();
        this.sqLiteDAO = new SQLiteDAO(appContext, DB_NAME, schema);
        this.modelDAO = new ModelDAO(appContext, DB_NAME, schema);
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
    public void testBuildUpdate_projectSuccess() throws IllegalAccessException, ParseException, NoSuchFieldException, InstantiationException {
        Project project = new Project();
        project.setName("MyProject");
        Project savedProject = modelDAO.save(project);

        savedProject.setName("MyUpdatedProject");
        TableInfo tableInfo = new TableInfo(sqLiteDAO);

        try {
            String sql = SqlQueryFactory.buildUpdate(savedProject, tableInfo.getColumnInfo(project.getTableName()));
            assertEquals(String.format("UPDATE projects SET name = 'MyUpdatedProject' WHERE id = %d", savedProject.getId()), sql);
        } catch (Exception e) {
            fail(Log.getStackTraceString(e));
        }
    }

    @Test
    public void testBuildUpdate_taskSuccess() throws IllegalAccessException, ParseException, NoSuchFieldException, InstantiationException {
        Project project = new Project();
        project.setName("MyProject");
        Project savedProject = modelDAO.save(project);

        Task task = new Task();
        task.setNumber(3);
        task.setTitle("MyTitle");
        task.setDescription("This is the description");
        task.setType("Defect");
        task.setStatus("Todo");
        task.setProjectId(savedProject.getId());

        Task savedTask = modelDAO.save(task);

        savedTask.setTitle("MyChangedTitle");
        savedTask.setDescription(null);
        savedTask.setStatus("In-Progress");
        savedTask.setNumber(4);
        TableInfo tableInfo = new TableInfo(sqLiteDAO);

        try {
            String sql = SqlQueryFactory.buildUpdate(savedTask, tableInfo.getColumnInfo(task.getTableName()));
            assertEquals(String.format("UPDATE tasks SET number = 4, description = NULL, title = 'MyChangedTitle', status = 'In-Progress', type = 'Defect', project_id = 1 WHERE id = %d", savedTask.getId()), sql);
        } catch (Exception e) {
            fail(Log.getStackTraceString(e));
        }
    }

    @Test
    public void testBuildDeleteById_taskSuccess() throws IllegalAccessException, ParseException, NoSuchFieldException, InstantiationException {
        Project project = new Project();
        project.setName("MyProject");
        Project savedProject = modelDAO.save(project);

        try {
            String sql = SqlQueryFactory.buildDeleteById(savedProject.getTableName(), savedProject.getId());
            assertEquals(String.format("DELETE FROM projects WHERE id = %d", savedProject.getId()), sql);
        } catch (Exception e) {
            fail(Log.getStackTraceString(e));
        }
    }
}
