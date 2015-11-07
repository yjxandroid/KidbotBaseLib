package com.kidbot.library.Log;

import android.util.Log;

/**
 * 日志打印
 * User: YJX
 * Date: 2015-11-03
 * Time: 11:20
 */
public class L {
    public static boolean isdebug=true;
    public static void d(String msg){
        if (isdebug){
            Log.d(generateTag(LogUtils.getStackTrace()), msg);
        }
    }

    public static void e(String msg){
        if (isdebug){
            Log.e(generateTag(LogUtils.getStackTrace()), msg);
        }
    }

    public static void v(String msg){
        if (isdebug){
            Log.v(generateTag(LogUtils.getStackTrace()), msg);
        }
    }

    public static void i(String msg){
        if (isdebug){
            Log.i(generateTag(LogUtils.getStackTrace()), msg);
        }
    }
    public static void w(String msg){
        if (isdebug){
            Log.w(generateTag(LogUtils.getStackTrace()), msg);
        }
    }

    public static void obj(Object obj){
        if (isdebug){
            Log.e(generateTag(LogUtils.getStackTrace()), LogUtils.objectToString(obj));
        }
    }

    public static void json(String json){
        if (isdebug){
            Log.e(generateTag(LogUtils.getStackTrace()), LogUtils.jsonToString(json));
        }
    }




    /**
     * 自动生成tag
     *
     * @return
     */
    private static String generateTag(StackTraceElement caller) {
        String stackTrace = caller.toString();
        stackTrace = stackTrace.substring(stackTrace.lastIndexOf('('), stackTrace.length());
        String tag = "%s%s.%s%s";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, "", callerClazzName, caller.getMethodName(), stackTrace);
        return tag;
    }
}
