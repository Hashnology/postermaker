package com.ashu.postermaker.view_holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashu.postermaker.R;


public class SubCatsVH extends RecyclerView.ViewHolder {
    public TextView tv_catname;
    public RelativeLayout rel_item;
    public ImageView iv_image_subcats;

    public SubCatsVH(@NonNull View itemView) {
        super(itemView);

        /*get here ids */
        iv_image_subcats=itemView.findViewById(R.id.iv_image_subcats);
//        tv_catname = itemView.findViewById(R.id.tv_s);
        rel_item = itemView.findViewById(R.id.rel_item_subCats);
    }
}
