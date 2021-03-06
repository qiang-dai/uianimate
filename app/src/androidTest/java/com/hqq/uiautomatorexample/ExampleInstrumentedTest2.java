package com.hqq.uiautomatorexample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
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
public class ExampleInstrumentedTest2 {
    Logger logger = Logger.getLogger("test");


    private UiDevice mDevice;
    private Context mContext = null;
    String  APP = "com.hqq.uiautomatorexample";

    public ExampleInstrumentedTest2() {
        mDevice = UiDevice.getInstance(getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
    }

    @Test
    public void testMainActivity() {
        ToolShell.sleep(1500);
        for (Integer i = 0; i < 100;i++) {
            String sessinId = ToolShell.getSessinId();

            testMainActivity2(sessinId);
            ToolShell.sleep(1500);
        }
    }
    public void testMainActivity2(String sessinId) {
        //截图
        String shortName = sessinId + "screen.png";
        String path = ToolShell.getStoragePath("") + shortName;
        //截屏
        ToolBitmap.getScreenshot(path);
        //检测起点
        Point start = ToolBitmap.detectedChessTest(path, sessinId);
        //检测opencv边缘中心
        Point top = ToolBitmap.searchTop(path, start, sessinId);
        //检测白点
        //Point whiteDot = ToolBitmap.detectedWhiteDot(path);
        Point end = new Point(100, 200);
        Point right = ToolBitmap.expandPixel(path, start, top, sessinId);
//        //根据白点修复位置
//        if (Math.abs(whiteDot.x - top.x) < 10
//                && Math.abs(whiteDot.y - right.y) < 50) {
//            right.x = whiteDot.x;
//            right.y = whiteDot.y;
//        }
        //最终位置
        Point dest = new Point(top.x, right.y);
        //微调：如果top 和 dest 差不多位置，就调整为 diff*3/5的位置
        Double width = Math.abs(top.x - start.x);
        if (width < 0) {
            width = -width;
        }
        Double y = top.y + (width)*4/7;

        if (Math.abs(dest.y - top.y) < 10
                || Math.abs(dest.y - top.y) > 300
                || Math.abs(dest.y - y) > 250) {
            logger.info("fix use y");
            logger.info("fix Math.abs(dest.y - top.y):" + Math.abs(dest.y - top.y));
            logger.info("fix Math.abs(dest.y - top.y):" + Math.abs(dest.y - top.y));
            logger.info("fix Math.abs(dest.y - y):" + Math.abs(dest.y - y));
            dest.y = y;
        }

        logger.info("opencvCenter final start:" + start);
        logger.info("opencvCenter final top:" + top);
        logger.info("opencvCenter final right:" + right);
        logger.info("opencvCenter final dest:" + dest);


        Mat source = imread(path);
        Imgproc.rectangle(source, dest,
                new org.opencv.core.Point(dest.x + 50, dest.y + 50),
                new Scalar(0, 0, 255));
        Imgproc.rectangle(source, start,
                new org.opencv.core.Point(start.x + 50, start.y + 50),
                new Scalar(0, 255, 255));

        String dir = ToolShell.getFileDirectory(path);
        String resPath = dir + sessinId + "result7StartDest.png";
        logger.info("opencvCenter resPath:" + resPath);
        imwrite(resPath, source);

        Double diff_x = dest.x - start.x;
        Double diff_y = dest.y - start.y;
        //计算距离
        Double diff = Math.sqrt(diff_x*diff_x + diff_y*diff_y);
        Double diff2 = diff/12;
        Integer duration = diff2.intValue();
        if (duration < 20) {
            duration = 20;
        }

        ToolAction.clickByClass("android.widget.ImageView", duration);
        logger.info("duration check, start:" + start);
        logger.info("duration check, diff:" + diff);
        logger.info("duration check, duration:" + duration);
    }
    @Before
    public void testBeafo() {
        boolean ret = OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_3_0, getTargetContext(), mLoaderCallback);
        logger.info("ret:" + ret);

        String APP_PACKAGE = "com.hqq.uiautomatorexample";
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
