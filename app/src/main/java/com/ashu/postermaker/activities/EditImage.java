package com.ashu.postermaker.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ashu.postermaker.R;
import com.ashu.postermaker.adapters.UsersAdapter;
import com.ashu.postermaker.interfaces.LayersListner;
import com.ashu.postermaker.model_classes.MyLayers;
import com.ashu.postermaker.model_classes.Users;
import com.ashu.postermaker.universal.AppController;
import com.ashu.postermaker.universal.CONSTANTS;
import com.ashu.postermaker.universal.Utils;
import com.codekidlabs.storagechooser.Content;
import com.codekidlabs.storagechooser.StorageChooser;
import com.squareup.picasso.Picasso;
import com.thuytrinh.android.collageviews.MultiTouchListener;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.annotations.NonNull;
import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerPopup;
import top.defaults.colorpicker.ColorPickerView;
import top.defaults.view.TextButton;

public class EditImage extends AppCompatActivity implements LayersListner {
    private static final String SAVED_STATE_KEY_COLOR = "saved_state_key_color";
    private int INITIAL_COLOR = 0xFFFF8000;
    private Context context;
    SeekBar seekBar;
    private long lastTouchDown;
    private RelativeLayout linear_seekbar;
    private static int CLICK_ACTION_THRESHHOLD = 400;

    public static final int PICK_IMAGE = 1;
    private ProgressDialog progressDialogue;
    private ArrayList<MyLayers> myLayers;
    private TextView tv2;
    ImageView image, sticker_image_view, layer_side_open_btn, bg_image, iv_down, img;
    RelativeLayout rootView, drawer_rel, rel_main;
    private int _xDelta;
    private int _yDelta;
    FrameLayout collageBgView;
    private AppCompatImageView btnExport, btnVisible;
    LinearLayout rel_layer, navigation;
    private boolean isViewdown = false;
    RecyclerView layer_recycler;
    AppCompatImageView btnLock;
    private UsersAdapter usersAdapter;
    private ArrayList<Users> layersArrayList;
    private boolean isDrawerOpen = false;

    private TextView tv1;

    private static final String TAG = EditImage.class.getSimpleName();
    public static final int PERM_RQST_CODE = 110;
    private StickerView stickerView;
    private TextSticker sticker;
    private StorageChooser chooser;
    private StorageChooser.Builder builder = new StorageChooser.Builder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_image);
        context = this;

        myLayers = new ArrayList<>();
        init();

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSticker();


                Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);

// Start animation
                navigation.startAnimation(slide_up);

                navigation.setVisibility(View.VISIBLE);
//                saveImage();
            }
        });
        btnVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDrawerCode();
            }
        });


    }

    private void saveImage() {

        try {
            collageBgView.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(collageBgView.getDrawingCache());
            collageBgView.setDrawingCacheEnabled(false);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "FlyerMaker");

            if (!mediaStorageDir.exists()) {
                mediaStorageDir.mkdirs();
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("App", "failed to create directory");
                }
            }

            Date currentTime = Calendar.getInstance().getTime();
            File f = new File(mediaStorageDir
                    + File.separator + currentTime + " poster.jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();


            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    bitmap, "Design", null);

            Uri uri = Uri.parse(path);

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            share.putExtra(Intent.EXTRA_STREAM, uri);
            share.putExtra(Intent.EXTRA_TEXT, "I found something cool!");
            context.startActivity(Intent.createChooser(share, "Share Your Design!"));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void init() {


//        stickerView = (StickerView) findViewById(R.id.sticker_view);
        navigation = findViewById(R.id.navigation);
        btnExport = findViewById(R.id.btnExport);
        btnVisible = findViewById(R.id.btnVisible);
        collageBgView = findViewById(R.id.collageBgView);
        image = findViewById(R.id.temp_view);


//        bg_image = findViewById(R.id.bg_image);
        rootView = findViewById(R.id.rootView);
//        stickerView = findViewById(R.id.sticker_view);
        sticker_image_view = findViewById(R.id.main_bg);
        rel_layer = findViewById(R.id.rel_layer);
        drawer_rel = findViewById(R.id.drawer_rel);
        rel_main = findViewById(R.id.rel_main);
        layer_recycler = findViewById(R.id.layer_recycler);
        layer_side_open_btn = findViewById(R.id.layer_side_open_btn);
        linear_seekbar = findViewById(R.id.linear_seekbar);
        iv_down = findViewById(R.id.iv_down);
        seekBar = findViewById(R.id.opacitybar);
        rel_main.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return false;
            }
        });


        layersArrayList = new ArrayList<>();
        /*set mananger here like this*/
        GridLayoutManager gridLayoutManager = new GridLayoutManager(layer_recycler.getContext(), 1, layer_recycler.VERTICAL, false);
        layer_recycler.setLayoutManager(gridLayoutManager);


