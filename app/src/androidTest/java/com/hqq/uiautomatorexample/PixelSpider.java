package com.hqq.uiautomatorexample;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.opencv.imgcodecs.Imgcodecs.imread;

/**
 * Created by xinmei365 on 2018/2/17.
 */

public class PixelSpider {

    private static Boolean isWhite(Bitmap bitmap, Point point) {
        Integer posx = Double.valueOf(point.x).intValue();
        Integer posy = Double.valueOf(point.y).intValue();

        if (posx < 0
                || posx >= bitmap.getWidth()
                || posy < 0
                || posy >= bitmap.getHeight()) {
            return false;
        }
        Integer color = bitmap.getPixel(posx, posy);

        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        if (r > 250 && g > 250 && b > 250) {
            return true;
        }
        return false;
    }

    private static void addWhiteList(Bitmap bitmap,
                                     Point point, List<Point> whiteList,
                                     Double left, Double right,
                                     Double top, Double bottom) {
        if (Double.compare(point.x, left) >= 0
                && Double.compare(point.x, right) < 0
                && Double.compare(point.y, top) >= 0
                && Double.compare(point.y, bottom) < 0) {

            if (isWhite(bitmap, point)) {
                whiteList.add(point);
            }
        }
    }
    private static void addList(Point point, List<Point> whiteList,
                                Double left, Double right,
                                Double top, Double bottom) {
        if (Double.compare(point.x, left) >= 0
                && Double.compare(point.x, right) < 0
                && Double.compare(point.y, top) >= 0
                && Double.compare(point.y, bottom) < 0) {
            whiteList.add(point);
        }
    }

    public static Point findTopWhitePoint(Bitmap bitmap, Point first) {
        //500*500区域
        List<Point> whiteList = new ArrayList<>();
        Map<Point, Boolean> pointMap = new HashMap<>();
        whiteList.add(first);

        Double x0 = first.x - 25;
        Double x1 = first.x + 25;
        Double y0 = first.y - 25;
        Double y1 = first.y + 25;

        Map<String, Boolean> usedMap = new HashMap<>();
        for (Integer i = 0;i < 25*25; i++) {
            if (whiteList.size() == 0) {
                break;
            }
            Point curPoint = whiteList.get(0);
            whiteList.remove(0);

            Point left = new Point(curPoint.x-1, curPoint.y);
            Point right = new Point(curPoint.x+1, curPoint.y);
            Point top = new Point(curPoint.x, curPoint.y-1);
            Point bottom = new Point(curPoint.x, curPoint.y+1);

            if (!usedMap.containsKey(left.toString())) {
                addWhiteList(bitmap, left, whiteList, x0, x1, y0, y1);
            }
            if (!usedMap.containsKey(right.toString())) {
                addWhiteList(bitmap, right, whiteList, x0, x1, y0, y1);
            }
            if (!usedMap.containsKey(top.toString())) {
                addWhiteList(bitmap, top, whiteList, x0, x1, y0, y1);
            }
            if (!usedMap.containsKey(bottom.toString())) {
                addWhiteList(bitmap, bottom, whiteList, x0, x1, y0, y1);
            }

            usedMap.put(left.toString(), true);
            usedMap.put(right.toString(), true);
            usedMap.put(top.toString(), true);
            usedMap.put(bottom.toString(), true);

        }

        Point point = searchTopWhitePixel(whiteList);
        System.exit(0);
        return first;
    }
    private static Point searchTopWhitePixel(List<Point>  whiteList) {
        //输出最右侧的边
        Point top = whiteList.get(0);
        for (Integer i = 0;i < whiteList.size(); i++) {
            Point curPoint = whiteList.get(i);
            if (Double.compare(curPoint.y, top.y) < 0) {
                top = curPoint;
            }
        }
        return  top;
    }

    private static Point searchRightWhitePixel(List<Point> whiteList) {
        //输出最右侧的边
        Point pointRight = whiteList.get(0);
        for (Integer i = 0;i < whiteList.size(); i++) {
            Point curPoint = whiteList.get(i);
            if (Double.compare(curPoint.x, pointRight.x) > 0) {
                pointRight = curPoint;
            }
            if (Double.compare(curPoint.x, pointRight.x) == 0
                    && Double.compare(curPoint.y, pointRight.y) < 0) {
                pointRight = curPoint;
            }
        }
        //获取5个像素以内的，y最小的值
        Point pointFinal = new Point(pointRight.x, pointRight.y);
        for (Integer i = 0; i< whiteList.size(); i++) {
            Point curPoint = whiteList.get(i);
            if (Math.abs(curPoint.x - pointRight.x)< 2) {
                if (curPoint.y < pointFinal.y) {
                    pointFinal = curPoint;
                }
            }
        }
        return  pointFinal;
    }

