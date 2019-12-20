//package com.ashu.postermaker.adapters;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.lineztech.muhammadawais.vanmove.R;
//import com.lineztech.muhammadawais.vanmove.database.AppDatabase;
//import com.lineztech.muhammadawais.vanmove.model.GetVehicles;
//import com.lineztech.muhammadawais.vanmove.models.DBBitmapUtility;
//import com.lineztech.muhammadawais.vanmove.utils.Utils;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.List;
//
//
///**
// * Created by Ravi Tamada on 12-03-2015.
// */
//public class VanBookingAdapter extends RecyclerView.Adapter<VanBookingAdapter.MyViewHolder> {
//    List<GetVehicles> data;
//    RecyclerView.Adapter<MyViewHolder> recyclerView;
//    String obj_id_appoint, patient_id, str_patient_name;
//    int position;
//    RelativeLayout container_cabbii_name;
//    GetVehicles current;
//    MyViewHolder holder;
// private  static   int globalPosition=0;
//
//
//
//    AppDatabase app_database;
//    private LayoutInflater inflater;
//    private Context context;
//    private com.android.volley.toolbox.ImageLoader imageLoader;
//    private ListAdapterListener mListener;
//
//    public VanBookingAdapter(Context context, List<GetVehicles> data, ListAdapterListener mListener) {
//        this.context = context;
//        inflater = LayoutInflater.from(context);
//        this.data = data;
//        this.mListener = mListener;
//
//    }
//
//    public void delete(int position) {
//        data.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.single_view_taxi_new, parent, false);
////        view.setOnClickListener(new MyOnClickListener());
//         holder = new MyViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        current = Utils.GetVehicles.get(position);
//        int myint = position;
//
//
//        String vehicle_name = current.getVehicle_name();
////        selector_view.setBackgroundColor(Color.parseColor("#ffffff"));
//
//        if(position==globalPosition)
//        {
//            //change color like
//            Log.d("globalPosition",""+globalPosition+"position"+position);
////            holder.tv_cabbii_type.setTextColor(Color.RED);
//            holder.selector_view.setBackgroundColor(Color.parseColor("#1F9EED"));
//            Utils.savePreferences("vehicle_name", current.getVehicle_name(), context);
//            GetVehicles ob = Utils.GetVehicles.get(position);
//            mListener.onClickAtOKButton(ob.getVehicle_id());
////            Utils.showToastTest(context, ob.getVehicle_name()+"Time :"+ob.getDriver_time());
//
//
//
//            Utils.savePreferences("rate_per_mile", current.getRate_per_mile_fare(), context);
//            Utils.savePreferences("rate_per_minute", current.getRate_per_minute_fare(), context);
//            Utils.savePreferences("rate_per_hour", current.getHourly_rate(), context);
//
//        }
//        else
//        {
//            Log.d("globalPosition",""+globalPosition+"position"+position);
//            //revert back to regular color
//            holder.selector_view.setBackgroundColor(Color.parseColor("#ffffff"));
////            holder.tv_cabbii_type.setTextColor(Color.BLACK);
//        }
//
//try {
//
//
//        Integer driver_time = current.getDriver_time();
//Log.d("driver_time","driver_time "+driver_time);
//        if (driver_time==null) {
////            holder.tv_distance_min.setText(String.format("%f", ""+driver_time));
//            holder.tv_distance_min.setText("N/A");
//        }  else if (driver_time>0) {
////            holder.tv_distance_min.setText(String.format("%f", ""+driver_time));
//            holder.tv_distance_min.setText("" + driver_time+"MIN");
//        } else if (driver_time==0) {
////            holder.tv_distance_min.setText(String.format("%f", ""+driver_time));
//            holder.tv_distance_min.setText("" +( driver_time + 1) +" MIN");
//        }else {
//            holder.tv_distance_min.setText("N/A");
//        }
//}catch (Exception e){
//    e.printStackTrace();
//}
//        holder.tv_cabbii_type.setText(vehicle_name);
//        String helpers = Utils.getPreferences("helpers", context);
//        if (helpers.equalsIgnoreCase("0")) {
//            holder.iv_helper.setBackgroundResource(R.color.white_color);
//
//        } else if (helpers.equalsIgnoreCase("1")) {
//            holder.iv_helper.setBackgroundResource(R.drawable.helper_one);
//
//
//        } else if (helpers.equalsIgnoreCase("2")) {
//            holder.iv_helper.setBackgroundResource(R.drawable.helper_two);
//
//        } else if (helpers.equalsIgnoreCase("3")) {
//            holder.iv_helper.setBackgroundResource(R.drawable.helper_three);
//
//        }
//        if (!TextUtils.isEmpty(current.getPicture())) {
//          /*  String url = Utils.imageUrl + current.getPicture();
//            imageLoader = AppController.getInstance().getImageLoader();
//            holder.iv_vehicles.setImageUrl(url, imageLoader);*/
//
//
////            int check_pic = app_database.checkStudentPic();
//            //Toast.makeText(this, ""+check_pic, Toast.LENGTH_LONG).show();
//
//            /*==========================this code saves images in db according to their names===========*/
//        /*   app_database = new AppDatabase(context);
//            app_database.open();
//
//            String alreadyVehicles = app_database.checkAlreadyVehicles(current.getPicture());
//            if (alreadyVehicles.equalsIgnoreCase(current.getPicture())) {
//                try {
//                    holder.iv_vehicles.setImageBitmap(DBBitmapUtility.getImage(app_database.getStudentImage(alreadyVehicles)));
//                    app_database.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            } else {
//                app_database.close();
//                new ConvertImageURL().execute(current.getPicture());
//
//            }*/
//
//
//      /*==========================this code saves images in db according to their names===========*/
//            String vehicle_id = current.getVehicle_id();
//            if(vehicle_id.equalsIgnoreCase("2")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.minivan);
//            }else    if(vehicle_id.equalsIgnoreCase("3")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.swb);
//            }else    if(vehicle_id.equalsIgnoreCase("4")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.mwb);
//            }else    if(vehicle_id.equalsIgnoreCase("5")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.lwb);
//            }else    if(vehicle_id.equalsIgnoreCase("6")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.luton);
//            }else    if(vehicle_id.equalsIgnoreCase("7")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.seventon);
//            }else    if(vehicle_id.equalsIgnoreCase("9")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.dropside);
//            }else    if(vehicle_id.equalsIgnoreCase("10")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.minibus);
//            }else    if(vehicle_id.equalsIgnoreCase("11")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.recovery);
//            }else    if(vehicle_id.equalsIgnoreCase("13")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.vehicle_spaceship);
//            }else    if(vehicle_id.equalsIgnoreCase("15")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.xlwb);
//            }else    if(vehicle_id.equalsIgnoreCase("17")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.wastetruck);
//            }else    if(vehicle_id.equalsIgnoreCase("18")){
//                holder.iv_vehicles.setBackgroundResource(R.drawable.tipper);
//            }
//
//
//        }
////        holder.tv_distance_min.setText(current.getDistance_driver() + "min");
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//    private void showInfoAlert() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setView(R.layout.info_dialogue);
//        final AlertDialog alert_dialog = builder.create();
//        TextView tv_name, tv_rate_per_mile, tv_mint_rate, tv_hourly_rate, tv_helper_rate;
//
//
//        alert_dialog.setCancelable(true);
//        alert_dialog.setCanceledOnTouchOutside(true);
//        alert_dialog.show();
//        tv_name = (TextView) alert_dialog.findViewById(R.id.tv_name);
//        tv_rate_per_mile = (TextView) alert_dialog.findViewById(R.id.tv_rate_per_mile);
//        tv_mint_rate = (TextView) alert_dialog.findViewById(R.id.tv_mint_rate);
//        tv_hourly_rate = (TextView) alert_dialog.findViewById(R.id.tv_hourly_rate);
//        tv_helper_rate = (TextView) alert_dialog.findViewById(R.id.tv_helper_rate);
////        tv_name.setText(data.get(getPosition()).getVehicle_name();
//        tv_rate_per_mile.setText("Rate Per Mile :" + current.getRate_per_mile_fare());
//        tv_mint_rate.setText("Rate Per Minute :" + current.getRate_per_minute_fare());
//        tv_hourly_rate.setText("Rate Per Hour :" + current.getHourly_rate());
//        tv_helper_rate.setText("Rate Per Helper :" + current.getRate_helper_hourly());
//
//
//    }
//
//    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        float bitmapRatio = (float) width / (float) height;
//        if (bitmapRatio > 1) {
//            width = maxSize;
//            height = (int) (width / bitmapRatio);
//        } else {
//            height = maxSize;
//            width = (int) (height * bitmapRatio);
//        }
//        return Bitmap.createScaledBitmap(image, width, height, true);
//    }
//
//    public interface ListAdapterListener { // create an interface
//        void onClickAtOKButton(String position); // create callback function
//    }
//
//    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private TextView tv_cabbii_type;
//        private TextView tv_distance_min;
//        private ImageView iv_cabbii_info, iv_helper;
//        private   View selector_view;
//        private  ImageView iv_vehicles;
//
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
////            itemView.setOnTouchListener(itemTouchListener);
//            container_cabbii_name = (RelativeLayout) itemView.findViewById(R.id.container_cabbii_name);
//            tv_cabbii_type = (TextView) itemView.findViewById(R.id.tv_vehicle_name);
//            tv_distance_min = (TextView) itemView.findViewById(R.id.tv_estimated);
//            iv_cabbii_info = (ImageView) itemView.findViewById(R.id.iv_vehicle_info);
//            iv_helper = (ImageView) itemView.findViewById(R.id.iv_helper);
//            iv_vehicles = (ImageView) itemView.findViewById(R.id.iv_vehicles);
//            selector_view = (View) itemView.findViewById(R.id.selector_view);
//
//
//     /*       iv_vehicles.setOnClickListener(this);
//            iv_cabbii_info.setOnClickListener(this);*/
////            itemView.setOnClickListener(this);
//            container_cabbii_name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//
//            iv_cabbii_info.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    String str_vehicle_name = data.get(getPosition()).getVehicle_name();
//                    Utils.showToastTest(context, str_vehicle_name);
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setView(R.layout.info_dialogue);
//                    final AlertDialog alert_dialog = builder.create();
//                    TextView tv_name, tv_rate_per_mile, tv_mint_rate, tv_hourly_rate, tv_helper_rate;
//
//
//                    alert_dialog.setCancelable(true);
//                    alert_dialog.setCanceledOnTouchOutside(true);
//                    alert_dialog.show();
//                    tv_name = (TextView) alert_dialog.findViewById(R.id.tv_name);
//                    tv_rate_per_mile = (TextView) alert_dialog.findViewById(R.id.tv_rate_per_mile);
//                    tv_mint_rate = (TextView) alert_dialog.findViewById(R.id.tv_mint_rate);
//                    tv_hourly_rate = (TextView) alert_dialog.findViewById(R.id.tv_hourly_rate);
//                    tv_helper_rate = (TextView) alert_dialog.findViewById(R.id.tv_helper_rate);
//                    tv_name.setText(data.get(getPosition()).getVehicle_name());
//                    tv_rate_per_mile.setText("Rate Per Mile :" + current.getRate_per_mile_fare());
//                    tv_mint_rate.setText("Rate Per Minute :" + current.getRate_per_minute_fare());
//                    tv_hourly_rate.setText("Rate Per Hour :" + current.getHourly_rate());
//                    tv_helper_rate.setText("Rate Per Helper :" + current.getRate_helper_hourly());
//
//
////                    showInfoAlert();
//                }
//            });
//
//            iv_vehicles.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//
//                /*    GetVehicles ob = Utils.GetVehicles.get(getAdapterPosition());
//                    mListener.onClickAtOKButton(ob.getVehicle_id());
//                    Utils.savePreferences("vehicle_name", ob.getVehicle_name(), context);
//
//                    Utils.savePreferences("rate_per_mile", ob.getRate_per_mile_fare(), context);
//                    Utils.savePreferences("rate_per_minute", ob.getRate_per_minute_fare(), context);
//                    Utils.savePreferences("rate_per_hour", ob.getHourly_rate(), context);*/
//
//                    globalPosition=getAdapterPosition();
//                    notifyDataSetChanged();
//
////                    Toast.makeText(context, "globalPosition"+globalPosition, Toast.LENGTH_SHORT).show();
//
//
//
//
//                }
//            });
//
//
//        }
//
//
//        @Override
//        public void onClick(View v) {
////            Toast.makeText(context, "onClick " + getPosition(), Toast.LENGTH_SHORT).show();
////            Intent mem=new Intent(context, Doctor_Detail.class);
////            mem.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//
////            mem.putExtra("Objectid", data.get(getPosition()).getDoctorObjectId());
////            mem.putExtra("doc_name",data.get(getPosition()).getDoctorName());
////            mem.putExtra("doc_address",data.get(getPosition()).getDoctorAddress());
////            mem.putExtra("doc_experience",data.get(getPosition()).getDoctorExperience());
////            mem.putExtra("doc_ratting",data.get(getPosition()).getDoctorRatting());
////            mem.putExtra("people_rate_no",data.get(getPosition()).getDoctorNOpeolpleRate());
////            mem.putExtra("doc_fee",data.get(getPosition()).getDoctorFee());
////            mem.putExtra("doc_image",data.get(getPosition()).getDoctorImage());
////            mem.putExtra("doc_featured",data.get(getPosition()).getDoctorFeaturedImage());
////            mem.putExtra("doc_clinic",data.get(getPosition()).getDoctorClinic());
////            mem.putExtra("doc_designatio",data.get(getPosition()).getDoctorDesignation());
////            mem.putExtra("doc_specialty",data.get(getPosition()).getDoctorSpecialty());
////            mem.putExtra("doc_education",data.get(getPosition()).getDoctorEducation());
////            mem.putExtra("doc_about",data.get(getPosition()).getDoctorAbout());
////            mem.putExtra("doc_latitude",data.get(getPosition()).getLatitude());
////            mem.putExtra("doc_longitude",data.get(getPosition()).getLongitude());
//
//
////            context.startActivity(mem);
//
//        }
//    }
//
//    private class ConvertImageURL extends AsyncTask<String, Void, Void> {
//        Bitmap image=null;
//        String vehicleUrl_Name="";
//
//        @Override
//        protected void onPreExecute() {
//
//        }
//
//        @Override
//        protected Void doInBackground(String... params) {
//            vehicleUrl_Name = params[0];
//            String image_url = Utils.imageUrl + params[0];
//            Log.d("hantash_linez", "image url" + image_url);
//            try {
//                URL url = new URL(image_url);
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 4;
//                image = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
//
//            } catch (Exception e) {
//                System.out.println(e);
//
//                Log.d("hantash_linez", "Error Message" + e);
//            }
//        /*    try {
//                URL url = new URL(image_url);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//                InputStream input = connection.getInputStream();
//                 image  = BitmapFactory.decodeStream(input);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }*/
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//
//            //iv_nav_passenger.setImageBitmap(image);
////            MyViewHolder myViewHolder=new MyViewHolder();
//            holder.iv_vehicles.setImageBitmap(image);
//            AppDatabase app_database = new AppDatabase(context);
//            app_database.open();
//            try {
//                app_database.addStudentImage(DBBitmapUtility.getBytes(image), vehicleUrl_Name);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
////            int check_pic = app_database.checkStudentPic();
//            //Toast.makeText(MainActivityDrawer.this, "Adding Pic To DB: " + check_pic, Toast.LENGTH_LONG).show();
//
//            app_database.close();
//
//        }
//
//    }
//
//}
////https://stackoverflow.com/questions/15444375/how-to-create-interface-between-fragment-and-adapter