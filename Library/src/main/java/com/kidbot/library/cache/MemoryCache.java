package com.kidbot.library.cache;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

/**
 * User: YJX
 * Date: 2015-11-10
 * Time: 11:22
 */
 final class MemoryCache {
    private LruCache<String,Object> memoryCache;

    public MemoryCache() {
        this.memoryCache =new LruCache<String,Object>((int)Runtime.getRuntime().maxMemory());
    }

    public void add(@NonNull String key,@NonNull Object value){
        this.memoryCache.put(key, value);
    }

    public void remove(@NonNull String key){
        this.memoryCache.remove(key);
    }
    public Object get(@NonNull String key){
        return this.memoryCache.get(key);
    }
    public void update(@NonNull String key,@NonNull Object value){
        if (this.memoryCache.get(key)!=null){
            this.memoryCache.remove(key);
        }
        this.memoryCache.put(key, value);
    }




}
