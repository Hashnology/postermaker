package com.ashu.postermaker.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ashu.postermaker.R;
import com.ashu.postermaker.model_classes.Categories;
import com.ashu.postermaker.model_classes.SubCatsModel;
import com.ashu.postermaker.model_classes.TemplatesModel;
import com.ashu.postermaker.universal.Utils;
import com.ashu.postermaker.view_holders.CatsVH;
import com.ashu.postermaker.view_holders.CatsVH2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter2 extends RecyclerView.Adapter<CatsVH2> {

    private Context context;
    private LayoutInflater inflater;
    private List<Categories> data;
    private List<TemplatesModel> dataTempList;
    private CatsVH2 usersViewHolder;
    private Categories current;
    private ImageLoader imageLoader;
    private ArrayList<SubCatsModel> subCatsModelArrayList;


    public CategoriesAdapter2(Context context, List<Categories> data, List<TemplatesModel> dataTempList) {
        this.context = context;
        this.data = data;
        this.dataTempList = dataTempList;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public CatsVH2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_cats2, viewGroup, false);


        usersViewHolder = new CatsVH2(view);

        return usersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CatsVH2 usersViewHolder, final int i) {

        current = data.get(i);
        String name = current.getCat_name();
        String cat_id = current.getCat_id();
        usersViewHolder.btnCAts.setText(name);


        if(i %2 == 1)
        {
            usersViewHolder.btnCAts.setBackgroundResource(R.drawable.my_gradient_drawable);
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            usersViewHolder.btnCAts.setBackgroundResource(R.drawable.my_gradient_drawable2);
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }




        /*inner lsit*/

        ArrayList temp = current.getTemplatesModelArrayList();
        TempaltesAdapterTwo categoriesAdapter = new TempaltesAdapterTwo(context, temp);
        usersViewHolder.rec_cats_subcats.setAdapter(categoriesAdapter);
        categoriesAdapter.notifyDataSetChanged();
//
//        final int pos = usersViewHolder.getAdapterPosition();
//        usersViewHolder.rel_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadSubCats("1");
//
//            }
//        });
    }


    private void loadSubCats(String cat_id) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(usersViewHolder.rec_cats_subcats.getContext(), 1, usersViewHolder.rec_cats_subcats.HORIZONTAL, false);
        usersViewHolder.rec_cats_subcats.setLayoutManager(gridLayoutManager);

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Posts....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        subCatsModelArrayList = new ArrayList<>();
//        subCatsModelArrayList.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.SUB_CATEGORIES_URL + cat_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String result = response.toString();
                        Log.d("result", result);


                        try {
                            JSONArray JA = new JSONArray(result);
                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject object1 = JA.getJSONObject(i);
                                String id = object1.getString("id");
                                String name = object1.getString("name");
                                String image = object1.getString("image");


                                SubCatsModel ob = new SubCatsModel(id, name, image);
                                subCatsModelArrayList.add(ob);
                            }
                            SubCatsAdapter categoriesAdapter = new SubCatsAdapter(context, subCatsModelArrayList);
                            usersViewHolder.rec_cats_subcats.setAdapter(categoriesAdapter);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("result", "error " + error);
                        progressDialog.dismiss();
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> postParam = new HashMap<String, String>();
//                postParam.put("id", "10");
//
//
//                return postParam;
//            }

        };
        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
