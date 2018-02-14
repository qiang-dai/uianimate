package com.hqq.uiautomatorexample;

import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.opencv.core.Point;

import java.util.logging.Logger;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by xinmei365 on 2018/2/14.
 */

class ToolAction {
    private static Logger logger = Logger.getLogger("ToolAction");
    
    public static UiObject clickById(String objId) {
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

    public static UiObject clickByClass(String clz, Integer val) {
        UiSelector uiSelector = new UiSelector().className(clz);
        UiObject object = new UiObject(uiSelector);

        try {
            Point pot = new Point(object.getVisibleBounds().centerX(), object.getVisibleBounds().centerY());
            UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
            Integer x = new Double(pot.x).intValue();
            Integer y = new Double(pot.y).intValue();
            mDevice.swipe(x, y, x, y, val);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static UiObject clickByDescription(String text) {
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

    public static UiObject clickByText(String text) {
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

    public static UiObject scrollVerticalByInstanceToEnd() {
        UiObject uiObject = null;
        try {
            new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollToEnd(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uiObject;
    }
    public static UiObject scrollHorizonByClass(String clz, Integer val) {
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

    public static UiObject getScrollObject() {
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
}
