package com.abstractx1.androidsql.tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.abstractx1.androidsql.BaseInstrumentedTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AccessibilityTest extends BaseInstrumentedTest {
    class TestClass {
        private int myPrivateField;
    }

    @Test
    public void testSetAccessibilityNotDangerous() throws NoSuchFieldException, IllegalAccessException {
        TestClass testClass = new TestClass();

        //First set the private field but retain accessibility for fieldInstance1
        Field fieldInstance1 = TestClass.class.getDeclaredField("myPrivateField");
        fieldInstance1.setAccessible(true);
        fieldInstance1.setInt(testClass, 1);

        //The following should error
        Field fieldInstance2 = TestClass.class.getDeclaredField("myPrivateField");
        try {
            fieldInstance2.setInt(testClass, 2);
            fail("myPrivate field should still be private!");
        } catch (IllegalAccessException e) {
            //The test passed
        } finally {
            fieldInstance1.setAccessible(false);
        }
    }
}
