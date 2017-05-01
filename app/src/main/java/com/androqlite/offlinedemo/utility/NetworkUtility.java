package com.androqlite.offlinedemo.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Base64;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by emran on 1/4/17.
 */

public class NetworkUtility {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static String authorizationString(String userName, String password) {
        String authorizationString = "Basic " + Base64.encodeToString(
                (userName + ":" + password).getBytes(),
                Base64.NO_WRAP); //Base64.NO_WRAP flag
        return authorizationString;
    }

    //MD5
    public static String md5(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")), 0, s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
