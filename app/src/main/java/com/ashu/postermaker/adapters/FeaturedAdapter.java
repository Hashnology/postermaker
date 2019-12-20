package com.ashu.postermaker.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ashu.postermaker.App;
import com.ashu.postermaker.R;
import com.ashu.postermaker.interfaces.FeatureListDelegate;
import com.ashu.postermaker.model_classes.FeatureModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder> {

    String baseURL = "http://najamchaudhry.com/poster/public/images/poster/home_screen/";

    private Context context;
    private List<FeatureModel> list;
    private FeatureListDelegate delegate;

    public FeaturedAdapter() {
        context= App.Companion.getInstance();
        list = new ArrayList<>();
    }

    public void setDelegate(FeatureListDelegate delegate){
        if (this.delegate != null){
            this.delegate = null;
        }
        this.delegate = delegate;
    }

    public void add(final FeatureModel model){
        list.add(model);
        App.Companion.getInstance().getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void addAll(final ArrayList<FeatureModel> models){
        list.addAll(models);
        App.Companion.getInstance().getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }



    public void removeAll(){
        final int size = list.size();
        list.clear();
        App.Companion.getInstance().getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                notifyItemRangeRemoved(0, size);
            }
        });
    }

    public void removeAt(final int index){
        if (index < 0 || index >= list.size()){
            return;
        }
        list.remove(index);
        App.Companion.getInstance().getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                notifyItemRemoved(index);
                notifyItemRangeChanged(index, list.size());
            }
        });
    }


    @NonNull
    @Override
    public FeaturedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feature_itemview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.ViewHolder viewHolder, final int i) {

        final FeatureModel model = list.get(i);

        String path = model.getImage();
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delegate != null){
                    delegate.onItemClick(i, model);
                }
            }
        });

//        Picasso.get().load(baseURL+path).placeholder(R.drawable.image_place_holder).into(viewHolder.view);
        Picasso.get().load(path).placeholder(R.drawable.image_place_holder).into(viewHolder.view);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }
        private void init(View v){
            view = v.findViewById(R.id.iv_featured_poster);
        }
    }
}
