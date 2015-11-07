package com.kidbot.library.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import java.io.PrintWriter;
import java.util.List;

/**
 * 系统相关的 api
 * User: YJX
 * Date: 2015-11-06
 * Time: 16:43
 */
public final class SystemUtils {

    /**
     * 得到指定包名 app 的版本号
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 版本号
     */
    @CheckResult
    public static int getAppVersionCode(@NonNull Context context, @NonNull String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(packageName, 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 得到指定包名 app 的版本号
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 版本号
     */
    @CheckResult
    public static String getAppVersionName(@NonNull Context context, @NonNull String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(packageName, 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    /**
     * 得到 app 的 签名的 hash值
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 签名的 hash值
     */
    @CheckResult
    public  static int getSignature(@NonNull Context context, @NonNull String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            Signature[] signature = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
            return signature[0].hashCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 安装app
     *
     * @param context 上下文
     * @param appPath 路径
     */
    public static void installApp(@NonNull Context context, @NonNull String appPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(appPath);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 删除app
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public  static void deleteApp(@NonNull Context context, @NonNull String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(intent);

    }

    /**
     * 打开app
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void openApp(@NonNull Context context, @NonNull String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 得到机器中所有的 app
     *
     * @param context 上下文
     * @return 所有的app信息
     */
    public static List<ResolveInfo> getAllAppInfos(@NonNull Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return packageManager.queryIntentActivities(intent, 0);
    }


    /**
     * 判断是否具有root权限
     * @return true 有  false 没有
     */
    @CheckResult
    public static boolean hasRootPerssion() {
        PrintWriter printWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            printWriter = new PrintWriter(process.getOutputStream());
            printWriter.flush();
            printWriter.close();
            return returnResult(process.waitFor());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 静默安装 app
     * @param appPath 路径
     * @return true 成功  false 失败
     */
    public static  boolean silentlyInstallApp(@NonNull String appPath){
        PrintWriter printWriter;
        Process process;
        try {
            process=Runtime.getRuntime().exec("su");
            printWriter=new PrintWriter(process.getOutputStream());
            printWriter.println("LD_LIBRARY_PATH=/vendor/lib:/system/lib ");
            printWriter.println("pm install -r " + appPath);
            printWriter.flush();
            printWriter.close();
            return returnResult(process.waitFor());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 静默卸载 app
     * @param packageName 包名
     * @return true 成功  false 失败
     */
    @CheckResult
    public static  boolean silentlyUninstallApp(@NonNull String packageName){
        PrintWriter printWriter;
        Process process;
        try {
            process=Runtime.getRuntime().exec("su");
            printWriter=new PrintWriter(process.getOutputStream());
            printWriter.println("LD_LIBRARY_PATH=/vendor/lib:/system/lib ");
            printWriter.println("pm uninstall " + packageName);
            printWriter.flush();
            printWriter.close();
            return returnResult(process.waitFor());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * 解析 Process 的值
     * @param value 值
     * @return 是否root
     */
    @CheckResult
    private static boolean returnResult(int value){
        // 代表成功
        if (value == 0) {
            return true;
        } else if (value == 1) { // 失败
            return false;
        } else { // 未知情况
            return false;
        }
    }


}
