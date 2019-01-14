package com.dt.learning.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dt.learning.R;
import com.dt.learning.util.Util;
import com.dt.learning.aidl.User;
import com.dt.learning.netservice.Data;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkActivity extends AppCompatActivity {

    private TextView tvReGet;
    private TextView tvRePost;
    private TextView tvReRxjava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvReGet=(TextView)findViewById(R.id.content_network_tv_retrofit_get);
        tvRePost=(TextView)findViewById(R.id.content_network_tv_retrofit_post);
        tvReRxjava=(TextView)findViewById(R.id.content_network_tv_retrofit_rxjava);

    }

    public void retrofitRequest(View view){
        switch (view.getId()){
            case R.id.content_network_btn_retrofit_get:
                Util.getRetrofit()
                        .create(Data.class)
                        .getUserViaGet()
                        .enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()){
                                    User user=response.body();
                                    tvReGet.setText("name:"+user.getName()+",age:"+String.valueOf(user.getAge()));
                                    Log.e("responseCode",String.valueOf(response.code()));
                                    Log.e("responseMsg",response.message());
                                    okhttp3.Headers headers = response.headers();
                                    for (int i=0;i<headers.size();i++){
                                        Log.e(headers.name(i),headers.value(i));
                                    }
                                }else {
                                    Util.showToast(String.valueOf(response.code()));
                                    try {
                                        Log.e("error body",response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Util.showToast("Failure");
                                t.printStackTrace();
                            }
                        });
                break;
            case R.id.content_network_btn_retrofit_post:
                Util.getRetrofit()
                        .create(Data.class)
                        .getUserViaPost()
                        .enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Util.showToast(String.valueOf(response.code()));
//                                User user=response.body();
//                                tvRePost.setText("name:"+user.getName()+",age:"+String.valueOf(user.getAge()));
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Util.showToast("Failure");
                                t.printStackTrace();
                            }
                        });
                break;
            case R.id.content_network_btn_retrofit_rxjava:
                Util.getRetrofit()
                        .create(Data.class)
                        .getUserWithRx()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<User>() {
                            private Disposable disposable;
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable=d;
                            }

                            @Override
                            public void onNext(User value) {
                                tvReRxjava.setText("name:"+value.getName()+",age:"+String.valueOf(value.getAge()));
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                Util.showToast("Error");
                            }

                            @Override
                            public void onComplete() {
                                disposable.dispose();
                            }
                        });
                break;
        }
    }

}
