package com.hqq.uiautomatorexample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
        //截图
        //File folder = new File(getTargetContext().getExternalCacheDir().getAbsolutePath() + "/screenshots/");

        String shortName = "uianim.png";
        //ToolBitmap.getScreenshot(ToolShell.getPath("/screenshots/") + shortName);
        ToolBitmap.detectedChessTest(ToolShell.getPath("/screenshots/") + shortName);
        //定位当前位置

        //定位物体中心
        //点击
        ToolAction.clickByClass("android.widget.ImageView", 70);
    }

}