package com.ashu.postermaker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.ashu.postermaker.R;
import com.ashu.postermaker.interfaces.LayersListner;
import com.ashu.postermaker.model_classes.SliderModel;
import com.ashu.postermaker.model_classes.Users;
import com.ashu.postermaker.view_holders.SliderViewHolder;
import com.ashu.postermaker.view_holders.UsersViewHolder;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MySliderAdapter extends SliderViewAdapter<SliderViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<SliderModel> data;
    private SliderViewHolder sliderViewHolder;
    private SliderModel current;
    private ImageLoader imageLoader;
    private LayersListner layersListner;

    public MySliderAdapter(Context context, List<SliderModel> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);

    }


    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = inflater.inflate(R.layout.image_slider_layout_item, parent, false);

        sliderViewHolder = new SliderViewHolder(view);
        return sliderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SliderViewHolder sliderViewHolder, final int i) {

        current = data.get(i);
        String name = current.getName();
        String image = current.getImage();


        sliderViewHolder.textViewDescription.setText(name);

        Glide.with(sliderViewHolder.imageViewBackground)
                .load(image)
                .fitCenter()
                .into(sliderViewHolder.imageViewBackground);

    }


    @Override
    public int getCount() {
        return data.size();
    }
}
