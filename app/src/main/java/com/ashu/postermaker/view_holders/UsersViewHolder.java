package com.ashu.postermaker.view_holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashu.postermaker.R;


public class UsersViewHolder extends RecyclerView.ViewHolder {
    public ImageView iv_image, iv_icon,iv_image_hidden;
    public TextView tv_heading;
    public RelativeLayout rel_item;

    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);

        /*get here ids */
        tv_heading = itemView.findViewById(R.id.tv_heading);
        iv_icon = itemView.findViewById(R.id.iv_icon);
        iv_image_hidden= itemView.findViewById(R.id.iv_image_hidden);
        iv_image = itemView.findViewById(R.id.iv_image);
        rel_item = itemView.findViewById(R.id.rel_item);
    }
}
