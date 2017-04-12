package com.dt.learning.Util;


import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import com.dt.learning.MyApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dnnt9 on 2017/2/13.
 */

public class Util {
    private static Toast toast;
    public static void showToast(String txt){
        if (toast==null)    toast=Toast.makeText(MyApplication.getContext(),txt,Toast.LENGTH_SHORT);
        else toast.setText(txt);
        toast.show();
    }
    public static String randomLetter(int length){
        char[] charStr;
        if (length<16) charStr=new char[length];
        else charStr=new char[16];
        Random random=new Random(System.currentTimeMillis());
        for (int i=0;i<length;i++){
            int r=random.nextInt(26)+'a';
            charStr[i]=(char)r;
        }
        return String.valueOf(charStr);
    }

    public static int randomAge(){
        Random random=new Random(System.currentTimeMillis());
        return random.nextInt(120);
    }

    public static Retrofit getRetrofit(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        long t1 = System.nanoTime();
                        Log.i("HttpRequest",String.format("Sending request %s on %s%n%s",
                                request.url(), chain.connection(), request.headers()));
                        Response response = chain.proceed(request);
                        long t2 = System.nanoTime();
                        Log.i("HttpResponse",String.format("Received response for %s in %.1fms%n%s",
                                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                        return response;
                    }
                })
                .build();
        return new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl("http://192.168.1.145:8080/test2/")
                .build();
    }
}
