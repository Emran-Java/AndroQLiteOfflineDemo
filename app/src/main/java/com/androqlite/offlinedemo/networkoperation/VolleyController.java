package com.androqlite.offlinedemo.networkoperation;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emran on 4/21/17.
 */

public class VolleyController {

    private Context mContext;
    private String mUrl;
    private int mMethod;

    private OnResponseListener onResponseListener;

    public VolleyController(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * @param method, Request.Method.POST or Request.Method.GET
     * */
    public void setServerIngo(String url, int method){
        mUrl = url;
        mMethod = method;
    }

    public void sendValueByVolley(final String[] keys, final String[] values) {
        StringRequest stringRequest = new StringRequest(mMethod/*Request.Method.POST*/, mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                onResponseListener.onResponse(response);
                Log.d("response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResponseListener.onErrorResponse(error.getMessage());
                Log.d("response", "VolleyError: " + error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramMap = new HashMap<>();

                if (keys != null && values != null) {
                    for (int i = 0; i < keys.length && i < values.length; i++) {
                        paramMap.put(keys[i], values[i]);
                    }
                }
                return paramMap;
            }
        };

        MySingleton.getInstance(mContext).addToRequestque(stringRequest);
    }

    public void getServerResponse(OnResponseListener onResponseListener){
        this.onResponseListener=onResponseListener;
    }

    public interface OnResponseListener{
        void onResponse(String response);
        void onErrorResponse(String response);
    }
}
