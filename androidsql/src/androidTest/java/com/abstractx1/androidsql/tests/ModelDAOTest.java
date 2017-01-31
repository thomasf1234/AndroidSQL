package com.abstractx1.androidsql.tests;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abstractx1.androidsql.BaseInstrumentedTest;
import com.abstractx1.androidsql.ModelDAO;
import com.abstractx1.androidsql.db.Schema;
import com.abstractx1.androidsql.models.Project;
import com.abstractx1.androidsql.models.Task;
import com.abstractx1.androidsql.schemas.TestSchemaMultipleTablesV1;
import com.abstractx1.androidsql.schemas.TestSchemaV1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ModelDAOTest extends BaseInstrumentedTest {
    ModelDAO modelDAO;
    @Before
    public void setUp() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Schema schema = new TestSchemaMultipleTablesV1();
        this.modelDAO = new ModelDAO(appContext, DB_NAME, schema);
    }

    @Test
    public void testModelBuild_success() throws IllegalAccessException, ParseException, NoSuchFieldException, InstantiationException {
        Project project = new Project();
        project.setName("A test project");
        assertEquals(0, project.getId());
        assertEquals(null, project.getCreatedAt());

        Project savedProject = modelDAO.save(project);

        assertNotNull(savedProject);
        assertEquals("A test project", savedProject.getName());
        assertEquals(1, savedProject.getId());
        Date now = new Date();
        assertEquals(true, dateInRange(savedProject.getCreatedAt(), now, 2));

    }

    @Test
    public void testSave_success() throws IllegalAccessException, ParseException, NoSuchFieldException, InstantiationException {
        Project project = new Project();
        project.setName("A test project");
        assertEquals(0, project.getId());
        assertEquals(null, project.getCreatedAt());

        Project savedProject = modelDAO.save(project);

        assertNotNull(savedProject);
        assertEquals("A test project", savedProject.getName());
        assertEquals(1, savedProject.getId());
        Date now = new Date();
        assertEquals(true, dateInRange(savedProject.getCreatedAt(), now, 2));

        Task task = new Task();
        task.setNumber(3);
        task.setTitle("MyTitle");
        task.setDescription("This is the description");
        task.setType("Defect");
        task.setStatus("Todo");
        task.setProjectId(savedProject.getId());

        assertEquals(0, task.getId());
        assertEquals(null, task.getCreatedAt());

        Task savedTask = modelDAO.save(task);

        assertNotNull(savedTask);
        assertEquals(3, savedTask.getNumber());
        assertEquals("MyTitle", savedTask.getTitle());
        assertEquals("This is the description", savedTask.getDescription());
        assertEquals("Defect", savedTask.getType());
        assertEquals("Todo", savedTask.getStatus());
        now = new Date();
        assertEquals(true, dateInRange(savedTask.getCreatedAt(), now, 2));

        savedTask.setTitle("MyChangedTitle");
        savedTask.setDescription(null);
        savedTask.setStatus("In-Progress");
        savedTask.setNumber(4);

        Task updatedTask = modelDAO.save(savedTask);

        assertNotNull(savedTask);
        assertEquals(4, savedTask.getNumber());
        assertEquals("MyChangedTitle", savedTask.getTitle());
        assertEquals(null, savedTask.getDescription());
        assertEquals("Defect", savedTask.getType());
        assertEquals("In-Progress", savedTask.getStatus());
        assertEquals(savedTask.getId(), updatedTask.getId());
        assertEquals(savedTask.getCreatedAt(), updatedTask.getCreatedAt());
        assertEquals(savedTask.getProjectId(), updatedTask.getProjectId());
    }

    @Test
    public void testModelDelete_success() throws IllegalAccessException, ParseException, NoSuchFieldException, InstantiationException {
        Project project = new Project();
        project.setName("A test project");
        Project savedProject = modelDAO.save(project);

        assertNotNull(savedProject);
        assertEquals("A test project", savedProject.getName());
        assertEquals(1, savedProject.getId());
        Date now = new Date();
        assertEquals(true, dateInRange(savedProject.getCreatedAt(), now, 2));

        modelDAO.delete(savedProject);
        Project foundProject = modelDAO.findById(savedProject.getClass(), savedProject.getId());
        assertEquals(null, foundProject);
    }


    boolean dateInRange(Date dateTime, Date referenceDateTime, int deltaSeconds) {
        Date lowerLimit = new Date(referenceDateTime.getTime() - deltaSeconds*1000);
        Date upperLimit = new Date(referenceDateTime.getTime() + deltaSeconds*1000);

        return dateTime.after(lowerLimit) && dateTime.before(upperLimit);
    }
}
