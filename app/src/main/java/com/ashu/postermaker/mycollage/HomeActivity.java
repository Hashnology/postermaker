package com.ashu.postermaker.mycollage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.ashu.postermaker.R;
import com.thuytrinh.android.collageviews.MultiTouchListener;

public class HomeActivity extends Activity {
    RelativeLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rootView = findViewById(R.id.collageBgView);

        float i = rootView.getX();
        float j = rootView.getY();
        float k = rootView.getPivotX();


//        rootView.setOnTouchListener(new OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                return true;
//            }
//        });


        findViewById(R.id.tv_sale).setOnTouchListener(new MultiTouchListener());
        findViewById(R.id.tv_off).setOnTouchListener(new MultiTouchListener());
        rootView.setOnTouchListener(new OnDragTouchListener(findViewById(R.id.tv_sale)));

        findViewById(R.id.tv_sale).setOnTouchListener(new OnDragTouchListener(findViewById(R.id.tv_sale),rootView));
        findViewById(R.id.tv_off).setOnTouchListener(new OnDragTouchListener(findViewById(R.id.tv_off),rootView));
        findViewById(R.id.collageView2).setOnTouchListener(new OnDragTouchListener(findViewById(R.id.collageView2),rootView));

//        findViewById(R.id.collageView1).setOnTouchListener(new MultiTouchListener());
//        findViewById(R.id.collageView2).setOnTouchListener(new MultiTouchListener());
//        findViewById(R.id.collageView3).setOnTouchListener(new MultiTouchListener());
//        findViewById(R.id.collageView4).setOnTouchListener(new MultiTouchListener());


    }
}