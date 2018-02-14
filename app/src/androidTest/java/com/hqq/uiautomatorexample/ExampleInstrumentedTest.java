package com.hqq.uiautomatorexample;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.CHAIN_APPROX_SIMPLE;
import static org.opencv.imgproc.Imgproc.RETR_EXTERNAL;

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
        //Device.pressHome();

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

//    private static Activity getCurrentActivity()
//    {
//        getInstrumentation().runOnMainSync(new Runnable()
//        {
//            public void run()
//            {
//                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(
//                        RESUMED);
//                if (resumedActivities.iterator().hasNext())
//                {
//                    Activity currentActivity = (Activity) resumedActivities.iterator().next();
//                }
//            }
//        });
//
//        return null;
//    }

//    private File getStoragePath() {
//        String removableStoragePath;
//        File fileList[] = new File("/storage/").listFiles();
//        //logger.info("fileList:" + fileList.length);
////        if (fileList == null) {
////            return new File("/storage/sdcard0/test/");
////        }
//        for (File file : fileList) {
//            logger.info("file:" + file);
//
//            if(!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canWrite()) {
//                return file;
//            }
//        }
//        logger.info("return final external directory");
//
//        return Environment.getExternalStorageDirectory();
//    }
//
//    private static final int REQUEST_EXTERNAL_STORAGE = 1;
//    private static String[] PERMISSIONS_STORAGE = {
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//    };
//    public static void verifyStoragePermissions(Activity activity) {
//// Check if we have write permission
//        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//// We don't have permission so prompt the user
//            ActivityCompat.requestPermissions(
//                    activity,
//                    PERMISSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE
//            );
//        }
//    }


//    protected static boolean shouldAskPermissions() {
//        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
//    }

//    public static Activity getActivity() {
//        try {
//            Class activityThreadClass = Class.forName("android.app.ActivityThread");
//            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
//            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
//            activitiesField.setAccessible(true);
//
//            Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);
//            if (activities == null)
//                return null;
//
//            for (Object activityRecord : activities.values()) {
//                Class activityRecordClass = activityRecord.getClass();
//                Field pausedField = activityRecordClass.getDeclaredField("paused");
//                pausedField.setAccessible(true);
//                if (!pausedField.getBoolean(activityRecord)) {
//                    Field activityField = activityRecordClass.getDeclaredField("activity");
//                    activityField.setAccessible(true);
//                    Activity activity = (Activity) activityField.get(activityRecord);
//                    return activity;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

