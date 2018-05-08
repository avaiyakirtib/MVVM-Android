package com.psk.mvvmdemo.data;

import android.util.Log;

import com.psk.mvvmdemo.MVVMApplication;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;

public class DataManager {
    
    private static final String TAG = "DataManager";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient client;

    private static DataManager ourInstance = new DataManager();

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
        client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(false)
                .build();
    }

    public static void runGetAction(final String url, final DataHandler handler) {

        final DataManager dataManager = DataManager.getInstance();
        Request.Builder request = new Request.Builder().url(url);
        request.get();

        dataManager.client.newCall(request.build()).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, final IOException e) {
                if (handler != null) {
                    MVVMApplication.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handler.handleResult(null, e.getMessage());
                        }
                    });
                } else {
                    Log.e(TAG, "Unhandled request error: " + e.getMessage());
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                if (response.code() != 200) {
                    if (handler != null) {
                        MVVMApplication.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                handler.handleResult(null, response.message());
                            }
                        });
                    } else {
                        Log.e(TAG, "Unhandled server error: " + response.message());
                    }
                    return;
                }

                ResponseBody responseBody = response.body();
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.buffer();
                final String body = buffer.clone().readString(Charset.forName("UTF-8"));

                if (handler != null) {
                    MVVMApplication.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handler.handleResult(body, null);
                        }
                    });
                }
            }
        });
    }

    public interface DataHandler {
        void handleResult(String data, String error);
    }

}
