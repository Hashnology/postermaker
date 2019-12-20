package com.ashu.postermaker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.text.Layout;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ashu.postermaker.R;

public class Splash extends AppCompatActivity {
    ImageView imageView;
    LinearLayout layout;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_fa);
        imageView=findViewById(R.id.logo);
        layout=findViewById(R.id.layout_text);
        anim= AnimationUtils.loadAnimation(this,R.anim.splashanimation);
        layout.startAnimation(anim);
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000);
        rotate.setInterpolator(new LinearInterpolator());

        ImageView image= (ImageView) findViewById(R.id.logo);

        image.startAnimation(rotate);


       Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this,TaskActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, 6000);
    }
}
