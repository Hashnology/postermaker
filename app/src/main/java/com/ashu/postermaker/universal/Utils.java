package com.ashu.postermaker.universal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.smarteist.autoimageslider.SliderView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

    //    public static String server = "http://www.jivansath.com/api/verification/";
    public static String server = "http://poster.fadootutorial.com/api/";

    public static String loginUser = server + "login_api.php";
    public static String RegisterUser = server + "register_user.php";
    public static String LoadSlider = server + "home/screen/slider";
    public static String CATEGORIES_URL = server + "categories";
    public static String SUB_CATEGORIES_URL = server + "categories/post/";
    public static String GET_FINAL_TEMPLATES = server + "templates/final";
    public static String ADD_NEW_CATEGORY = server + "add_new_category.php";
    public static String ADD_SUB_CATEGORY = server + "add_sub_category.php";
    public static String GET_ALL_SUB_CATEGORY = server + "get_all_sub_categories.php";
    public static String ADD_PRODUCTS = server + "add_new_items_api.php";
    public static String GET_ALL_PRODUCTS = server + "get_all_products.php";
    public static String DELETE_PRODUCT_API = server + "delete_product.php";
    public static String UPDATE_PRODUCT_API = server + "update_product.php";
    public static String GET_ALL_EXPENSES = server + "get_all_expenses.php";
    public static String GET_ALL_USERS = server + "get_all_users.php";
    public static String APPROVE_USERS = server + "approve_user.php";
    public static String DEACTIVATE_USERS = server + "deactivate_user.php";
    public static String SEND_SMS = server + "send_sms.php";
    public static String GET_ADMIN = server + "get_admin.php";
    public static String SALE_PRODUCT = server + "sale_product.php";
    public static String USER_SALES = server + "get_user_sales.php";
    public static String DELETE_SOLD_PRODUCT_API = server + "delete_sale.php";


    public static String ImageUrl = "http://karimapps.com/spreadbusiness/images/uploads/";


    public static String loadTask = server + "LoadTasks?formdata=";
    public static String updateLatLong = server + "UpdateLatLong?formdata=";
    public static String updateTaskStatus = server + "UpdateTaskStatus?formdata=";
    public static String SendSOSAPi = server + "UploadAlerts?formdata=";


    public static int DISTANCE_TO_ROTATE = 20;

//    public static ArrayList<MinTime> GetMinTime = new ArrayList<MinTime>();
//    public static ArrayList<GetVehicles> GetVehicles = new ArrayList<GetVehicles>();
//    public static ArrayList<LoadCountries> LoadCountriesList = new ArrayList<LoadCountries>();
//    public static ArrayList<GetDrivers> DriversArroundList = new ArrayList<GetDrivers>();
//    public static ArrayList<UpcomingBookings> UpcomingBookingsList = new ArrayList<UpcomingBookings>();
//    public static ArrayList<InventoryImages> getInventoryImagesList = new ArrayList<InventoryImages>();
//    public static ArrayList<AllOffers> All_Offers_List = new ArrayList<AllOffers>();

    public static String getPreferences(String key, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userName = sharedPreferences.getString(key, "");
        return userName;
    }

    public static int getPreferencesInt(String key, Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int int_value = sharedPreferences.getInt(key, 0);
        return int_value;
    }

    public static boolean savePreferences(String key, String value,
                                          Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }

    public static boolean savePreferencesInt(String key, int value,
                                             Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
        return true;
    }


    public static byte[] getImageBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 30, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImageBitmapFromByteArray(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(Resources.getSystem(), x);
    }

//	private void postJSonbyCreatingJsonObject() {
//		try {
//			RequestQueue requestQueue = Volley.newRequestQueue();
//
//			String URL = CONSTANTS.baseUrl + "register_driver2.php";
//			JSONObject postParam = new JSONObject();
//
//			postParam.put("device", "android");
//			postParam.put("vehicle_license_number", "111111");
//			postParam.put("vehicle_registration_number", "433");
//			postParam.put("vehicle_maker_model", "4343");
//			postParam.put("vehicle_year", "443");
//			postParam.put("vehicle_type", "dfdsfsd");
//			postParam.put("vehicle_air_conditioner", "sdfsdf");
//			postParam.put("voice_booking", "dfdsfs");
//			postParam.put("payment_card", "dfdsf");
//
//			final String requestBody = postParam.toString();
//
//
//
//
//
//			StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//				@Override
//				public void onResponse(String response) {
//					Log.i("VOLLEY", response);
//					Toast.makeText(context, "message", Toast.LENGTH_SHORT).show();
//				}
//			}, new Response.ErrorListener() {
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					Log.e("VOLLEY", error.toString());
//					Toast.makeText(context, "message", Toast.LENGTH_SHORT).show();
//				}
//			}) {
//
//
//				@Override
//				public Map<String, String> getHeaders ()throws AuthFailureError {
//					HashMap<String, String> headers = new HashMap<String, String>();
//					headers.put("Content-Type", "application/json");
//					headers.put("registration_id", "cBTXx4-Q3n0:APA91bHs81tH5Lz6IdmI8_vE7c43nRObz20lTtWaWL0mAtOf9qnIUvafjd2qMQvpvCPxSQifhv1AK-EXD_a9S2ypaz5XkQbka2YjkQRcDY21kXvPGFpz6M1scGnD4d2_gYiXw3vXATmq");
//					headers.put("driver_id", "1");
//					return headers;
//				}
//
//				@Override
//				public byte[] getBody() throws AuthFailureError {
//					try {
//						return requestBody == null ? null : requestBody.getBytes("utf-8");
//					} catch (UnsupportedEncodingException uee) {
//						VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//						return null;
//					}
//				}};
//
//
//
//
//
//			requestQueue.add(stringRequest);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}

    public static boolean WifiEnable(Context context) {
        boolean mWifiEnable = false; // Assume disabled
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            mWifiEnable = true;
            return true;
        }
        return false;
    }

    public static boolean isMobileDataEnable(Context context) {
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean) method.invoke(cm);
        } catch (Exception e) {
            // Some problem accessible private API and do whatever error
            // handling you want here
        }
        return mobileDataEnabled;
    }

    public static void showToast(Context context, String txt) {
        Toast.makeText(context, "" + txt, Toast.LENGTH_SHORT).show();

    }

    public static void showToastTest(Context context, String txt) {

        Toast.makeText(context, "" + txt, Toast.LENGTH_SHORT).show();

    }

    public static void showCustomDialog(Context context, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int getMyViewHeight(LinearLayout sliderView) {
//        w = sliderView.getWidth();
        int h=0;
        return h = sliderView.getHeight();
//        Log.v("W-H", w+"-"+h);
    }
}