//        for (int i = 0; i <= 0; i++) {
//            Users users = new Users("text", "Hide Layers");
//            layersArrayList.add(users);
//            usersAdapter = new UsersAdapter(context, layersArrayList, EditImage.this);
//
//            layer_recycler.setAdapter(usersAdapter);
//            usersAdapter.notifyDataSetChanged();
//        }


        iv_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_down);
                linear_seekbar.startAnimation(slide_down);
                linear_seekbar.setVisibility(View.GONE);
            }
        });
        layer_side_open_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.shake);
                rel_main.startAnimation(anim);
//                onDrawerCode();

            }
        });


//        tv1.setOnTouchListener(new OnDragTouchListener(tv1, collageBgView));


        stickerView = new StickerView(context);

        FrameLayout.LayoutParams paramsmain = new FrameLayout.LayoutParams(Utils.pxToDp(context, 900), Utils.pxToDp(context, 1200));
        paramsmain.leftMargin = Utils.pxToDp(context, 0);
        paramsmain.topMargin = Utils.pxToDp(context, 0);
        paramsmain.gravity = Gravity.CENTER;
//                                bg_image.setScaleType(ImageView.ScaleType.FIT_XY);
        collageBgView.addView(stickerView, paramsmain);
        stickerView.showBorder = true;
        stickerView.showBorder = true;
        showSticker();





    }

    private void onDrawerCode() {
        if (isDrawerOpen == true) {
            Animation slide = AnimationUtils.loadAnimation(context, R.anim.slide_from_right);
//
            drawer_rel.startAnimation(slide);
            drawer_rel.setVisibility(View.GONE);
            isDrawerOpen = false;
        } else {
            Animation slide = AnimationUtils.loadAnimation(context, R.anim.slide_from_left);
            drawer_rel.startAnimation(slide);
            drawer_rel.setVisibility(View.VISIBLE);
            isDrawerOpen = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        collageBgView.invalidate();
    }
    //    private void lockUnlockSticker() {
//        if(sticker_view.isLocked()){
//            btnLock.setImageResource(R.drawable.ic_unlock);
//        }else{
//            btnLock.setImageResource(R.drawable.ic_lock);
//        }
////        sticker_view.isLocked() = !sticker_view.isLocked();
//    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onAddTextClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.alert_add_text);
        final Dialog dialog = builder.create();
        dialog.show();


        final int color = INITIAL_COLOR;
//        if (savedInstanceState != null) {
//            color = savedInstanceState.getInt(SAVED_STATE_KEY_COLOR, INITIAL_COLOR);
//        }
        final ColorPickerView colorPickerView = dialog.findViewById(R.id.colorPicker);

        colorPickerView.setInitialColor(color);

        final TextButton colorHex = dialog.findViewById(R.id.colorHex);
        TextButton resetColor = dialog.findViewById(R.id.resetColor);
        final Button btn_add_text = dialog.findViewById(R.id.btn_add_text);
        final EditText et_text = dialog.findViewById(R.id.et_text);
        final Button pickedColor = dialog.findViewById(R.id.pickedColor);
        final LinearLayout main_lay = dialog.findViewById(R.id.main_lay);

//        pickedColor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showColorPicker(colorPickerView);
//
//            }
//        });

        resetColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerView.reset();

            }
        });

        colorPickerView.subscribe(new ColorObserver() {
            @Override
            public void onColor(int color, boolean fromUser, boolean shouldPropagate) {
                pickedColor.setBackgroundColor(color);
                INITIAL_COLOR = color;
                btn_add_text.setBackgroundColor(color);
                main_lay.setBackgroundColor(color);
                colorHex.setText(colorHex(color));
                et_text.setTextColor(color);
                et_text.setHintTextColor(color);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(color);
                }

            }
        });


        pickedColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                stickerView = new StickerView(context);
