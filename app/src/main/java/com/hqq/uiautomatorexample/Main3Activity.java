package com.hqq.uiautomatorexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // 取得LinearLayout 物件
        LinearLayout ll = (LinearLayout)findViewById(R.id.viewObj);

        // 将TextView 加入到LinearLayout 中
        TextView tv = new TextView(this);
        tv.setText("Hello World");
        ll. addView ( tv );

        // 将Button 1 加入到LinearLayout 中
        Button b1 = new Button(this);
        b1.setText("取消");
        ll. addView ( b1 );

        // 将Button 2 加入到LinearLayout 中
        Button b2 = new Button(this);
        b2.setText("确定");
        ll. addView ( b2 );

        // 从LinearLayout 中移除Button 1
        ll. removeView ( b1 );
    }
}
