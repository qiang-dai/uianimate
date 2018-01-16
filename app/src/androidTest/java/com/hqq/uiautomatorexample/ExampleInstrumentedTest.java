package com.hqq.uiautomatorexample;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.hqq.uiautomatorexample", appContext.getPackageName());
    }


    private UiDevice mDevice;
    private Context mContext = null;
    String  APP = "com.hqq.uideviceapplication";

    public ExampleInstrumentedTest() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
        mDevice.pressHome();

    }

    @Test
    public void testMainActivity() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
        mDevice.pressHome();
        Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(APP);  //启动app
        mContext.startActivity(myIntent);
        mDevice.click(275, 1);

//        UiSelector uiSelector = new UiSelector().resourceId(APP + ":id/btn_Button");
//        UiObject object = new UiObject(uiSelector);
//        try {
//            object.click();
//        } catch (UiObjectNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