//                if (stickerView == null) {
//                    FrameLayout.LayoutParams paramsmain = new FrameLayout.LayoutParams(Utils.pxToDp(context, 900), Utils.pxToDp(context, 1200));
////                                paramsmain.leftMargin = Utils.pxToDp(context, 0);
////                                paramsmain.topMargin = Utils.pxToDp(context, 0);
//                    paramsmain.gravity = Gravity.CENTER;
//
//
//                    collageBgView.addView(stickerView, paramsmain);
//
//                    stickerView.bringToFront();
//                }


                String str_text = et_text.getText().toString();
                if (!TextUtils.isEmpty(str_text)) {
                    testAdd(str_text, INITIAL_COLOR);
                    dialog.dismiss();
                } else {
                    et_text.setError("Enter text");
                    et_text.setFocusable(true);
                    Toast.makeText(EditImage.this, "Please enter text", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void showColorPicker(final ColorPickerView colorPickerView) {
        new ColorPickerPopup.Builder(this)
                .initialColor(colorPickerView.getColor())
                .enableAlpha(true)
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(true)
                .onlyUpdateOnTouchEventUp(true)
                .build()
                .show(new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        colorPickerView.setInitialColor(color);
                    }
                });
    }

    private String colorHex(int color) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return String.format(Locale.getDefault(), "0x%02X%02X%02X%02X", a, r, g, b);
    }

    private void showSticker() {

        //currently you can config your own icons and icon event
        //the event you can custom
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_close_white_18dp),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_scale_white_18dp),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                com.xiaopo.flying.sticker.R.drawable.sticker_ic_flip_white_18dp),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());

        BitmapStickerIcon heartIcon =
                new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.ic_bulb),
                        BitmapStickerIcon.LEFT_BOTTOM);
//        heartIcon.setIconEvent(new HelloIconEvent());

        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon, heartIcon));

        //default icon layout
        stickerView.configDefaultIcons();
        stickerView.setConstrained(true);
        stickerView.bringToFrontCurrentSticker=true;

//        stickerView.setBackgroundColor(Color.WHITE);
        stickerView.setLocked(false);


        sticker = new TextSticker(this);

        sticker.setDrawable(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.sticker_transparent_background));
        sticker.setText("Hello, world!");
        sticker.setTextColor(Color.BLACK);
        sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        sticker.resizeText();

        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerAdded");
//                sticker.setMatrix(freshInsetMatrix(desired_w, desired_h, desired_left, desired_top));
//                sticker.setMatrix(addStickerToSpecificPosition(sticker,3,3,3,3));

            }

