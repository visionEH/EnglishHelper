package com.example.visioneh.englishhelper.util;


import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 62588 on 2018/5/1.
 */

public class OkHttpUtil {
  /*  Dispatcher*/
    private static OkHttpUtil okHttp;
    private OkHttpClient client;
    private Handler handler;
    public static OkHttpUtil getInstance(){
        if(okHttp==null){
            synchronized (OkHttpUtil.class) {
                okHttp = new OkHttpUtil();
            }
        }
        return okHttp;
    }

    private OkHttpUtil(){
        client=new OkHttpClient();
        handler=new Handler();

    }
    public  void get(String url, final ResultCallback callback){
        Request.Builder builder=new Request.Builder().url(url);
        Request request=builder.build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                    if(callback!=null){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onfail(e);
                            }
                        });
                    }


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(callback!=null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onsuccess(response);
                        }
                    });
                }

            }
        });
    }

}
