package com.ashu.postermaker.model_classes;

import android.widget.ImageView;
import android.widget.TextView;

public class MyLayers {
    private String type;
    private TextView tv_heading;
    private ImageView iv_icon;

    public MyLayers(String type, TextView tv_heading, ImageView iv_icon) {
        this.type = type;
        this.tv_heading = tv_heading;
        this.iv_icon = iv_icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TextView getTv_heading() {
        return tv_heading;
    }

    public void setTv_heading(TextView tv_heading) {
        this.tv_heading = tv_heading;
    }

    public ImageView getIv_icon() {
        return iv_icon;
    }

    public void setIv_icon(ImageView iv_icon) {
        this.iv_icon = iv_icon;
    }
}