//            @Override
//            public void onStickerAddedSpecific(@NonNull Sticker sticker,int desired_w,int desired_h,int desired_left,int desired_top) {
//                Log.d(TAG, "onStickerAdded");
////                sticker.setMatrix(freshInsetMatrix(desired_w, desired_h, desired_left, desired_top));
//                sticker.setMatrix(addStickerToSpecificPosition(sticker,3,3,3,3));
//
//            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                //stickerView.removeAllSticker();
                if (sticker instanceof TextSticker) {
                    ((TextSticker) sticker).setTextColor(Color.RED);
                    stickerView.replace(sticker);
                    stickerView.invalidate();


                }

                Log.d(TAG, "onStickerClicked");
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerDeleted");
            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerDragFinished");
            }

            @Override
            public void onStickerTouchedDown(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerTouchedDown");
            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerZoomFinished");
            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerFlipped");
            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                Log.d(TAG, "onDoubleTapped: double tap will be with two click");
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERM_RQST_CODE);
        } else {
//            loadSticker();
            getTemplatesById();

        }


    }


    private void loadSticker() {
//        stickerView.createBitmap().getPixel(10,20);
        Drawable drawable =
                ContextCompat.getDrawable(this, R.drawable.haizewang_215);
        Drawable drawable1 =
                ContextCompat.getDrawable(this, R.drawable.haizewang_23);
        stickerView.addSticker(new DrawableSticker(drawable));
        stickerView.addSticker(new DrawableSticker(drawable1), Sticker.Position.BOTTOM | Sticker.Position.RIGHT);

        Drawable bubble = ContextCompat.getDrawable(this, R.drawable.user);

        stickerView.addSticker(
                new TextSticker(getApplicationContext())
                        .setDrawable(bubble)
                        .setText("Sticker\n")
                        .setMaxTextSize(14)
                        .resizeText()
                , Sticker.Position.TOP);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERM_RQST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadSticker();
        }
    }

    public void testReplace(View view) {
        if (stickerView.replace(sticker)) {
            Toast.makeText(EditImage.this, "Replace Sticker successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EditImage.this, "Replace Sticker failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void testLock(View view) {
        stickerView.setLocked(!stickerView.isLocked());
    }

    public void testRemove(View view) {
        if (stickerView.removeCurrentSticker()) {
            Toast.makeText(EditImage.this, "Remove current Sticker successfully!", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(EditImage.this, "Remove current Sticker failed!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void testRemoveAll(View view) {
        stickerView.removeAllStickers();
    }

    public void reset(View view) {
        stickerView.removeAllStickers();
        loadSticker();
    }

    public void testAdd(String text, int color) {
        TextSticker sticker = new TextSticker(this);
        sticker.setText(text);
        sticker.setTextColor(color);
        sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        sticker.resizeText();
        stickerView.addSticker(sticker);

    }

    public void addTextSticker(String text, int color) {
        final TextSticker sticker = new TextSticker(this);
        sticker.setText(text);
        sticker.setTextColor(Color.BLACK);
        sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        sticker.resizeText();
        stickerView.addSticker(sticker);
        stickerView.bringToFront();

    }


    /**
     * Method to make json array request where response starts with [
     */
//    private void getTamplatesArray() {
//
//
//        JsonArrayRequest req = new JsonArrayRequest("http://poster.fadootutorial.com/api/templates",
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        String result = response.toString();
//                        Log.d("result", response.toString());
//                        try {
//                            JSONArray JA = new JSONArray(result);
//                            for (int i = 0; i < JA.length(); i++) {
//                                JSONObject firstObject = JA.getJSONObject(i);
//                                String id = firstObject.getString("id");
//
//                                String background_image = firstObject.getString("background_image");
//
//                                String category_id = firstObject.getString("category_id");
//                                String name = firstObject.getString("name");
//                                JSONArray JA_Headings = firstObject.getJSONArray("headings");
//                                for (int j = 0; j <= JA_Headings.length(); j++) {
//                                    JSONObject headingsObject = JA_Headings.getJSONObject(j);
//
//                                    String heading = headingsObject.getString("heading");
//                                    String x_axis = headingsObject.getString("x_axis");
//                                    String y_axis = headingsObject.getString("y_axis");
//                                    String font = headingsObject.getString("font");
//                                    String font_size = headingsObject.getString("font_size");
//                                    testAdd(heading);
//                                    /*testing code*/
//
////                                    FrameLayout rl = findViewById(R.id.frame);
////                                    TextView iv;
////                                    FrameLayout.LayoutParams params;
////
////                                    int yellow_iv_id = 123; // Some arbitrary ID value.
////
////                                    iv = new TextView(context);
////                                    iv.setId(yellow_iv_id);
////                                    iv.setBackgroundColor(Color.YELLOW);
////                                    params = new FrameLayout.LayoutParams(300, 400);
////                                    params.leftMargin = Integer.parseInt(x_axis);
////                                    params.topMargin = Integer.parseInt(y_axis);
////                                   iv.setText("awais is mkaing app");
////                                    rl.addView(iv, params);
////
//
//
////                                    FrameLayout rl = findViewById(R.id.frame);
//                                    TextView iv;
//                                    StickerView.LayoutParams params = null;
//
//                                    int yellow_iv_id = j; // Some arbitrary ID value.
//
//                                    iv = new TextView(context);
//                                    iv.setId(yellow_iv_id);
////                                    iv.setBackgroundColor(Color.YELLOW);
//                                    params = new StickerView.LayoutParams(300, 400);
//                                    params.leftMargin = Integer.parseInt(x_axis);
//                                    params.topMargin = Integer.parseInt(y_axis);
//                                    iv.setText(heading);
//                                    iv.setTextSize(Float.parseFloat(font_size));
//                                    sticker_view.addView(iv, params);
//
//
//                                }
//
//                                JSONArray JA_Images = firstObject.getJSONArray("images");
//                                for (int k = 0; k <= JA_Images.length(); k++) {
//                                    JSONObject obImages = JA_Images.getJSONObject(k);
//
//                                    String image = obImages.getString("image");
//                                    String x_axis = obImages.getString("x_axis");
//                                    String y_axis = obImages.getString("y_axis");
//                                    String width = obImages.getString("width");
//                                    String height = obImages.getString("height");
//
//                                    /*testing code*/
//
//                                    FrameLayout rl = findViewById(R.id.frame);
//                                    ImageView iv;
//                                    FrameLayout.LayoutParams params;
//
//                                    int yellow_iv_id = k; // Some arbitrary ID value.
//
//                                    iv = new ImageView(context);
//                                    iv.setId(yellow_iv_id);
////                                    iv.setBackgroundColor(Color.YELLOW);
//                                    params = new FrameLayout.LayoutParams(300, 400);
//                                    params.leftMargin = Integer.parseInt(x_axis);
//                                    params.topMargin = Integer.parseInt(y_axis);
//
//                                    Picasso.get().load(image).into(iv);
//                                    rl.addView(iv, params);
//
//
////
//                                }
//
//
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("result", "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(req);
//    }
    private void getTemplatesById() {
        progressDialogue = new ProgressDialog(context);
        progressDialogue.setMessage("Loading Template");
        progressDialogue.setCancelable(false);
        progressDialogue.setCanceledOnTouchOutside(false);
        progressDialogue.show();

        layersArrayList.clear();
        myLayers.clear();
        String tempId = Utils.getPreferences(CONSTANTS.KEY_TEMP_ID, context);


        JsonArrayRequest req = new JsonArrayRequest("http://poster.fadootutorial.com/api/templates/" + tempId,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String result = response.toString();
                        Log.d("result", response.toString());
                        progressDialogue.setMessage("Please wait");
                        try {
                            JSONArray JA = new JSONArray(result);
                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject firstObject = JA.getJSONObject(i);
                                String id = firstObject.getString("id");
                                String category_id = firstObject.getString("category_id");
                                String background_image = firstObject.getString("background_image");

                                bg_image = new ImageView(context);


                                FrameLayout.LayoutParams paramsmain = new FrameLayout.LayoutParams(Utils.pxToDp(context, 900), Utils.pxToDp(context, 1200));
                                paramsmain.leftMargin = Utils.pxToDp(context, 0);
                                paramsmain.topMargin = Utils.pxToDp(context, 0);
                                paramsmain.gravity = Gravity.CENTER;
//                                bg_image.setScaleType(ImageView.ScaleType.FIT_XY);
//                                collageBgView.addView(stickerView, paramsmain);


                                collageBgView.addView(bg_image, paramsmain);

//                                new ConvertImageURLMain().execute(background_image, "" + Utils.pxToDp(context, 900), "" + Utils.pxToDp(context, 1200), "0", "0");


                                Picasso.get().load(background_image).into(bg_image);

                                String name = firstObject.getString("name");


                                JSONArray JA_Images = firstObject.getJSONArray("images");
                                progressDialogue.setMessage("Setting Icon images");


                                for (int k = 0; k < JA_Images.length(); k++) {
                                    JSONObject obImages = JA_Images.getJSONObject(k);

                                    String image = obImages.getString("image");
                                    String x_axis = obImages.getString("x_axis");
                                    String y_axis = obImages.getString("y_axis");
                                    String width = obImages.getString("width");
                                    String height = obImages.getString("height");
                                    if (!image.equalsIgnoreCase("null")) {
                                        if (k == 0) {

                                            new ConvertImageURL().execute(image, width, height, "" + Utils.pxToDp(context, Integer.parseInt(x_axis)), "" + Utils.pxToDp(context, Integer.parseInt(y_axis)));

//                                            Users users = new Users("image", image);
//                                            layersArrayList.add(users);
//
//                                            MyLayers layers = new MyLayers("iv", null, img);
//                                            myLayers.add(layers);


                                        }
                                        if (k == 1) {

                                            new ConvertImageURL1().execute(image, width, height, "" + Utils.pxToDp(context, Integer.parseInt(x_axis)), "" + Utils.pxToDp(context, Integer.parseInt(y_axis)));


//                                            Users users = new Users("image", image);
//                                            layersArrayList.add(users);
////
//                                            MyLayers layers = new MyLayers("iv", null, img1);
//                                            myLayers.add(layers);

                                        }
                                        if (k == 2) {

                                            new ConvertImageURL2().execute(image, width, height, "" + Utils.pxToDp(context, Integer.parseInt(x_axis)), "" + Utils.pxToDp(context, Integer.parseInt(y_axis)));

//                                            Users users = new Users("image", image);
//                                            layersArrayList.add(users);
//                                            MyLayers layers = new MyLayers("iv", null, img2);
//                                            myLayers.add(layers);


                                        }
                                    }


                                }


                                progressDialogue.setMessage("Loading Headings");

                                JSONArray JA_Headings = firstObject.getJSONArray("headings");


                                for (int j = 0; j < JA_Headings.length(); j++) {
                                    JSONObject headingsObject = JA_Headings.getJSONObject(j);

                                    String heading = headingsObject.getString("heading");
                                    String x_axis = headingsObject.getString("x_axis");
                                    String y_axis = headingsObject.getString("y_axis");
                                    String font = headingsObject.getString("font");
                                    String font_size = headingsObject.getString("font_size");
                                    if (!heading.equalsIgnoreCase("null")) {

                                        if (j == 0) {
                                            addTextSticker(heading, 000000);


//                                            Users users = new Users("text", heading);
//                                            layersArrayList.add(users);
//
//                                            MyLayers layers = new MyLayers("tv", tv, null);
//                                            myLayers.add(layers);

                                        }
                                        if (j == 1) {

                                            TextView tv1 = new TextView(context);
                                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(w,h);
                                            params.leftMargin = Utils.pxToDp(context, Integer.parseInt(x_axis));
                                            params.topMargin = Utils.pxToDp(context, Integer.parseInt(y_axis));
                                            collageBgView.addView(tv1, params);
//                                            tv1.setOnTouchListener(new OnDragTouchListener(tv1, collageBgView));
                                            tv1.setTextSize(Float.parseFloat(font_size));
                                            tv1.setText(heading);
                                            tv1.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Toast.makeText(context, "TV2 is cliked", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            tv1.setOnTouchListener(new MultiTouchListener());

                                            MyLayers layers = new MyLayers("tv", tv1, null);
                                            myLayers.add(layers);
                                            Users users = new Users("text", heading);
                                            layersArrayList.add(users);
                                        }
                                        if (j == 2) {
                                            tv2 = new TextView(context);
                                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(w,h);
                                            params.leftMargin = Utils.pxToDp(context, Integer.parseInt(x_axis));
                                            params.topMargin = Utils.pxToDp(context, Integer.parseInt(y_axis));
                                            collageBgView.addView(tv2, params);
//                                            tv2.setOnTouchListener(new OnDragTouchListener(tv2, collageBgView));
                                            tv2.setTextSize(Float.parseFloat(font_size));
                                            tv2.setText(heading);
                                            tv2.setOnTouchListener(new MultiTouchListener());

                                            tv2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Toast.makeText(context, "TV3 is cliked", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            MyLayers layers = new MyLayers("tv", tv2, null);
                                            myLayers.add(layers);
                                            Users users = new Users("text", heading);
                                            layersArrayList.add(users);

                                        }
                                    }


                                }


                            }


                            usersAdapter = new UsersAdapter(context, layersArrayList, EditImage.this);
                            layer_recycler.setAdapter(usersAdapter);
                            usersAdapter.notifyDataSetChanged();
//
                            if (progressDialogue.isShowing()) {
                                progressDialogue.cancel();
                            }

                            Animation anim = AnimationUtils.loadAnimation(context, R.anim.shake);
                            rel_main.startAnimation(anim);
//                            showSticker();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (progressDialogue.isShowing()) {
                                progressDialogue.cancel();
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("result", "Error: " + error.getMessage());
                if (progressDialogue.isShowing()) {
                    progressDialogue.cancel();
                }
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        req.setShouldCache(false);
//        myQueue.add(request);
        AppController.getInstance().addToRequestQueue(req);
    }

    private void changeImageOpacity(final ImageView imagev) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //            int opacty = 100; // from 0 to 255
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                imagev.setAlpha(progresValue * 25);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });


    }


    @Override
    public void clickOnLayer(int pos) {
        String type = myLayers.get(pos).getType();

        if (type.equalsIgnoreCase("tv")) {
            TextView textView = myLayers.get(pos).getTv_heading();
            if (textView.getVisibility() == View.VISIBLE) {
                textView.setVisibility(View.GONE);

            } else {
                textView.setVisibility(View.VISIBLE);

            }

        } else {
            ImageView imageView = myLayers.get(pos).getIv_icon();

            if (imageView.getVisibility() == View.VISIBLE) {
                imageView.setVisibility(View.GONE);

            } else {
                imageView.setVisibility(View.VISIBLE);

            }
        }

    }

    public void onAddImageClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
                Uri image_path = data.getData();


                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), image_path);
                    Toast.makeText(context, "Picture Is Attached!", Toast.LENGTH_SHORT).show();
                    bg_image.setImageBitmap(bitmap);
//                    Drawable d = new BitmapDrawable(getResources(), bitmap);

//                    stickerView.addSticker(new DrawableSticker(d));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
                Uri image_path = data.getData();


                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), image_path);
                    Toast.makeText(context, "Picture Is Attached!", Toast.LENGTH_SHORT).show();
//                    bg_image.setImageBitmap(bitmap);
                    Drawable d = new BitmapDrawable(getResources(), bitmap);

                    stickerView.addSticker(new DrawableSticker(d));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void addStickerImage(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
    }


    public void openFolder() {
        try {

            // ----------------- Localization -------------------
            Content c = new Content();
            c.setCreateLabel("Create");
            c.setInternalStorageText("My Storage");
            c.setCancelLabel("Cancel");
            c.setSelectLabel("Select");
            c.setOverviewHeading("Choose Drive");

            builder.withActivity(this)
                    .withFragmentManager(getFragmentManager())
                    .setMemoryBarHeight(1.5f)
//                .disableMultiSelect()
                    .withContent(c);


            chooser = builder.build();
            chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
                @Override
                public void onSelect(String path) {
//                    Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();

                    File file = new File(Environment.getExternalStorageDirectory(),
                            "FlyerMaker");

                    Log.d("path", file.toString());
                    String path2 = file.toString();
                    builder.withPredefinedPath(path2);
                    builder.setType(StorageChooser.DIRECTORY_CHOOSER);
                    builder.allowCustomPath(true);
                    builder.filter(StorageChooser.FileType.IMAGES);


                }
            });

            chooser.setOnCancelListener(new StorageChooser.OnCancelListener() {
                @Override
                public void onCancel() {
                    Toast.makeText(getApplicationContext(), "Storage Chooser Cancelled.", Toast.LENGTH_SHORT).show();
                }
            });

            chooser.setOnMultipleSelectListener(new StorageChooser.OnMultipleSelectListener() {
                @Override
                public void onDone(ArrayList<String> selectedFilePaths) {
                    for (String s : selectedFilePaths) {
                        Log.e(TAG, s);
                    }
                }
            });

            chooser.show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void getMyDesigns(View view) {

        File file = new File(Environment.getExternalStorageDirectory(),
                "FlyerMaker");

        Log.d("path", file.toString());


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri mydir = Uri.parse(file.toString());
        intent.setDataAndType(mydir, "*/*");
        startActivity(intent);
//        openFolder();
    }

    public void onTemplateChoosing(View view) {
        startActivity(new Intent(context, SliderMainActivity.class));
    }

    private Matrix freshInsetMatrix(StickerView inset_sticker) {
        float sticker_w = inset_sticker.getWidth();
        float sticker_h = inset_sticker.getHeight();

        float scale_x = 300 / sticker_w;
        float scale_y = 150 / sticker_h;

        Matrix matrix = new Matrix();
        matrix.postTranslate(200 / scale_x, 500 / scale_y);
        matrix.postScale(scale_x, scale_y);
        return matrix;
    }


    private Matrix addStickerToSpecificPosition(Sticker inset_sticker, int desired_w, int desired_h, int desired_x, int desired_y) {
        float sticker_w = inset_sticker.getWidth();
        float sticker_h = inset_sticker.getHeight();

        float scale_x = desired_w / sticker_w;
        float scale_y = desired_h / sticker_h;

        Matrix matrix = new Matrix();
        matrix.postTranslate(desired_x / scale_x, desired_y / scale_y);
        matrix.postScale(scale_x, scale_y);
        return matrix;
    }


    private class ConvertImageURL extends AsyncTask<String, Void, Void> {
        Bitmap image = null;
        String imageUrl = "";
        int w = 0;
        int h = 0;
        int x = 0;
        int y = 0;
        String width, height, x_axis, y_axis;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                imageUrl = params[0];
                width = params[1];
                height = params[2];
                x_axis = params[3];
                y_axis = params[4];
                URL url = new URL(imageUrl);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);


            } catch (Exception e) {
                System.out.println(e);

                Log.d("hantash_linez", "Error Message" + e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            w = Integer.parseInt(width);
            h = Integer.parseInt(height);
            x = Integer.parseInt(x_axis);
            y = Integer.parseInt(y_axis);


            Drawable d = new BitmapDrawable(getResources(), image);
            stickerView.addSticker(new DrawableSticker(d));
//            stickerView.getCurrentSticker().setMatrix(addStickerToSpecificPosition(stickerView.getCurrentSticker(), 800, 800, 10, 100));
            stickerView.getCurrentSticker().setMatrix(addStickerToSpecificPosition(stickerView.getCurrentSticker(), w, h, Utils.pxToDp(context, Integer.parseInt(x_axis)), Utils.pxToDp(context, Integer.parseInt(y_axis))));


        }

    }

    private class ConvertImageURL1 extends AsyncTask<String, Void, Void> {
        Bitmap image = null;
        String imageUrl = "";
        int w = 0;
        int h = 0;
        int x = 0;
        int y = 0;
        String width, height, x_axis, y_axis;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                imageUrl = params[0];
                width = params[1];
                height = params[2];
                x_axis = params[3];
                y_axis = params[4];
                URL url = new URL(imageUrl);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);


            } catch (Exception e) {
                System.out.println(e);

                Log.d("hantash_linez", "Error Message" + e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            w = Integer.parseInt(width);
            h = Integer.parseInt(height);
            x = Integer.parseInt(x_axis);
            y = Integer.parseInt(y_axis);


            Drawable d = new BitmapDrawable(getResources(), image);
            stickerView.addSticker(new DrawableSticker(d));

//            stickerView.getCurrentSticker().setMatrix(addStickerToSpecificPosition(stickerView.getCurrentSticker(), 800, 800, 10, 100));
            stickerView.getCurrentSticker().setMatrix(addStickerToSpecificPosition(stickerView.getCurrentSticker(), w, h, Utils.pxToDp(context, Integer.parseInt(x_axis)), Utils.pxToDp(context, Integer.parseInt(y_axis))));


        }

    }

    private class ConvertImageURL2 extends AsyncTask<String, Void, Void> {
        Bitmap image = null;
        String imageUrl = "";
        int w = 0;
        int h = 0;
        int x = 0;
        int y = 0;
        String width, height, x_axis, y_axis;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                imageUrl = params[0];
                width = params[1];
                height = params[2];
                x_axis = params[3];
                y_axis = params[4];
                URL url = new URL(imageUrl);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);


            } catch (Exception e) {
                System.out.println(e);

                Log.d("hantash_linez", "Error Message" + e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            w = Integer.parseInt(width);
            h = Integer.parseInt(height);
            x = Integer.parseInt(x_axis);
            y = Integer.parseInt(y_axis);


            Drawable d = new BitmapDrawable(getResources(), image);
            stickerView.addSticker(new DrawableSticker(d));
//            stickerView.getCurrentSticker().setMatrix(addStickerToSpecificPosition(stickerView.getCurrentSticker(), 800, 800, 10, 100));
            stickerView.getCurrentSticker().setMatrix(addStickerToSpecificPosition(stickerView.getCurrentSticker(), w, h, Utils.pxToDp(context, Integer.parseInt(x_axis)), Utils.pxToDp(context, Integer.parseInt(y_axis))));


        }

    }

    private class ConvertImageURLMain extends AsyncTask<String, Void, Void> {
        Bitmap image = null;
        String imageUrl = "";
        int w = 0;
        int h = 0;
        int x = 0;
        int y = 0;
        String width, height, x_axis, y_axis;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                imageUrl = params[0];
                width = params[1];
                height = params[2];
                x_axis = params[3];
                y_axis = params[4];
                URL url = new URL(imageUrl);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);


            } catch (Exception e) {
                System.out.println(e);

                Log.d("hantash_linez", "Error Message" + e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            w = Integer.parseInt(width);
            h = Integer.parseInt(height);
            x = Integer.parseInt(x_axis);
            y = Integer.parseInt(y_axis);


            Drawable d = new BitmapDrawable(getResources(), image);
            stickerView.addSticker(new DrawableSticker(d));
//            stickerView.getCurrentSticker().setMatrix(addStickerToSpecificPosition(stickerView.getCurrentSticker(), 800, 800, 10, 100));
            stickerView.getCurrentSticker().setMatrix(addStickerToSpecificPosition(stickerView.getCurrentSticker(), w, h, Utils.pxToDp(context, Integer.parseInt(x_axis)), Utils.pxToDp(context, Integer.parseInt(y_axis))));

            addTextSticker("mama ma athhay aaaaaaan", 000000);

        }

    }

}
