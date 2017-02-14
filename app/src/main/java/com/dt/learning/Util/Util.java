package com.dt.learning.Util;

import android.app.Application;
import android.widget.Toast;

import com.dt.learning.MyApplication;

import java.util.Random;

/**
 * Created by dnnt9 on 2017/2/13.
 */

public class Util {
//    public enum Letter{
//        q(0),w,e,r,t,y,u,i,o,p,a,s,d,f,g,h,j,k,l,z,x,c,v,b,n,m;
//        private int index;
//
//        Letter(int i) {
//            index=i;
//        }
//    }
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
}
