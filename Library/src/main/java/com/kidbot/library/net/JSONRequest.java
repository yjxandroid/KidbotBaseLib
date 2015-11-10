package com.kidbot.library.net;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.kidbot.library.Log.L;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON 请求
 * User: YJX
 * Date: 2015-11-09
 * Time: 16:51
 */
public  class JSONRequest<T> extends Request<T> {
    private Class<T> clazz;
    private Response.Listener<T> mSuccessListener;
    private HashMap<String,String> params;
    public JSONRequest(int method, String url,Class clazz, Response.Listener mSuccessListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.mSuccessListener=mSuccessListener;
        this.clazz=clazz;
    }

    public JSONRequest(int method, String url,HashMap<String,String> params,Class clazz, Response.Listener mSuccessListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.mSuccessListener=mSuccessListener;
        this.clazz=clazz;
        this.params=params;
    }


    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode!=200){
            return Response.error(new VolleyError("failer"));
        }else{
            try {
                String content=new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                L.e(content);
                T t= JSON.parseObject(content,clazz);
                return Response.success(t,HttpHeaderParser.parseCacheHeaders(response));
            } catch (Exception e) {
                e.printStackTrace();
                return Response.error(new VolleyError("failer"));
            }
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mSuccessListener.onResponse(response);
    }

    @Override
    public HashMap<String, String> getParams() {
        if (getMethod()==Method.GET){
            return null;
        }else {
            return params;
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
         HashMap<String,String> headers=new HashMap<String, String>();
        headers.put("Charset","UTF-8");
        headers.put("Accept-Encoding","gzip");
        return headers;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        RetryPolicy retryPolicy=new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return retryPolicy;
    }
}
