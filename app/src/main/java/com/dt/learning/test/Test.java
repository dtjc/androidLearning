package com.dt.learning.test;

import com.dt.learning.aidl.User;

/**
 * Created by dnnt9 on 2017/2/15.
 */

public class Test {
    private static User staticUser;
    public static void setStaticUser(User user){
        staticUser=user;
    }
    public static User getStaticUser(){
        return staticUser;
    }
    private int k;

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

}
