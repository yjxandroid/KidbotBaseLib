package com.kidbot.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

/**
 * 网络工具类
 * User: YJX
 * Date: 2015-10-31
 * Time: 20:42
 */
public final class NetUtils {

    @CheckResult
    public static boolean isNet3G(@NonNull Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if (ConnectivityManager.TYPE_MOBILE==info.getType()){
            return true;
        }
        return false;
    }
    @CheckResult
    public static boolean isNetWifi(@NonNull Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if (ConnectivityManager.TYPE_WIFI==info.getType()){
            return true;
        }
        return false;
    }
}
