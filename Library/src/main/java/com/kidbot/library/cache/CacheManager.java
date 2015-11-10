package com.kidbot.library.cache;

import android.support.annotation.NonNull;

/**
 * 缓存管理器
 * User: YJX
 * Date: 2015-11-10
 * Time: 11:21
 */
public final class CacheManager {
    public static CacheManager instance;
    private MemoryCache memoryCache;
    private CacheManager(){
        this.memoryCache=new MemoryCache();
    }
    public  static CacheManager getInstance(){
        if (instance==null){
            synchronized (CacheManager.class){
                if (instance==null){
                    instance=new CacheManager();
                }
            }
        }
        return  instance;
    }


    public void add(@NonNull String key,@NonNull Object value){
        this.memoryCache.add(key, value);
    }

    public void remove(@NonNull String key){
        this.memoryCache.remove(key);
    }

    public Object get(@NonNull String key){
      return   this.memoryCache.get(key);
    }

    public void update(@NonNull String key,@NonNull Object value){
        this.memoryCache.update(key, value);
    }




}