    public static Point findWhitePoint(Bitmap bitmap, Point first) {
        //500*500区域
        List<Point> whiteList = new ArrayList<>();
        Map<Point, Boolean> pointMap = new HashMap<>();
        whiteList.add(first);

        Map<String, Boolean> usedMap = new HashMap<>();
        for (Integer i = 0;i < 500*500; i++) {
            if (whiteList.size() == 0) {
                break;
            }
            Point curPoint = whiteList.get(0);
            whiteList.remove(0);

            Point left = new Point(curPoint.x-1, curPoint.y);
            Point right = new Point(curPoint.x+1, curPoint.y);
            Point top = new Point(curPoint.x, curPoint.y-1);
            Point bottom = new Point(curPoint.x, curPoint.y+1);
            if (!usedMap.containsKey(left.toString())) {
                addList(left, whiteList, first.x, first.x + 500, first.y, first.y + 500);
            }
            if (!usedMap.containsKey(right.toString())) {
                addList(right, whiteList, first.x, top.x + 500, first.y, first.y + 500);
            }
            if (!usedMap.containsKey(top.toString())) {
                addList(top, whiteList, first.x, first.x + 500, first.y, first.y + 500);
            }
            if (!usedMap.containsKey(bottom.toString())) {
                addList(bottom, whiteList, first.x, first.x + 500, top.y, first.y + 500);
            }

            usedMap.put(left.toString(), true);
            usedMap.put(right.toString(), true);
            usedMap.put(top.toString(), true);
            usedMap.put(bottom.toString(), true);

            if (isWhite(bitmap, curPoint)) {
                return curPoint;
            }

        }
        System.exit(0);
        return first;
    }

    public static Point scanAllWhitePoint(Bitmap bitmap, Point first, Point start) {
        //500*500区域
        List<Point> whiteList = new ArrayList<>();
        Map<Point, Boolean> pointMap = new HashMap<>();
        whiteList.add(first);

        Integer startPos = 0;
        Map<String, Boolean> usedMap = new HashMap<>();


        String dir = ToolShell.getStoragePath("");
        String chessPath = dir + "../chess.png";
        Mat templete = imread(chessPath);

        Double x0 = first.x;
        Double y0 = first.y;
        Double x1 = start.x - 10 - templete.width()/2;
        Double y1 = start.y;
        if (Double.compare(x0, x1) > 0) {
            x1 = bitmap.getWidth() - 1.0;
        }

        for (Integer i = 0;i < 500*500; i++) {
            if (whiteList.size() == 0) {
                break;
            }
            if (startPos >= whiteList.size()) {
                break;
            }
            Point curPoint = whiteList.get(startPos);
            startPos+=1;

            Point left = new Point(curPoint.x-1, curPoint.y);
            Point right = new Point(curPoint.x+1, curPoint.y);
            Point top = new Point(curPoint.x, curPoint.y-1);
            Point bottom = new Point(curPoint.x, curPoint.y+1);
            if (!usedMap.containsKey(left.toString())) {
                addWhiteList(bitmap, left, whiteList, x0, x1, y0, y1);
            }

            if (!usedMap.containsKey(right.toString())) {
                addWhiteList(bitmap, right, whiteList, x0, x1, y0, y1);
            }
            if (!usedMap.containsKey(top.toString())) {
                addWhiteList(bitmap, top, whiteList, x0, x1, y0, y1);
            }
            if (!usedMap.containsKey(bottom.toString())) {
                addWhiteList(bitmap, bottom, whiteList, x0, x1, y0, y1);
            }

            usedMap.put(left.toString(), true);
            usedMap.put(right.toString(), true);
            usedMap.put(top.toString(), true);
            usedMap.put(bottom.toString(), true);
        }
        //输出最右侧的边
        Point pointRight = searchRightWhitePixel(whiteList);
        return pointRight;
    }
}
