package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import java.util.Objects;

public class HomeActivity extends BaseActivity {
    private LinearLayout ll_self;
    private LinearLayout ll_chs;
    private LinearLayout ll_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        ll_self = findViewById(R.id.ll_self);
        ll_chs = findViewById(R.id.ll_chs);
        ll_play = findViewById(R.id.ll_play);
        ll_self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this, SelfSetlActivity.class);
                startActivity(intent);
            }
        });
    }

    //物理返回键
    private long firstTime = 0;

    @Override
    public void onBackPressed() {

        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 800) {
            // Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            new XPopup.Builder(HomeActivity.this).asInputConfirm("退出系统", "请输入密码",
                            new OnInputConfirmListener() {
                                @Override
                                public void onConfirm(String text) {
                                    if (Objects.equals(text, "123456")) {
                                        HomeActivity.this.finish();
                                    } else {
                                        Toast.makeText(HomeActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                    .show();
            //finish();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU) {//MENU键
            Log.d("TAG", "onKeyDown: ");
            //监控/拦截菜单键
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

/*    @Override
    public void onAttachedToWindow() {
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        super.onAttachedToWindow();


    }*/
}