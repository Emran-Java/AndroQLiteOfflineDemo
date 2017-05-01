package com.androqlite.offlinedemo.networkoperation;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by emran on 4/21/17.
 */

public class MySingleton {

    private static MySingleton mMySingleton;
    private static Context mContext;
    private RequestQueue requestQueue;

    private MySingleton(Context context) {
        mContext = context;
    }

    private RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context){
        if(mMySingleton ==null){
            mMySingleton = new MySingleton(context);
        }
        return mMySingleton;
    }

    public<T> void addToRequestque(Request<T> tRequest){
        getRequestQueue().add(tRequest);
    }
}
