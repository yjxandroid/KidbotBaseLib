package com.kidbot.library.net;/**
 * Created by yjx on 15/11/10.
 */

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: YJX
 * Date: 2015-11-10
 * Time: 10:50
 */
public class OkHttpStack extends HurlStack {
    private final OkUrlFactory okUrlFactory;
    public OkHttpStack() {
        this(new OkHttpClient());
    }
    public OkHttpStack(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            throw new NullPointerException("Client must not be null.");
        }
        this.okUrlFactory = new OkUrlFactory(okHttpClient);
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return okUrlFactory.open(url);
    }
}
