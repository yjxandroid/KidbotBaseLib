package yjx.kidbotbaselib;/**
 * Created by yjx on 15/11/3.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kidbot.library.Log.L;

import java.io.IOException;

/**
 * User: YJX
 * Date: 2015-11-03
 * Time: 17:58
 */
public class KidbotHttpServer extends Service {
    private HttpServer server;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        server=new HttpServer(40000);
        try {
            server.start();
            L.e("HttpServer 开启");
        } catch (IOException e) {
            e.printStackTrace();
            L.e("HttpServer 失败");
        }
    }
}
