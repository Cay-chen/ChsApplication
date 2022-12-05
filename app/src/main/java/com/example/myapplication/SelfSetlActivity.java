package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Method;

public class SelfSetlActivity extends BaseActivity {
    private EditText writebankcard_mobileedit;
    private EditText writebankcard_mobileedit1;
    private CustomKeyboard mCustomKeyboard;
    private CustomKeyboard mCustomKeyboard1;
    private Button backBtn;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.self_setl_activity);

        writebankcard_mobileedit = (EditText) findViewById(R.id.identity);
        writebankcard_mobileedit1 = (EditText) findViewById(R.id.his_no);
        backBtn = (Button) findViewById(R.id.back_btn);
        //1 屏蔽掉系统默认输入法
        if (Build.VERSION.SDK_INT <= 10) {
            writebankcard_mobileedit.setInputType(InputType.TYPE_NULL);
            writebankcard_mobileedit1.setInputType(InputType.TYPE_NULL);
        } else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(writebankcard_mobileedit, false);
                setShowSoftInputOnFocus.invoke(writebankcard_mobileedit1, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //2 初试化键盘
        MyKeyboardView keyboardView = (MyKeyboardView) findViewById(R.id.customKeyboard);
        // mCustomKeyboard.showKeyboard();
        // mCustomKeyboard1.showKeyboard();

        writebankcard_mobileedit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mCustomKeyboard = new CustomKeyboard(SelfSetlActivity.this, keyboardView, writebankcard_mobileedit);
                mCustomKeyboard.showKeyboard();
                return false;
            }
        });
        writebankcard_mobileedit1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mCustomKeyboard1 = new CustomKeyboard(SelfSetlActivity.this, keyboardView, writebankcard_mobileedit1);
                mCustomKeyboard1.showKeyboard();
                return false;
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //物理返回键
    @Override
    public void onBackPressed() {
        if (mCustomKeyboard.isShowKeyboard()) {
            mCustomKeyboard.hideKeyboard();
        } else if (mCustomKeyboard1.isShowKeyboard()) {
            mCustomKeyboard1.hideKeyboard();
        } else {
            finish();
        }
    }
}