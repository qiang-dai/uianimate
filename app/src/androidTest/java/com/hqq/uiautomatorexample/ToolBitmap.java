package com.hqq.uiautomatorexample;

import android.graphics.Bitmap;
import android.os.Build;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

/**
 * Created by xinmei365 on 2018/2/14.
 */

public class ToolBitmap {

    private static Logger logger = Logger.getLogger("ToolAction");

    private static void storeBitmap(Bitmap bitmap, String path)
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

    public static Mat getScreenshot(String picPath) {
        Bitmap bitmap;
        bitmap = getInstrumentation().getUiAutomation().takeScreenshot();

        logger.info("folder.getPath:" + picPath);

        storeBitmap(bitmap, picPath);

        ToolShell.sleep(1000);

        Mat img = imread(picPath);// 读入图片，将其转换为Mat

        double scale = 0.5;
        Size dsize = new Size(img.width() * scale, img.height() * scale); // 设置新图片的大小
        Mat img2 = new Mat(dsize, CvType.CV_16S);// 创建一个新的Mat（opencv的矩阵数据类型）
        Imgproc.resize(img, img2,dsize);
        String newPicPath = picPath.replace("uianim.png", "uianim2.png");
        imwrite(newPicPath, img2);

        return img;
    }

    public static void detectedChessTest(String path) {
        String dir = ToolShell.getDir(path);
        String chessPath = dir + "chess.png";

        logger.info("detectedChessTest path:" + path);
        Mat source = imread(path);
//        Mat source, templete;
//        source = imread(path);
//        templete = imread(chessPath);
//
//        Mat result = Mat.zeros(source.rows() - templete.rows() + 1,
//                source.cols() - templete.cols() + 1, CvType.CV_32FC1);
//        Imgproc.matchTemplate(source, templete, result, Imgproc.TM_SQDIFF_NORMED);
//        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1);
//        Core.MinMaxLocResult mlr = Core.minMaxLoc(result);
//        org.opencv.core.Point matchLoc = mlr.minLoc;
//
//        System.out.println(matchLoc.x + ":" + matchLoc.y);
//        Imgproc.rectangle(source, matchLoc,
//                new org.opencv.core.Point(matchLoc.x + templete.width(), matchLoc.y + templete.height()),
//                new Scalar(0, 255, 0));
//
//        String resPath = dir + "result.png";
//        imwrite(resPath, source);
    }
}
