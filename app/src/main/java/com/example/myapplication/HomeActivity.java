package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

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
                intent.setClass(HomeActivity.this,SelfSetlActivity.class);
                startActivity(intent);
            }
        });
    }
}