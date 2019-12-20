package com.ashu.postermaker.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.ashu.postermaker.R;
import com.ashu.postermaker.activities.SingleImageViewActivity;
import com.ashu.postermaker.model_classes.TemplatesModel;
import com.ashu.postermaker.universal.CONSTANTS;
import com.ashu.postermaker.universal.Utils;
import com.ashu.postermaker.view_holders.TempaltesVH;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TempaltesAdapterTwo extends RecyclerView.Adapter<TempaltesVH> {

    private Context context;
    private LayoutInflater inflater;
    private List<TemplatesModel> data;
    private TempaltesVH usersViewHolder;
    private TemplatesModel current;
    private ImageLoader imageLoader;


    public TempaltesAdapterTwo(Context context, List<TemplatesModel> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public TempaltesVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_sub_cats2, viewGroup, false);

        usersViewHolder = new TempaltesVH(view);
        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TempaltesVH usersViewHolder, final int i) {

        current = data.get(i);
        String name = current.getCat_name();
        String image = current.getBanner_image();
//        usersViewHolder.tv_catname.setText(name);
        Picasso.get().load(image).into(usersViewHolder.iv_image_subcats);


        final int pos = usersViewHolder.getAdapterPosition();
        usersViewHolder.iv_image_subcats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String banner_image = data.get(pos).getBanner_image();
                String tempId = data.get(pos).getTemplate_id();
                Utils.savePreferences(CONSTANTS.KEY_TEMP_IMAGE, banner_image, context);
                Utils.savePreferences(CONSTANTS.KEY_TEMP_ID, tempId, context);
                Toast.makeText(context, "template" + tempId, Toast.LENGTH_SHORT).show();

                context.startActivity(new Intent(context, SingleImageViewActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
