package com.ashu.postermaker.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.ashu.postermaker.R;
import com.ashu.postermaker.activities.EditImage;
import com.ashu.postermaker.interfaces.LayersListner;
import com.ashu.postermaker.model_classes.Users;
import com.ashu.postermaker.universal.AppController;
import com.ashu.postermaker.view_holders.UsersViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.ashu.postermaker.R.drawable.ic_remove_red_eye_black_24dp;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Users> data;
    private UsersViewHolder usersViewHolder;
    private Users current;
    private ImageLoader imageLoader;
    private LayersListner layersListner;

    public UsersAdapter(Context context, List<Users> data, LayersListner layersListner) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.layersListner = layersListner;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_layers, viewGroup, false);

        usersViewHolder = new UsersViewHolder(view);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersViewHolder usersViewHolder, final int i) {

        current = data.get(i);
        String name = current.getCat_name();
        String type = current.getCat_id();

        if (type.equalsIgnoreCase("image")) {

            Picasso.get().load(name).into(usersViewHolder.iv_icon);
            usersViewHolder.tv_heading.setVisibility(View.GONE);
            usersViewHolder.iv_icon.setVisibility(View.VISIBLE);

        } else {


            usersViewHolder.tv_heading.setText(name);
            usersViewHolder.tv_heading.setVisibility(View.VISIBLE);
            usersViewHolder.iv_icon.setVisibility(View.GONE);
        }

        final int pos = usersViewHolder.getAdapterPosition();
        usersViewHolder.rel_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layersListner.clickOnLayer(pos);


                if (usersViewHolder.iv_image.getVisibility() == View.VISIBLE) {
                    usersViewHolder.iv_image_hidden.setVisibility(View.VISIBLE);
                    usersViewHolder.iv_image.setVisibility(View.GONE);

                } else {
                    usersViewHolder.iv_image_hidden.setVisibility(View.GONE);
                    usersViewHolder.iv_image.setVisibility(View.VISIBLE);

                }


//                usersViewHolder.iv_image.setImageDrawable(context.getResources().getDrawable(ic_remove_red_eye_black_24dp));
//                usersViewHolder.iv_image.setImageDrawable(ic_remove_red_eye_black_24dp);
//                Toast.makeText(context, "item cliecked " + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
