package com.hqq.uiautomatorexample;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.screenshot.Screenshot;
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
import android.support.v4.app.ActivityCompat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
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
        mDevice = UiDevice.getInstance(getInstrumentation());
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

    private UiObject scrollVerticalByInstanceToEnd() {
        UiObject uiObject = null;
        try {
            new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollToEnd(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uiObject;
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

    private void storeBitmap(Bitmap bitmap, String path)
    {
        BufferedOutputStream out = null;
        try
        {
            out = new BufferedOutputStream(new FileOutputStream(path));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

//            Thread.sleep(10*1000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Activity getCurrentActivity()
    {
        getInstrumentation().runOnMainSync(new Runnable()
        {
            public void run()
            {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(
                        RESUMED);
                if (resumedActivities.iterator().hasNext())
                {
                    Activity currentActivity = (Activity) resumedActivities.iterator().next();
                }
            }
        });

        return null;
    }

    private File getStoragePath() {
        String removableStoragePath;
        File fileList[] = new File("/storage/").listFiles();
        //logger.info("fileList:" + fileList.length);
//        if (fileList == null) {
//            return new File("/storage/sdcard0/test/");
//        }
        for (File file : fileList) {
            logger.info("file:" + file);

            if(!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canWrite()) {
                return file;
            }
        }
        logger.info("return final external directory");

        return Environment.getExternalStorageDirectory();
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
// Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
// We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    protected static boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static Activity getActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);

            Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);
            if (activities == null)
                return null;

            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //@TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
  /*  int requestCode = 200;
    requestPermissions(permissions, requestCode);*/
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(getTargetContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            //ActivityManager am = (ActivityManager)getTargetContext().getSystemService(Context.ACTIVITY_SERVICE);
            //ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

            ActivityCompat.requestPermissions(
                    getActivity(), permissions, 1);
        }
    }

    @Test
    public void testMainActivity() {
        mDevice = UiDevice.getInstance(getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
//        scrollHorizonByClass("android.widget.RelativeLayout", 1);
//        try {
//            mDevice.wakeUp();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        if (shouldAskPermissions()) {
//            askPermissions();
//        }

        mDevice.pressHome();
        mDevice.openNotification();
        clickByDescription("Apps");
        scrollVerticalByInstanceToEnd();

        Bitmap bitmap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            bitmap = getInstrumentation().getUiAutomation().takeScreenshot();

            File folder = new File(getTargetContext().getExternalCacheDir().getAbsolutePath() + "/screenshots/");
            if (!folder.exists())
            {
                folder.mkdirs();
            }
            logger.info("folder.getPath:" + folder.getPath());
            storeBitmap(bitmap, folder.getPath() + "/uianim.png");

        }
//        String fileName = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/unanim3.png";
//        fileName = getTargetContext().getFileStreamPath("uianim4.png").toString();
//        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() +  "/storage/data/test";
//        File folder = new File(dir);
//        if (!folder.exists())
//        {
//            folder.mkdirs();
//        }
        File folder = getStoragePath();
        logger.info("fileName:" + folder);
        mDevice.takeScreenshot(new File("/storage/emulated/0/Android/data/com.hqq.uiautomatorexample/cache/screenshots/z.png"));
        //mDevice.takeScreenshot(new File(folder + "/zz.png"));
        //Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(APP);  //启动app
        //mContext.startActivity(myIntent);
//        clickByText("Settings");
//        getScrollObject();
//        clickByText("Display");
    }
    @Before
    public void testBeafo() {

    }

}
