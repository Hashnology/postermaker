package com.ashu.postermaker.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ashu.postermaker.R;
import com.ashu.postermaker.adapters.CategoriesAdapter;
import com.ashu.postermaker.adapters.MySliderAdapter;
import com.ashu.postermaker.adapters.SliderAdapter;
import com.ashu.postermaker.model_classes.Categories;
import com.ashu.postermaker.model_classes.SliderModel;
import com.ashu.postermaker.model_classes.TemplatesModel;
import com.ashu.postermaker.universal.Utils;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class SliderMainActivity extends AppCompatActivity {

    SliderView sliderView;
    private ProgressDialog progressDialogue;
    private Context context;
    private ArrayList<SliderModel> sliderModelArrayList;
    private ArrayList<Categories> categoriesArrayList;
    private ArrayList<TemplatesModel> templatesModelArrayList;
    private MySliderAdapter mySliderAdapterAdapter;
    private CategoriesAdapter categoriesAdapter;
    private RecyclerView rec_cats;
    private LinearLayout linear_slider;
    private CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_slider);
        context = this;

        categoriesArrayList = new ArrayList<>();
        rec_cats = findViewById(R.id.rec_cats);
        linear_slider=findViewById(R.id.linear_slider);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(rec_cats.getContext(), rec_cats.VERTICAL, false);
        rec_cats.setLayoutManager(gridLayoutManager);
        rec_cats.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(rec_cats, false);
        rec_cats.setHasFixedSize(true);
        rec_cats.setItemViewCacheSize(20);
        rec_cats.setDrawingCacheEnabled(true);
        rec_cats.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        cardView= findViewById(R.id.cardView);
        sliderView = findViewById(R.id.imageSlider);
        sliderModelArrayList = new ArrayList<>();
        final SliderAdapter adapter = new SliderAdapter(this);
        adapter.setCount(5);
//        loadSlider();
        loadCats();

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });


    }

    private void loadSlider() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading slider....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        sliderModelArrayList.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.LoadSlider,
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
                                String is_active = object1.getString("is_active");
                                String is_deleted = object1.getString("is_deleted");


                                SliderModel ob = new SliderModel(id, name, image, is_active, is_deleted);
                                sliderModelArrayList.add(ob);
                            }
                            mySliderAdapterAdapter = new MySliderAdapter(context, sliderModelArrayList);
                            sliderView.setSliderAdapter(mySliderAdapterAdapter);
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


    private void loadCats() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Posts....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        categoriesArrayList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.GET_FINAL_TEMPLATES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String result = response.toString();
                        Log.d("result", result);


                        try {


                            JSONArray JA = new JSONArray(result);
                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject object1 = JA.getJSONObject(i);

                                templatesModelArrayList = new ArrayList<>();
                                Iterator<?> keys = object1.keys();
                                String key = null;
                                while (keys.hasNext()) {
                                    key = (String) keys.next();

                                    if (object1.get(key) instanceof JSONObject) {
                                        System.out.println(key); // do whatever you want with it
                                    }
                                }


                                JSONArray JA_Inner = object1.getJSONArray(key);

                                String category_name = null;
                                for (int j = 0; j < JA_Inner.length(); j++) {

                                    JSONObject jsonObject2 = JA_Inner.getJSONObject(j);
                                    String id = jsonObject2.getString("id");
                                    String category_id = jsonObject2.getString("category_id");
                                    String template_name = jsonObject2.getString("name");
                                    String banner_image = jsonObject2.getString("banner_image");
                                    category_name = jsonObject2.getString("category_name");
                                    TemplatesModel obb = new TemplatesModel(id, template_name, category_id, category_name, banner_image);
                                    templatesModelArrayList.add(obb);


                                }
                                int size = templatesModelArrayList.size();

                                Categories ob = new Categories(key, category_name, templatesModelArrayList);
                                categoriesArrayList.add(ob);
                                categoriesAdapter = new CategoriesAdapter(context, categoriesArrayList, templatesModelArrayList);
                                rec_cats.setAdapter(categoriesAdapter);
                                categoriesAdapter.notifyDataSetChanged();


                            }

//                            rec_cats.setOnScrollListener(new HidingScrollListener(linear_slider) {
//                                @Override
//                                public void onMoved(int distance) {
//                                    linear_slider.setTranslationY(-distance);
//                                }
//                            });

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
}
