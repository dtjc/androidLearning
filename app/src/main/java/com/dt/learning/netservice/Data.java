package com.dt.learning.netservice;

import com.dt.learning.aidl.User;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dnnt9 on 2017/2/14.
 */

public interface Data {
    @POST("test")
    Call<User> getUserViaPost();

    @GET("test")
    Call<User> getUserViaGet();

    @GET("test")
    Observable<User> getUserWithRx();
}
