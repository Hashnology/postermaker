package com.ashu.postermaker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ashu.postermaker.R;
import com.ashu.postermaker.universal.CONSTANTS;
import com.ashu.postermaker.universal.Utils;
import com.squareup.picasso.Picasso;

public class SingleImageViewActivity extends AppCompatActivity {
    private ImageView iv_settings, iv_edit;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_view_edit_activity);
        context = this;
        String banner = Utils.getPreferences(CONSTANTS.KEY_TEMP_IMAGE, context);


        iv_edit = findViewById(R.id.temp_view);
        Picasso.get().load(banner).into(iv_edit);


    }

    public void onEditClickSingle(View view) {
        startActivity(new Intent(context, EditImage.class));

    }

    public void onCancelClickSingle(View view) {
        startActivity(new Intent(context, SliderMainActivityTwo.class));

    }
}