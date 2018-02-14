package com.hqq.uiautomatorexample;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
        //遍历

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

    public static double[] getChessPoint(Mat source, Mat templete) {
        Mat result = Mat.zeros(source.rows() - templete.rows() + 1,
                source.cols() - templete.cols() + 1, CvType.CV_32FC1);
        Imgproc.matchTemplate(source, templete, result, Imgproc.TM_SQDIFF_NORMED);
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1);
        Core.MinMaxLocResult mlr = Core.minMaxLoc(result);
        org.opencv.core.Point matchLoc = mlr.minLoc;

        System.out.println(matchLoc.x + ":" + matchLoc.y);
        return new double[]{matchLoc.x + templete.width() / 2, matchLoc.y + templete.height(), templete.width(), templete.height()};
    }

    public static void detectedChessTest(String path) {
        String dir = ToolShell.getDir(path);
        String chessPath = dir + "chess.png";

        logger.info("detectedChessTest path dir:" + path);
        logger.info("detectedChessTest path chessPath:" + chessPath);
        Mat source, templete;
        source = imread(path);
        templete = imread(chessPath);

        logger.info("detectedChessTest:" + source.rows() + ", " + templete.rows());
        logger.info("detectedChessTest:" + source.cols() + ", " + templete.cols());
        logger.info("detectedChessTest:" + String.valueOf(source.rows() - templete.rows() + 1));
        logger.info("detectedChessTest:" + String.valueOf(source.cols() - templete.cols() + 1));
        Mat result = Mat.zeros(source.rows() - templete.rows() + 1,
                source.cols() - templete.cols() + 1, CvType.CV_32FC1);
        Imgproc.matchTemplate(source, templete, result, Imgproc.TM_SQDIFF_NORMED);
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1);
        Core.MinMaxLocResult mlr = Core.minMaxLoc(result);
        org.opencv.core.Point matchLoc = mlr.minLoc;

        //System.out.println(matchLoc.x + ":" + matchLoc.y);
        logger.info("detectedChessTest matchLoc:" + matchLoc.x + ":" + matchLoc.y);
        Imgproc.rectangle(source, matchLoc,
                new org.opencv.core.Point(matchLoc.x + templete.width(), matchLoc.y + templete.height()),
                new Scalar(0, 255, 0));

        String resPath = dir + "result2.png";
        imwrite(resPath, source);
    }

    public static void detectedWhiteDot(String path) {
        String dir = ToolShell.getDir(path);
        String chessPath = dir + "white_dot.jpg";

        logger.info("detectedWhiteDot path dir:" + path);
        logger.info("detectedWhiteDot path chessPath:" + chessPath);
        Mat source, templete;
        source = imread(path);
        templete = imread(chessPath);

        logger.info("detectedWhiteDot:" + source.rows() + ", " + templete.rows());
        logger.info("detectedWhiteDot:" + source.cols() + ", " + templete.cols());
        logger.info("detectedWhiteDot:" + String.valueOf(source.rows() - templete.rows() + 1));
        logger.info("detectedWhiteDot:" + String.valueOf(source.cols() - templete.cols() + 1));
        Mat result = Mat.zeros(source.rows() - templete.rows() + 1,
                source.cols() - templete.cols() + 1, CvType.CV_32FC1);
        Imgproc.matchTemplate(source, templete, result, Imgproc.TM_SQDIFF_NORMED);
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1);
        Core.MinMaxLocResult mlr = Core.minMaxLoc(result);
        org.opencv.core.Point matchLoc = mlr.minLoc;

        //System.out.println(matchLoc.x + ":" + matchLoc.y);
        logger.info("detectedWhiteDot matchLoc:" + matchLoc.x + ":" + matchLoc.y);
        Imgproc.rectangle(source, matchLoc,
                new org.opencv.core.Point(matchLoc.x + templete.width(), matchLoc.y + templete.height()),
                new Scalar(0, 255, 0));

        String resPath = dir + "result.png";
        imwrite(resPath, source);
    }

    public static Integer getMaxCnt(Map<Integer, Integer> cntMap) {
        Integer max = 0;
        for (Integer k : cntMap.keySet()) {
            Integer v = cntMap.get(k);
            if (max < v ) {
                max = v;
            }
        }
        return max;
    }

    public static void searchCenter(Bitmap mBitmap) {
        int mBitmapWidth = 0;
        int mBitmapHeight = 0;

        int mArrayColor[] = null;
        int mArrayColorLengh = 0;
        long startTime = 0;
        int mBackVolume = 0;

        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();

        mArrayColorLengh = mBitmapWidth * mBitmapHeight;
        mArrayColor = new int[mArrayColorLengh];
        int count = 0;
        for (int i = mBitmapHeight - 300; i >= 0; i--) {
            //统计不同颜色点的个数
            Map<Integer, Integer> pixelCntMap = new HashMap<>();
            for (int j = 0; j < mBitmapWidth; j++) {
                //获得Bitmap 图片中每一个点的color颜色值
                int color = mBitmap.getPixel(j, i);
                //将颜色值存在一个数组中 方便后面修改
                mArrayColor[count] = color;
                //如果你想做的更细致的话 可以把颜色值的R G B 拿到做响应的处理 笔者在这里就不做更多解释
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);

                Integer cnt = pixelCntMap.get(color);
                if (cnt == null) {
                    cnt = 1;
                } else {
                    cnt += 1;
                }
                pixelCntMap.put(color, cnt);
                if (pixelCntMap.size() > 1) {
                    //计算最大量颜色的比例
                    Integer max = getMaxCnt(pixelCntMap);
                    logger.info("["+ i + "]" + "getMaxCnt max:" + max + ", rate:" + max*100.0/mBitmapWidth + "%");

                    break;
                }

                count++;
            }
            break;
        }
        startTime = System.currentTimeMillis();
    }

