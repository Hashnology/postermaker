package com.ashu.postermaker.universal;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by farhan on 4/17/2017.
 */

public class MyRequestQueue {

    private static Context context;
    private static MyRequestQueue request_instance;
    private RequestQueue requestQueue;

    private MyRequestQueue(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }

        return requestQueue;
    }

    public static synchronized MyRequestQueue getRequestInstance(Context context){
        if(request_instance == null){
            request_instance = new MyRequestQueue(context);
        }

        return request_instance;
    }

    public<T> void addRequest(Request<T> request){
        requestQueue.add(request);
    }
}
