package com.androidafe.dobazar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;


import com.androidafe.dobazar.R;
import com.androidafe.dobazar.adapters.CatAdapter;
import com.androidafe.dobazar.databinding.ActivityCategoryBinding;
import com.androidafe.dobazar.model.CategoryModel;
import com.androidafe.dobazar.utils.ApiController;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding binding;
    List<CategoryModel> models;
    CatAdapter catAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        models = new ArrayList<>();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Call<ArrayList<CategoryModel>> call = ApiController.getInstance().getApi().getCategories();
        call.enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {

                if (response.isSuccessful()){
                    models = response.body();
                    catAdapter = new CatAdapter(models,getApplicationContext());
                    binding.catRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    binding.catRecylerView.setAdapter(catAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {

            }
        });


    }
}