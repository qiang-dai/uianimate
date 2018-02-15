package com.hqq.uiautomatorexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.CHAIN_APPROX_SIMPLE;
import static org.opencv.imgproc.Imgproc.RETR_EXTERNAL;

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

        logger.info("folder.getStoragePath:" + picPath);

        storeBitmap(bitmap, picPath);

//        ToolShell.sleep(1000);

        Mat img = imread(picPath);// 读入图片，将其转换为Mat

//        double scale = 0.5;
//        Size dsize = new Size(img.width() * scale, img.height() * scale); // 设置新图片的大小
//        Mat img2 = new Mat(dsize, CvType.CV_16S);// 创建一个新的Mat（opencv的矩阵数据类型）
//        Imgproc.resize(img, img2,dsize);
//        String newPicPath = picPath.replace("uianim.png", "uianim2.png");
//        imwrite(newPicPath, img2);

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

    public static Point detectedChessTest(String path) {
        String dir = ToolShell.getFileDirectory(path);
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
                new Scalar(255, 0, 0));


        matchLoc.x += templete.width()/2;
        matchLoc.y += templete.height();

        Imgproc.rectangle(source, matchLoc,
                new org.opencv.core.Point(matchLoc.x + 30, matchLoc.y + 30),
                new Scalar(255, 0, 255));

        String resPath = dir + "result.png";
        imwrite(resPath, source);

        return matchLoc;
    }

    public static Point detectedWhiteDot(String path) {
        String dir = ToolShell.getFileDirectory(path);
        String chessPath = dir + "white_dot.png";

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
        //fix
        matchLoc.x += templete.width()/2;
        matchLoc.y += templete.height()/2;

        Imgproc.rectangle(source, matchLoc,
                new org.opencv.core.Point(matchLoc.x + templete.width(), matchLoc.y + templete.height()),
                new Scalar(255, 0, 0));

        String resPath = dir + "result2.png";
        imwrite(resPath, source);
        return matchLoc;
    }

    public static Integer getMinCnt(Map<Integer, Integer> cntMap) {
        Integer min = 10000;
        for (Integer k : cntMap.keySet()) {
            Integer v = cntMap.get(k);
            if (min > v) {
                min = v;
            }
        }
        return min;
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

    public static Integer getLeast(Map<Integer, Integer> cntMap, Integer max) {
        for (Integer k : cntMap.keySet()) {
            Integer v = cntMap.get(k);
            if (v != max) {
                return k;
            }
        }
        return -1;
    }

    public static Boolean sortPoint(Point p1, Point p2) {
        if (p1.x < p2.x) {
            return true;
        }
        if (p1.y < p2.y) {
            return true;
        }
        return false;
    }
    // 根据三个点计算中间那个点的夹角   pt1 pt0 pt2
    private static double getAngle(Point pt1, Point pt2, Point pt0)
    {
        double dx1 = pt1.x - pt0.x;
        double dy1 = pt1.y - pt0.y;
        double dx2 = pt2.x - pt0.x;
        double dy2 = pt2.y - pt0.y;
        return (dx1*dx2 + dy1*dy2)/Math.sqrt((dx1*dx1 + dy1*dy1)*(dx2*dx2 + dy2*dy2) + 1e-10);
    }
    public static Point opencvCenter(String path, Point start, Point top) {
        Point end = new Point(100, 300);

        String dir = ToolShell.getFileDirectory(path);

        Mat img = imread(path);// 读入图片，将其转换为Mat
        double scale = 0.5;
        Size dsize = new Size(img.width() * scale, img.height() * scale); // 设置新图片的大小

        //彩色转灰度
        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
        imwrite(dir + "/uianim3.png", img);
        // 高斯滤波，降噪
        Imgproc.GaussianBlur(img, img, new Size(3,3), 2, 2);
        imwrite(dir + "/uianim4.png", img);
        // Canny边缘检测
        Imgproc.Canny(img, img, 3, 8, 3, false);
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



        //chess
        String chessPath = dir + "chess.png";
        Mat chess = imread(chessPath);
        Double xxx = start.x + chess.width()/2;

        List<Point> totalPoints = new ArrayList<>();
        for (MatOfPoint contour: contours) {
            logger.info("contour:" + contour.toString());
            // 边框的凸包
            Imgproc.convexHull(contour, hull);

            // 用凸包计算出新的轮廓点
            Point[] contourPoints = contour.toArray();
            int[] indices = hull.toArray();
            List<Point> newPoints = new ArrayList<>();
            for (int index : indices) {
                Point point = contourPoints[index];
                if (point.y > 300
                        && point.y < start.y + 300) {

                    //必须在chess的旁边
                    if (start.x <= 1080/2 && point.x <= start.x + chess.width()/2) {
                        continue;
                    }
                    if (start.x >= 1080/2 && point.x >= start.x - chess.width()/2) {
                        continue;
                    }
                    totalPoints.add(point);
                }
            }
        }

        //所有的点统一排序
        logger.info("totalPoints size:" + totalPoints.size());
        Collections.sort(totalPoints, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return Double.compare(p1.y, p2.y);
            }
        });
        Collections.sort(totalPoints, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return Double.compare(p1.x, p2.x);
            }
        });
        //遇到x相同的，去掉y大的点
