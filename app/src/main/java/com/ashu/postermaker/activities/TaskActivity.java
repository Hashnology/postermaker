package com.ashu.postermaker.activities;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ashu.postermaker.BuildConfig;
import com.ashu.postermaker.R;
import com.ashu.postermaker.adapters.MySliderAdapter;
import com.ashu.postermaker.adapters.SliderAdapter;
import com.ashu.postermaker.model_classes.SliderModel;
import com.ashu.postermaker.universal.Utils;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TaskActivity extends AppCompatActivity {
    SliderView sliderView;
    private Context context;
    private MySliderAdapter mySliderAdapterAdapter;
    Button btn_share, btn_rate, btn_more, btn_open_folder;
    RelativeLayout home_rel, features_rel, categories_rel, templates_rel;
    private ArrayList<SliderModel> sliderModelArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.task_layout);

        linkViews();

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApp();
            }
        });

        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateApp();
            }
        });

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreApp();
            }
        });


    }

    private void linkViews() {

        btn_share = findViewById(R.id.btn_share);
        btn_rate = findViewById(R.id.btn_rate);
        btn_more = findViewById(R.id.btn_more);
        btn_open_folder = findViewById(R.id.btn_open_folder);


        sliderView = findViewById(R.id.imageSlider);
        sliderModelArrayList = new ArrayList<>();
        final SliderAdapter adapter = new SliderAdapter(this);
        adapter.setCount(5);
        context = this;
        loadSlider();

        home_rel = findViewById(R.id.home_rel);
        features_rel = findViewById(R.id.features_rel);
        categories_rel = findViewById(R.id.categories_rel);
        templates_rel = findViewById(R.id.templates_rel);

        home_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TaskActivity.this, "Home", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, SliderMainActivityTwo.class));
            }
        });

        features_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TaskActivity.this, "Features", Toast.LENGTH_SHORT).show();
            }
        });

        categories_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TaskActivity.this, "Categories", Toast.LENGTH_SHORT).show();
            }
        });

        templates_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SliderMainActivity.class));
                Toast.makeText(TaskActivity.this, "Templates", Toast.LENGTH_SHORT).show();
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

    private void shareApp() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(TaskActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        try {


            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            }, 2000);

        } catch (Exception e) {
            //e.toString();
            progressDialog.dismiss();

        }
    }

    private void rateApp() {


        //Uri uri = Uri.parse("market://details?id=" + TaskActivity.this.getPackageName());
        Uri uri = Uri.parse("market://details?id=" + "com.postermaster.postermaker");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {

            startActivity(goToMarket);

        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    // Uri.parse("http://play.google.com/store/apps/details?id=" + TaskActivity.this.getPackageName())));
                    Uri.parse("http://play.google.com/store/apps/details?id=" + "com.postermaster.postermaker")));

        }
    }

    private void moreApp() {
        //for open specfic app on play store
        /*try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.postermaster.postermaker" )));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +"com.postermaster.postermaker")))
        }*/

        //open play store


        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps")));

    }
}
