package com.dt.learning.Util;


import android.widget.Toast;
import com.dt.learning.MyApplication;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.util.Random;
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
        return new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://192.168.0.105:8080/info/")
                .build();
    }
}
