package com.example.ic2.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit instance;
    public static String IMAGE_PATH ="https://";
    private static final  String url ="https://ic2.se/api/";

    private static Retrofit getInstance(){

        if(instance==null){
            instance=new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return instance;
    }

    public static IApi getAPI(){
        return getInstance().create(IApi.class);
    }
}
