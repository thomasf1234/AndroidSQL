package com.abstractx1.androidsql.tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abstractx1.androidsql.BaseInstrumentedTest;
import com.abstractx1.androidsql.ColumnInfo;
import com.abstractx1.androidsql.SQLite;
import com.abstractx1.androidsql.TableInfo;
import com.abstractx1.androidsql.db.SQLiteDAO;
import com.abstractx1.androidsql.db.Schema;
import com.abstractx1.androidsql.schemas.TestSchemaV2;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TableInfoTest extends BaseInstrumentedTest {
    @Test
    public void getColumnInfo() throws Exception {
        TableInfo tableInfo = new TableInfo(getSqLiteDAO());

        assertEquals(null, tableInfo.getColumnInfo("unknown_table"));

        ColumnInfo projectsColumnInfo = tableInfo.getColumnInfo("projects");
        assertNotNull(projectsColumnInfo);

        List<String> actualColumns = new ArrayList<>(projectsColumnInfo.getColumnNames());
        List<String> expectedColumns = Arrays.asList("id", "name", "created_at");
        assertEquals(true, listEqualsNoOrder(actualColumns, expectedColumns));

        assertEquals(SQLite.TYPENAME_INTEGER, projectsColumnInfo.getTypeName("id"));
        assertEquals(SQLite.TYPENAME_TEXT, projectsColumnInfo.getTypeName("name"));
        assertEquals(SQLite.TYPENAME_DATETIME, projectsColumnInfo.getTypeName("created_at"));
    }
}
