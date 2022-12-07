package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.utlis.Http;

import java.io.IOException;
import java.lang.reflect.Method;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SelfSetlActivity extends BaseActivity {
    private EditText writebankcard_mobileedit;
    private EditText writebankcard_mobileedit1;
    private CustomKeyboard mCustomKeyboard;
    private CustomKeyboard mCustomKeyboard1;
    private Button checkBtn;

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
        Button backBtn = (Button) findViewById(R.id.back_btn);
        checkBtn = (Button) findViewById(R.id.self_check_btn);
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

        /**
         * 返回按键 返回上一级
         */
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Http http = new Http();
                        try {
                            String back =http.get("http://192.168.121.189:8088/login");
                            Log.d("TAG", "onClick: "+back);
                            Message message = Message.obtain();
                            message.what = 11;
                            Bundle bundle = new Bundle();
                            bundle.putString("response", back);
                            message.setData(bundle);
                            handler.sendMessage(message);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });





    }
/*
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
*/
private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what == 11) {
            Log.d("TAG", "handleMessage: "+ msg.getData().getString("response"));
           // mTv.setText("onResponse:" + msg.getData().getString("response"));
        }
    }
};

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    //物理返回键
    @Override
    public void onBackPressed() {
        if (mCustomKeyboard!=null && mCustomKeyboard.isShowKeyboard() ) {
            mCustomKeyboard.hideKeyboard();
        } else if (mCustomKeyboard1!=null &&mCustomKeyboard1.isShowKeyboard()  ) {
            mCustomKeyboard1.hideKeyboard();
        } else {
            finish();
        }
    }
}