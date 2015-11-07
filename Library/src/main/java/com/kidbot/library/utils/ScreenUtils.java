package com.kidbot.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 屏幕操作类
 * User: YJX
 * Date: 2015-11-04
 * Time: 15:47
 */
public final class ScreenUtils {

    /**
     * 得到屏幕宽度
     * @param context 上下文
     * @return 屏幕宽度
     */
    @CheckResult
    public static int getScreenWidth(@NonNull Context context){
        WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 得到屏幕高度
     * @param context 上下文
     * @return 屏幕高度
     */
    @CheckResult
    public static int getScreenHeight(@NonNull Context context){
        WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 隐藏状态栏
     * @param activity 对应页面
     */
    public static void hideStateBar(@NonNull Activity activity){
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 沉浸式状态栏 设置 只能在 android 4.4 版本以上设置
     * @param activity 当前 activity
     * @param statusColor 颜色
     */
    public static void statusBarCompat(@NonNull Activity activity,int statusColor){
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            activity.getWindow().setStatusBarColor(statusColor);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            View rootView=activity.getWindow().getDecorView();
            ViewGroup contentView= (ViewGroup) rootView.findViewById(android.R.id.content);
            View view=new View(activity);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(activity)));
            view.setBackgroundColor(statusColor);
            contentView.addView(view);
        }
    }


    /**
     * 得到状态栏的高度
     * @param context 上下文
     * @return 高度
     */
    public static int getStatusBarHeight(@NonNull Context context){
        Resources resources=context.getResources();
        int result=0;
        int resourceId=resources.getIdentifier("status_bar_height","dimen","android");
        if (resourceId > 0)
        {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * dp 转 px
     * @param context 上下文
     * @param dp dp值
     * @return px值
     */
    public static int  dp2px(Context context,int dp){
        float density = getDensity(context);
        return (int)(dp * density + 0.5);
    }


    /**
     * px 转 dp
     * @param context 上下文
     * @param px dp值
     * @return dp值
     */
    public static int px2dp(Context context,int px){
        float density = getDensity(context);
        return (int)((px - 0.5) / density);
    }


    /**
     * 得到屏幕分辨率
     * @param context 上下文
     * @return 分辨率
     */
    public static float getDensity(Context context){
        WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
         windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.density;
    }



}
