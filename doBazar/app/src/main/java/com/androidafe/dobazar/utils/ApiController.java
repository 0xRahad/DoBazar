package com.androidafe.dobazar.utils;

import com.androidafe.dobazar.interfaces.RetrofitClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {

    static final String url = "https://devtutor.me/";
    private static ApiController clientObject;
    private static Retrofit retrofit;

    ApiController() {

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiController getInstance(){
        if (clientObject == null){
            clientObject = new ApiController();
        }return clientObject;
    }

    public RetrofitClient getApi(){
        return retrofit.create(RetrofitClient.class);
    }
}