//        Map<Double, Point> tmpMap = new HashMap<>();
//
//        for (Integer i = 0; i < totalPoints.size() - 1; i++) {
//            Point point = totalPoints.get(i);
//            //判断是不是差不多的像素点，如果是就聚类
//
//            Double k = point.x;
//            if (tmpMap.containsKey(k)) {
//                Point tmp = tmpMap.get(k);
//                if (point.y < tmp.y) {
//                    tmpMap.put(k, point);
//                }
//            } else {
//                tmpMap.put(k, point);
//            }
//        }
//        //重新组织list
//        totalPoints.clear();
//        for (Double k : tmpMap.keySet()) {
//            Point point = tmpMap.get(k);
//            totalPoints.add(point);
//
//        }
        Mat source2 = imread(path);
        for (Point point : totalPoints) {
            //ok
            Imgproc.rectangle(source2, point,
                    new org.opencv.core.Point(point.x + 50, point.y + 50),
                    new Scalar(0, 0, 255));
            String resPath = dir + "result5.png";
            logger.info("opencvCenter resPath:" + resPath);
            imwrite(resPath, source2);
        }
        //从前往后匹配
        Integer rightPos = totalPoints.size() - 1;
        Integer leftPos = 0;

        Mat source = imread(path);

        Map<Point, Point> optionCenterMap = new HashMap<>();
        for (Integer i = 0; i < 10000; i++) {
            if (leftPos >= rightPos) {
                logger.info("error for match left/right");
                break;
            }
            Point left = totalPoints.get(leftPos);
            Point right= totalPoints.get(rightPos);
            Double x = (left.x + right.x)/2;
            Double y = (left.y + right.y)/2;
            Point center = new Point(x, y);
            //判断cos值
            Double cosine = getAngle(top, start, center);
//            if ((Math.abs(center.x - top.x) < 50)
//                    && (Math.abs(left.y - right.y) < 50)
//                    && center.y < start.y
//                    && center.y > top.y
//                    && cosine < 0) {
            if (Math.abs(center.x - top.x) < 30
                    && Math.abs(left.y - right.y) < 30
                    && center.y < start.y
                    && center.y > top.y) {

                //ok
                Imgproc.rectangle(source, center,
                        new org.opencv.core.Point(center.x + 50, center.y + 50),
                        new Scalar(255, 255, 255));
                Imgproc.rectangle(source, left,
                        new org.opencv.core.Point(left.x + 50, left.y + 50),
                        new Scalar(255, 0, 255));
                Imgproc.rectangle(source, right,
                        new org.opencv.core.Point(right.x + 50, right.y + 50),
                        new Scalar(255, 255, 0));
                //return center;
                optionCenterMap.put(center, center);

            } else if (center.x > top.x) {
                rightPos --;
            } else {
                leftPos ++;
            }

        }
        //选最上层的option
        String resPath = dir + "result3.png";
        logger.info("optionCenterMap size:" + optionCenterMap.size());
        logger.info("opencvCenter resPath:" + resPath);
        imwrite(resPath, source);

        return top;


        //for (MatOfPoint contour: contours) {
