package com.dt.learning.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by dnnt9 on 2017/2/14.
 */

public class User implements Parcelable,Serializable {
    private String name;
    private int age;

    public static final Parcelable.Creator<User> CREATOR=new Parcelable.Creator<User>(){
        @Override
        public User createFromParcel(Parcel source) {
            User user=new User();
            user.name=source.readString();
            user.age=source.readInt();  //读取的顺序必须和写入的顺序一致
            return user;
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

}
