package com.androidafe.dobazar.interfaces;

import com.androidafe.dobazar.model.CategoryModel;
import com.androidafe.dobazar.model.ProductModel;
import com.androidafe.dobazar.model.UserResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitClient {

    @FormUrlEncoded
    @POST("admin/register.php")
    Call<UserResponse> getRegisterResponse(
            @Field("fname") String fname,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("address") String address
    );

    @FormUrlEncoded
    @POST("admin/login.php")
    Call<UserResponse> getLoginResponse(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("admin/products.php")
    Call<ArrayList<ProductModel>> getProducts();

    @GET("admin/categories.php")
    Call<ArrayList<CategoryModel>> getCategories();



}
