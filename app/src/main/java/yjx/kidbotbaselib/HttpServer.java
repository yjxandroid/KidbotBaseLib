package yjx.kidbotbaselib;

import com.kidbot.library.Log.L;

/**
 * User: YJX
 * Date: 2015-11-03
 * Time: 18:02
 */
public class HttpServer extends NanoHTTPD {
    public HttpServer(int port) {
        super(port);
    }

    public HttpServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();

        String uri = session.getUri();
        L.e(method + " '" + uri + "' ");
        return new NanoHTTPD.Response("hahaha");
    }
}