//    //@TargetApi(23)
//    protected void askPermissions() {
//        String[] permissions = {
//                "android.permission.READ_EXTERNAL_STORAGE",
//                "android.permission.WRITE_EXTERNAL_STORAGE"
//        };
//  /*  int requestCode = 200;
//    requestPermissions(permissions, requestCode);*/
//        // Check if we have write permission
//        int permission = ActivityCompat.checkSelfPermission(getTargetContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            // We don't have permission so prompt the user
//            //ActivityManager am = (ActivityManager)getTargetContext().getSystemService(Context.ACTIVITY_SERVICE);
//            //ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
//
//            ActivityCompat.requestPermissions(
//                    getActivity(), permissions, 1);
//        }
//    }

    // 根据三个点计算中间那个点的夹角 pt1 pt0 pt2
    private static double getAngle(Point pt1, Point pt2, Point pt0)
    {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return (dx1*dx2 + dy1*dy2)/Math.sqrt((dx1*dx1 + dy1*dy1)*(dx2*dx2 + dy2*dy2) + 1e-10);
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

        //mDevice.pressHome();
        //mDevice.openNotification();
//        clickByDescription("Apps");
//        scrollVerticalByInstanceToEnd();

        Bitmap bitmap;
        //if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            bitmap = getInstrumentation().getUiAutomation().takeScreenshot();

            File folder = new File(getTargetContext().getExternalCacheDir().getAbsolutePath()
                    + "/screenshots/");
            if (!folder.exists())
            {
//                folder.mkdirs();
            }
            String dir = ToolShell.getPath("screenshots/");
            logger.info("folder.getPath:" + dir);
            storeBitmap(bitmap, dir + "/uianimx.png");

            Mat img = imread(dir + "/uianimx.png");// 读入图片，将其转换为Mat
            double scale = 0.5;
            Size dsize = new Size(img.width() * scale, img.height() * scale); // 设置新图片的大小
//            Mat img2 = new Mat(dsize, CvType.CV_16S);// 创建一个新的Mat（opencv的矩阵数据类型）
//            Imgproc.resize(img, img2,dsize);
//            imwrite(dir + "/uianim2.png", img2);

            //彩色转灰度
            Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
            imwrite(dir + "/uianim3.png", img);
            // 高斯滤波，降噪
            Imgproc.GaussianBlur(img, img, new Size(3,3), 2, 2);
            imwrite(dir + "/uianim4.png", img);
            // Canny边缘检测
            Imgproc.Canny(img, img, 20, 60, 3, false);
            imwrite(dir + "/uianim5.png", img);
            // 膨胀，连接边缘
            Imgproc.dilate(img, img, new Mat(), new Point(-1,-1), 3, 1, new Scalar(1));
            imwrite(dir + "/uianim6.png", img);
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(img, contours, hierarchy, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);

            // 找出轮廓对应凸包的四边形拟合
            List<MatOfPoint> squares = new ArrayList<>();
            List<MatOfPoint> hulls = new ArrayList<>();
            MatOfInt hull = new MatOfInt();
            MatOfPoint2f approx = new MatOfPoint2f();
            approx.convertTo(approx, CvType.CV_32F);

            for (MatOfPoint contour: contours) {
                logger.info("contour:" + contour.toString());

                // 边框的凸包
                Imgproc.convexHull(contour, hull);

                // 用凸包计算出新的轮廓点
                Point[] contourPoints = contour.toArray();
                int[] indices = hull.toArray();
                List<Point> newPoints = new ArrayList<>();
                for (int index : indices) {
                    newPoints.add(contourPoints[index]);
                }
                MatOfPoint2f contourHull = new MatOfPoint2f();
                contourHull.fromList(newPoints);

                // 多边形拟合凸包边框(此时的拟合的精度较低)
                Imgproc.approxPolyDP(contourHull, approx, Imgproc.arcLength(contourHull, true)*0.02, true);

                // 筛选出面积大于某一阈值的，且四边形的各个角度都接近直角的凸四边形
                MatOfPoint approxf1 = new MatOfPoint();
                approx.convertTo(approxf1, CvType.CV_32S);
                if (approx.rows() == 4 && Math.abs(Imgproc.contourArea(approx)) > 400 &&
                        Imgproc.isContourConvex(approxf1)) {
                    double maxCosine = 0;
                    for (int j = 2; j < 5; j++) {
                        double cosine = Math.abs(getAngle(approxf1.toArray()[j%4], approxf1.toArray()[j-2], approxf1.toArray()[j-1]));
                        maxCosine = Math.max(maxCosine, cosine);
                    }
                    // 角度大概72度
                    if (maxCosine < 0.3) {
                        MatOfPoint tmp = new MatOfPoint();
                        contourHull.convertTo(tmp, CvType.CV_32S);
                        squares.add(approxf1);
                        hulls.add(tmp);
                    }
                    logger.info("approxf1:" + approxf1.toString() + ", size:" + Imgproc.contourArea(approx));
                    for (Integer i = 0;i < approxf1.toArray().length; i++) {
                        logger.info("point:" + approxf1.toArray()[i]);
                    }
                }
            }
            imwrite(dir + "/uianim7.png", img);

            //ImageView imgview = (ImageView) findViewById(R.layout.activity_main3);
            // 显示照片
            //imgview.setImageBitmap(bitmap);

        }
//        String fileName = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/unanim3.png";
//        fileName = getTargetContext().getFileStreamPath("uianim4.png").toString();
//        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() +  "/storage/data/test";
//        File folder = new File(dir);
//        if (!folder.exists())
//        {
//            folder.mkdirs();
//        }
//        File folder = getStoragePath();
//        logger.info("fileName:" + folder);
//        mDevice.takeScreenshot(new File("/storage/emulated/0/Android/data/com.hqq.uiautomatorexample/cache/screenshots/z.png"));

//        TextView tv = new TextView(mContext);
//        tv.findViewById(R.layout.activity_main3);
        //mDevice.takeScreenshot(new File(folder + "/zz.png"));
        //Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(APP);  //启动app
        //mContext.startActivity(myIntent);
//        clickByText("Settings");
//        getScrollObject();
//        clickByText("Display");
    }
    @Before
    public void testBeafo() {
        boolean ret = OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_3_0, getTargetContext(), mLoaderCallback);
        logger.info("ret:" + ret);

        // Launch the app
        String APP_PACKAGE = "com.hqq.uiautomatorexample";

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = new Intent()
                .setClassName(APP_PACKAGE, APP_PACKAGE + ".MainActivity")
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(APP_PACKAGE).depth(0)), 1000);
        mContext = InstrumentationRegistry.getTargetContext();
    }
    private static final String TAG = "OCVSample::Activity";
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(getTargetContext()) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

}
