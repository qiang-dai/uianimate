package com.hqq.uiautomatorexample;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.logging.Logger;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
//    @Test
//    public void useAppContext() throws Exception {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.hqq.uiautomatorexample", appContext.getPackageName());
//    }
    Logger logger = Logger.getLogger("test");


    private UiDevice mDevice;
    private Context mContext = null;
    String  APP = "com.hqq.uiautomatorexample";

    public ExampleInstrumentedTest() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
        mDevice.pressHome();

    }

    private UiObject clickById(String objId) {
        UiSelector uiSelector = new UiSelector().resourceId(objId);
        UiObject object = new UiObject(uiSelector);
        logger.info("objId:" + objId + ",object:" + object);
        try {
            object.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    private UiObject clickByDescription(String text) {
        UiSelector uiSelector = new UiSelector().description(text);
        UiObject object = new UiObject(uiSelector);
        logger.info("text:" + text + ",object:" + object);

        try {
            object.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    private UiObject clickByText(String text) {
        UiSelector uiSelector = new UiSelector().text(text);
        UiObject object = new UiObject(uiSelector);
        logger.info("text:" + text + ",object:" + object);

        try {
            object.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    private UiObject scrollHorizonByClass(String clz, Integer val) {
        UiSelector uiSelector = new UiSelector().className(clz);
        UiObject object = new UiObject(uiSelector);

        try {
            if (val < 0) {
                object.swipeRight(200);
            } else {
                object.swipeLeft(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    private UiObject getScrollObject() {
        UiScrollable noteList = new UiScrollable( new UiSelector().scrollable(true));
        UiObject note = null;
        try {
            if (noteList.exists()) {
                note = noteList.getChildByText(new UiSelector().className("android.widget.TextView"), "System", true);
            } else {
                note = new UiObject(new UiSelector().text("System"));
            }
            assertThat(note, notNullValue());
            note.longClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return note;
    }
    @Test
    public void testMainActivity() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
        scrollHorizonByClass("android.widget.RelativeLayout", 1);
//        try {
//            mDevice.wakeUp();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //mDevice.pressHome();
        //Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(APP);  //启动app
        //mContext.startActivity(myIntent);

        //clickById(APP + ":id/btn_Button");
        //mDevice.pressHome();
//        clickById("com.google.android.apps.nexuslauncher:id/page_indicator");
//        clickByText("Settings");
//        getScrollObject();
//        clickByText("Display");
//        clickByText("掌上财富");

//
//        for(int i = 0; i < 10; i++) {
//            UiObject2 app = mDevice.wait(Until.findObject(By.text("应用管理")), 5000);
//            if (app != null) {
//                app.click();
//                break;
//            }
//            List<UiObject2> scrollList = mDevice.findObjects(By.clazz("android.widget.ScrollView"));
//            UiObject2 scroll = scrollList.get(0);
//            scroll.scroll(Direction.DOWN, 0.8f, 3000);
//        }

//        Utils.sleep(3000, "for listView");
//        List<UiObject2> listList = mDevice.findObjects(By.clazz("android.widget.ListView"));
//        if (listList.size() > 0) {
//            UiObject2 list = listList.get(0);
//            list.scroll(Direction.DOWN, 13f, 10000);
//
//            clearCache("iKeyboard");
//            clearCache("Kika Emoji Keyboard Pro");
//            clearCache("Kika Keyboard");
//        } else {
//            log.error("no listView");
//        }

    }
    @Before
    public void testBeafo() {

    }

}