//    public static int[] getBoardAndChessXYValue(String currentImage) {
//        try {
//            BufferedImage image = ImageIO.read(new File(currentImage));
//            int width = image.getWidth();
//            int height = image.getHeight();
//            System.out.println("宽度: " + width + ", 高度: " + height);
//            int boardX = 0;
//            int boardY = 0;
//            //获取棋子的XY坐标和棋子的宽度高度
//            double[] chessInfo = getChessPoint(Imgcodecs.imread(currentImage), Imgcodecs.imread("./chess.jpg"));
//            int chessX = (int)chessInfo[0];
//            int chessY = (int)chessInfo[1];
//            int chessWidth = (int)chessInfo[2];
//            int chessHeight = (int)chessInfo[3];
//
//            //该双重循环用来获得跳板的上顶点
//            for (int y = gameScoreBottomY; y < height; y ++) {
//                processRGBInfo(image, 0, y);
//                //获取背景色
//                int lastPixelR = this.rgbInfo.getRValue();
//                int lastPixelG = this.rgbInfo.getGValue();
//                int lastPixelB = this.rgbInfo.getBValue();
//                /**
//                 * 只要boardX>0就表示下个跳板的中心坐标x取到了
//                 */
//                if (boardX > 0) {
//                    break;
//                }
//                int boardXSum = 0;
//                int boardXCount = 0;
//                for (int x = 0; x < width; x ++) {
//                    processRGBInfo(image, x, y);
//                    int pixelR = this.rgbInfo.getRValue();
//                    int pixelG = this.rgbInfo.getGValue();
//                    int pixelB = this.rgbInfo.getBValue();
//
//                    //棋子头部比下一个跳板还要高
//                    if (Math.abs(x - chessX) < chessWidth / 2) {
//                        continue;
//                    }
//                    //判断背景色和当前RGB是否有很大差异
//                    if ((Math.abs(pixelR - lastPixelR) + Math.abs(pixelG - lastPixelG) + Math.abs(pixelB - lastPixelB)) > 50) {
//                        boardXSum += x;
//                        boardXCount ++;
//                    }
//                }
//                if (boardXSum > 0) {
//                    boardX = boardXSum / boardXCount;
//                }
//            }
//            boardY = chessY - Math.abs(boardX - chessX) *
//                    Math.abs(boardY1 - boardY2) / Math.abs(boardX1 - boardX2);
//            if (boardX > 0 && boardY > 0) {
//                int[] result = new int[]{chessX, chessY, boardX, boardY};
//                return result;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
