package com.kidbot.library.server;

import android.support.annotation.CheckResult;

import java.util.Map;

/**
 * 基础 本地 服务
 * User: YJX
 * Date: 2015-11-04
 * Time: 08:59
 */
public abstract class KidbotServer  extends NanoHTTPD{
    public KidbotServer(int port) {
        super(port);
    }

    public KidbotServer(String hostname, int port) {
        super(hostname, port);
    }


    @Override
    public Response serve(IHTTPSession session) {
        Method method=session.getMethod();
        String uri=session.getUri();
        Map<String,String> params=session.getParms();
        switch (method){
            case GET:
                return get(uri);
            case POST:
                return post(uri, params);
            case PUT:
                return put(uri,params);
            case DELETE:
                return delete(uri);
        }
        return super.serve(session);
    }

    /**
     * GET 请求
     * @param uri 请求地址
     * @return 返回响应
     */
    @CheckResult
    public abstract Response get(String uri);

    /**
     * POST 请求
     * @param uri 请求地址
     * @param params 请求参数
     * @return 返回响应
     */
    @CheckResult
    public abstract Response post(String uri,Map<String,String> params);

    /**
     * PUT 请求
     * @param uri 请求地址
     * @param params 请求参数
     * @return 返回响应
     */
    @CheckResult
    public abstract Response put(String uri,Map<String,String> params);

    /**
     * DELETE 请求
     * @param uri 请求地址
     * @return 返回响应
     */
    @CheckResult
    public abstract Response delete(String uri);
}
