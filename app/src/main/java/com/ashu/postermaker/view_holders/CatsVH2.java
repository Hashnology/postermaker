package com.ashu.postermaker.view_holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashu.postermaker.R;


public class CatsVH2 extends RecyclerView.ViewHolder {
    public Button btnCAts;
    public RelativeLayout rel_item;
    public ImageView iv_sub_cats;
    public RecyclerView rec_cats_subcats;


    public CatsVH2(@NonNull View itemView) {
        super(itemView);

        /*get here ids */
        btnCAts = itemView.findViewById(R.id.btn_cats);
        rel_item = itemView.findViewById(R.id.rel_item);
        iv_sub_cats = itemView.findViewById(R.id.iv_image_subcats);
        rec_cats_subcats = itemView.findViewById(R.id.rec_cats_subcats);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(rec_cats_subcats.getContext(), 1, rec_cats_subcats.VERTICAL, false);
        rec_cats_subcats.setLayoutManager(gridLayoutManager);

    }
}
