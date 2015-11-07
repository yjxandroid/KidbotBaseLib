package com.kidbot.library.hookdex;

import android.app.Application;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * 分包加载
 * User: YJX
 * Date: 2015-11-01
 * Time: 21:51
 */
public final class Hook {


    public void loadDex(@NonNull Application application,@NonNull String dexPath) {
        boolean hasBaseDexClassLoader = true;
        try {
            Class.forName("dalvik.system.BaseDexClassLoader");
        } catch (ClassNotFoundException e) {
            hasBaseDexClassLoader = false;
        }
        if (hasBaseDexClassLoader) {
            PathClassLoader pathLoader = (PathClassLoader) application.getClassLoader();
            DexClassLoader dexLoader=new DexClassLoader(dexPath,application.getDir("dex",0).getAbsolutePath(),dexPath,application.getClassLoader());

        }
    }


    /**
     * 得到 BaseDexClassLoader 中 字段 pathList 的值
     * @param loader BaseDexClassLoader
     * @return pathList
     */
    private Object getDexPathList(BaseDexClassLoader loader){
        try {
            Field field=  loader.getClass().getDeclaredField("pathList");
            field.setAccessible(true);
            return field.get(loader);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 得到 BaseDexClassLoader 中 字段 dexElements 的值
     * @param obj
     * @return
     */
    private Object getDexElements(Object obj){
        try {
            Field field=obj.getClass().getDeclaredField("dexElements");
            field.setAccessible(true);
            return  field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
