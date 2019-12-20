package com.ashu.postermaker.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ashu.postermaker.R;
import com.ashu.postermaker.universal.AppController;
import com.squareup.picasso.Picasso;
import com.xiaopo.flying.sticker.StickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestMove extends Activity implements View.OnTouchListener {

    TextView _view;
    ViewGroup _root,rootmain;
    private int _xDelta;
    private int _yDelta;
    ImageView iv22, imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        _root = (ViewGroup) findViewById(R.id.root);
        rootmain= (ViewGroup) findViewById(R.id.root);
        imageView = (ImageView) findViewById(R.id.iv_main);
//        _view = new TextView(this);
//
//        iv22 = new ImageView(this);
//
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 50);
//        layoutParams.leftMargin = 150;
//        layoutParams.topMargin = 150;
//        layoutParams.bottomMargin = -150;
//        layoutParams.rightMargin = -150;
//
//        _view.setLayoutParams(layoutParams);
//
//        _view.setOnTouchListener(this);
//        _root.addView(_view);

//        iv.setBackgroundResource(R.drawable.logo);
//        iv.setImageDrawable(R.drawable.logo);
//        imageView.setOnTouchListener(this);
//        imageView.setOnTouchListener(this);
//        _root.addView(iv22);

        getTamplatesArray();
//        _root.addView(imageView);
    }

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
//                layoutParams.rightMargin = -250;
//                layoutParams.bottomMargin = -250;

                view.setLayoutParams(layoutParams);
                break;
        }
        _root.invalidate();
        rootmain.invalidate();
        return true;
    }


    private void getTamplatesArray() {


        JsonArrayRequest req = new JsonArrayRequest("http://poster.fadootutorial.com/api/templates",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String result = response.toString();
                        Log.d("result", response.toString());
                        try {
                            JSONArray JA = new JSONArray(result);
                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject firstObject = JA.getJSONObject(i);
                                String id = firstObject.getString("id");
                                String category_id = firstObject.getString("category_id");
                                String name = firstObject.getString("name");
                                JSONArray JA_Headings = firstObject.getJSONArray("headings");

                                JSONArray JA_Images = firstObject.getJSONArray("images");
                                for (int k = 0; k <= JA_Images.length(); k++) {
                                    JSONObject obImages = JA_Images.getJSONObject(k);

                                    String image = obImages.getString("image");
                                    String x_axis = obImages.getString("x_axis");
                                    String y_axis = obImages.getString("y_axis");
                                    String width = obImages.getString("width");
                                    String height = obImages.getString("height");

                                    /*testing code*/

                  /*                  FrameLayout rl = findViewById(R.id.frame);
                                    ImageView iv;
                                    FrameLayout.LayoutParams params;

                                    int yellow_iv_id = k; // Some arbitrary ID value.

                                    iv = new ImageView(TestMove.this);
                                    iv.setId(yellow_iv_id);
//                                    iv.setBackgroundColor(Color.YELLOW);
                                    params = new FrameLayout.LayoutParams(300, 400);
                                    params.leftMargin = Integer.parseInt(x_axis);
                                    params.topMargin = Integer.parseInt(y_axis);

                                    Picasso.get().load(image).into(iv);*/

                                    if (k == 0) {
                                        ImageView ivv = new ImageView(TestMove.this);
                                        ivv.setOnTouchListener(TestMove.this);

                                        Picasso.get().load(image).into(ivv);
                                        _root.addView(ivv);

                                    }
                                    if (k == 0) {
                                        ImageView ivv1 = new ImageView(TestMove.this);
                                        ivv1.setOnTouchListener(TestMove.this);

                                        Picasso.get().load(image).into(ivv1);
                                        _root.addView(ivv1);

                                    }
                                    if (k == 1) {
                                        Picasso.get().load(image).into(imageView);
//                                        _root.addView(imageView);
                                    }


//
                                }

                                for (int j = 0; j <= JA_Headings.length(); j++) {
                                    JSONObject headingsObject = JA_Headings.getJSONObject(j);

                                    String heading = headingsObject.getString("heading");
                                    String x_axis = headingsObject.getString("x_axis");
                                    String y_axis = headingsObject.getString("y_axis");
                                    String font = headingsObject.getString("font");
                                    String font_size = headingsObject.getString("font_size");


                                    if (j == 0) {
                                        TextView tv1 = new TextView(TestMove.this);
                                        tv1.setOnTouchListener(TestMove.this);
                                        tv1.setText(heading);
                                        tv1.setOnTouchListener(TestMove.this);

                                        rootmain.addView(tv1);

                                    }
                                    if (j == 1) {
                                        TextView tv2 = new TextView(TestMove.this);
                                        tv2.setOnTouchListener(TestMove.this);
                                        tv2.setText(heading);
                                        tv2.setOnTouchListener(TestMove.this);
                                        rootmain.addView(tv2);

                                    }


                                    //
//                                    TextView iv;
//                                    StickerView.LayoutParams params = null;
//
//                                    int yellow_iv_id = j; // Some arbitrary ID value.
//
//                                    iv = new TextView(TestMove.this);
//                                    iv.setId(yellow_iv_id);
////                                    iv.setBackgroundColor(Color.YELLOW);
//                                    params = new StickerView.LayoutParams(300, 400);
//                                    params.leftMargin = Integer.parseInt(x_axis);
//                                    params.topMargin = Integer.parseInt(y_axis);
//                                    iv.setText(heading);
//                                    iv.setTextSize(Float.parseFloat(font_size));


//                                    _view.setText(heading);


                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("result", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }


}