//        for (Integer i = 0; i < contours.size(); i++) {
//            MatOfPoint contour = contours.get(i);
//
//            logger.info("contour:" + contour.toString());
//
//            // 边框的凸包
//            Imgproc.convexHull(contour, hull);
//
//            // 用凸包计算出新的轮廓点
//            Point[] contourPoints = contour.toArray();
//            int[] indices = hull.toArray();
//            List<Point> newPoints = new ArrayList<>();
//            for (int index : indices) {
//                newPoints.add(contourPoints[index]);
//            }
//            MatOfPoint2f contourHull = new MatOfPoint2f();
//            contourHull.fromList(newPoints);
//
//            // 多边形拟合凸包边框(此时的拟合的精度较低)
//            Imgproc.approxPolyDP(contourHull, approx, Imgproc.arcLength(contourHull, true)*0.02, true);
//
//            // 筛选出面积大于某一阈值的，且四边形的各个角度都接近直角的凸四边形
//            MatOfPoint approxf1 = new MatOfPoint();
//            approx.convertTo(approxf1, CvType.CV_32S);
//
//            if (Math.abs(Imgproc.contourArea(approx)) > 40000 &&
//                    Imgproc.isContourConvex(approxf1)) {
//                //求最高点
//                Point[] arr = approxf1.toArray();
//                Point top = new Point(1920,1080);
//                for(Point point : arr) {
//                    if (point.y < top.y) {
//                        top = point;
//                    }
//                }
//                logger.info("opencvCenter top:" + top);
//                Mat source;
//                source = imread(path);
//                Imgproc.rectangle(source, top,
//                        new org.opencv.core.Point(top.x + 50, top.y + 50),
//                        new Scalar(255, 0, 0));
////
//                String resPath = dir + String.format("result3_%d.png", i);
//                logger.info("opencvCenter resPath:" + resPath);
//                imwrite(resPath, source);
//
//                //取大于300的小的
//                if (top.y > 300 && end.y > top.y) {
//                    end = top;
//                }
//            }
//        }
//        imwrite(dir + "/uianim7.png", img);
    }
    public static Point searchTop(String path, Point start) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap mBitmap = BitmapFactory.decodeFile(path, options);

        int mBitmapWidth = 0;
        int mBitmapHeight = 0;

        int mArrayColor[] = null;
        int mArrayColorLengh = 0;
        long startTime = 0;
        int mBackVolume = 0;

        Point point = new Point(1920, 1080);

        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();

        mArrayColorLengh = mBitmapWidth * mBitmapHeight;
        mArrayColor = new int[mArrayColorLengh];
        int count = 0;
        for (int i = 300; i < mBitmapHeight; i++) {
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

                    Integer min = j-max;//getMinCnt(pixelCntMap);
                    if (min < 20) {
                        continue;
                    }
                    Point top = new Point(j-10, i-5);
                    point = top;
                    String dir = ToolShell.getFileDirectory(path);

                    Mat source;
                    source = imread(path);
                    Imgproc.rectangle(source, top,
                            new org.opencv.core.Point(top.x + 50, top.y + 50),
                            new Scalar(255, 0, 0));

//                    top.x = 50;
//                    top.y = 80;
//                    Imgproc.rectangle(source, top,
//                            new org.opencv.core.Point(top.x + 50, top.y + 50),
//                            new Scalar(0, 255, 0));
//
//                    top.x = 150;
//                    top.y = 180;
//                    Imgproc.rectangle(source, top,
//                            new org.opencv.core.Point(top.x + 50, top.y + 50),
//                            new Scalar(0, 0, 255));
//
                    String resPath = dir + String.format("result4.png", i);

                    //定点 point
                    //计算中心
                    Double diff_x = point.x - start.x;
                    if (diff_x < 0) {
                        diff_x = -diff_x;
                    }
                    Double diff_y = diff_x*3/5;
                    point.y = start.y - diff_y;
                    Imgproc.rectangle(source, point,
                            new org.opencv.core.Point(point.x + 50, point.y + 50),
                            new Scalar(0, 0, 0));

                    logger.info("opencvCenter resPath:" + resPath);
                    imwrite(resPath, source);

                    return point;
                }

                count++;
            }
        }
        startTime = System.currentTimeMillis();
        return point;
    }

//    public static Point searchMiddle(String path, Point start) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap mBitmap = BitmapFactory.decodeFile(path, options);
//
//        int mBitmapWidth = 0;
//        int mBitmapHeight = 0;
//
//        Point point = new Point(1920, 1080);
//
//        mBitmapWidth = mBitmap.getWidth();
//        mBitmapHeight = mBitmap.getHeight();
//
//        Double last_rate = 100.0;
//        for (int i = 300; i < start.y; i++) {
//            //统计不同颜色点的个数
//            Map<Integer, Integer> pixelCntMap = new HashMap<>();
//            for (int j = 0; j < mBitmapWidth; j++) {
//                //获得Bitmap 图片中每一个点的color颜色值
//                int color = mBitmap.getPixel(j, i);
//
//                Integer cnt = pixelCntMap.get(color);
//                if (cnt == null) {
//                    cnt = 1;
//                } else {
//                    cnt += 1;
//                }
//                if (color == -7565675) {
//                    color = -3552042;
//                }
//                pixelCntMap.put(color, cnt);
//            }
//
//            Integer min = getMinCnt(pixelCntMap);
//            if (pixelCntMap.size() > 1 && min > 20) {
//                //计算最大量颜色的比例
//                Integer max = getMaxCnt(pixelCntMap);
//                logger.info("["+ i + "]" + "searchMiddle getMaxCnt max:" + max + ", rate:" + max*100.0/mBitmapWidth + "%");
//                Double cur_rate = max*100.0/mBitmapWidth;
//                if (cur_rate >= last_rate) {
//                //if (i == 950) {
//                    Point top = new Point(540, i);
//                    point = top;
//                    String dir = ToolShell.getFileDirectory(path);
//
//                    Mat source;
//                    source = imread(path);
//                    Imgproc.rectangle(source, top,
//                            new org.opencv.core.Point(top.x + 50, top.y + 50),
//                            new Scalar(0, 255, 0));
//
////                    top.y = 950;
////                    Imgproc.rectangle(source, top,
////                            new org.opencv.core.Point(top.x + 50, top.y + 50),
////                            new Scalar(255, 0, 255));
//
//                    String resPath = dir + String.format("result5.png", i);
//                    //logger.info("opencvCenter resPath:" + resPath);
//                    imwrite(resPath, source);
//
//                    return point;
//                } else {
//                    last_rate = cur_rate;
//                }
//            }
//        }
//        return point;
//    }

}
