package com.androqlite.offlinedemo.utility;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by emran on 5/1/17.
 */

public class DataParser {

    public ArrayList<String[]> getCoverData(String jsonData, String apiArrayIndexKey, String[] responseKeyArray) {
        ArrayList<String[]> arrayList = new ArrayList<>();
        Log.d("debug", "jsonData: " + jsonData);
        Log.d("debug", "apiArrayIndexKey: " + apiArrayIndexKey);
        if (responseKeyArray != null)
            Log.d("debug", "responseKeyArray: " + responseKeyArray.toString());
        try {

            if (jsonData != null && !jsonData.isEmpty()) {

                JSONArray jsonArray = new JSONObject(jsonData).getJSONArray(apiArrayIndexKey);

                //Log.d("test", "dataSize: "+jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String[] jsonValue = new String[responseKeyArray.length];
                    //Log.d("test","paramKeySetSiz "+responseKeyArray.length+", ");

                    for (int j = 0; j < responseKeyArray.length; j++) {
                        if (jsonObject.has(responseKeyArray[j])) {
                            //Log.d("test","paramKeySet["+j+"]: "+responseKeyArray[j]+", ");
                            jsonValue[j] = jsonObject.getString(responseKeyArray[j]);
                        }
                    }
                    arrayList.add(jsonValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "Parser Exception: " + e.toString());
        }
        return arrayList;
    }
}
