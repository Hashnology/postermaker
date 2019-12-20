package com.ashu.postermaker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.ashu.postermaker.R;
import com.ashu.postermaker.model_classes.SubCatsModel;
import com.ashu.postermaker.view_holders.SubCatsVH;
import com.ashu.postermaker.view_holders.SubCatsVH2;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCatsAdapter2 extends RecyclerView.Adapter<SubCatsVH2> {

    private Context context;
    private LayoutInflater inflater;
    private List<SubCatsModel> data;
    private SubCatsVH2 usersViewHolder;
    private SubCatsModel current;
    private ImageLoader imageLoader;


    public SubCatsAdapter2(Context context, List<SubCatsModel> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public SubCatsVH2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_sub_cats, viewGroup, false);

        usersViewHolder = new SubCatsVH2(view);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SubCatsVH2 usersViewHolder, final int i) {

        current = data.get(i);
        String name = current.getSub_cat_name();
        String image = current.getSub_cat_image();
//        usersViewHolder.tv_catname.setText(name);
        Picasso.get().load(image).into(usersViewHolder.iv_image_subcats);


        final int pos = usersViewHolder.getAdapterPosition();
        usersViewHolder.rel_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
