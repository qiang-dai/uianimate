# Android 自动化测试 UiautomatorExample
1. 参考
    - http://blog.csdn.net/eclipsexys/article/details/45622813/
    - https://www.jianshu.com/p/7718860ec657
    
关注 UiAutomator

2. http://blog.csdn.net/zhubaitian/article/details/39777951

3. ADB
登录
./platform-tools/adb -s 69DDU16519018529 shell

4. 下载文件
./adb -s 69DDU16519018529 pull /storage/emulated/0/Android/data/com.hqq.uiautomatorexample/cache/screen.png

4.1 上传
/Users/xinmei365/Downloads/android-ndk-r14b/platform-tools/adb -s 69DDU16519018529 push chess.png /storage/emulated/0/Android/data/com.hqq.uiautomatorexample/cache/
/Users/xinmei365/Downloads/android-ndk-r14b/platform-tools/adb -s 69DDU16519018529 push white_dot.png /storage/emulated/0/Android/data/com.hqq.uiautomatorexample/cache/

5. 下载opencv sdk，然后
安装 opencv-manager
再导入 java 目录
再设置modle dependency
再设置 build.gradle的版本号，与主工程一致

6. cd UiautomatorExample

7.Todo
    1,采用入栈的算法，遍历右侧定点
    2,根据start的位置，限制查找范围
    3,长方形根据对称点，避免跑到耳朵上
    4,执行过程中出现sqllite错误
    5,ECONNRESET错误








