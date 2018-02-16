package com.hqq.uiautomatorexample;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by xinmei365 on 2018/2/14.
 */

public class ToolShell {
    private static Logger logger = Logger.getLogger("ToolShell");

    public static String runShellCmd(String cmd) {
        String output = null;
        try {
            Process pro = Runtime.getRuntime().exec(cmd);
            pro.waitFor();
            InputStream in = pro.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = read.readLine())!=null){
                output = line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            output = e.getStackTrace().toString();
        }
        return output;
    }

    public static String getFileDirectory(String fileName) {
        if (fileName.contains("/")) {
            String path = fileName.substring(0, fileName.lastIndexOf("/")) + "/";
            return path;
        }
        if (fileName.contains("\\")) {
            String path = fileName.substring(0, fileName.lastIndexOf("\\")) + "\\";
            return path;
        }
        return fileName;
    }
    public static void sleep(Integer millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getStoragePath(String part) {
        File folder = new File(getTargetContext().getExternalCacheDir().getAbsolutePath() + "/pics/" + part);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder.getPath() + "/";
    }
}
