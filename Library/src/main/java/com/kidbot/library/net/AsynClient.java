package com.kidbot.library.net;/**
 * Created by yjx on 15/11/9.
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * User: YJX
 * Date: 2015-11-09
 * Time: 16:38
 */
public class AsynClient {
    private static AsynClient instance;
    private RequestQueue requestQueue;
    private AsynClient(Context context){
        requestQueue= Volley.newRequestQueue(context);
    }

    public static AsynClient getInstance(Context context){
        if (instance==null){
            synchronized (AsynClient.class){
                if (instance==null){
                    instance=new AsynClient(context);
                }
            }
        }
        return instance;
    }

    public void addRequest(Request request){
        this.requestQueue.add(request);
    }
}
