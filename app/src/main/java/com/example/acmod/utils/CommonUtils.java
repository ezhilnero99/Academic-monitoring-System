package com.example.acmod.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;

import com.example.acmod.R;

public class CommonUtils {

    //Hides Keyboard
    public static void hideKeyboard(Activity activity){
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Checks for Internet Connectivity
    public static boolean alerter(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            Boolean flag = !(activeNetworkInfo != null && activeNetworkInfo.isConnected());
            if(flag)
                Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            return flag;
        }
        return false;
    }
    //Verify Necessary Permissions
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    //opensCustomBrowser
    public static void openCustomBrowser(Context context,String url){
        try{
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            customTabsIntent.launchUrl(context, Uri.parse(url));
            builder.setToolbarColor(context.getResources().getColor(R.color.colorAccent));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
