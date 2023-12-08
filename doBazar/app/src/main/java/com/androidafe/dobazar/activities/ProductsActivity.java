package com.androidafe.dobazar.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.androidafe.dobazar.adapters.CustomProducts;
import com.androidafe.dobazar.databinding.ActivityProductsBinding;
import com.androidafe.dobazar.model.ProductModel;
import com.androidafe.dobazar.utils.ApiController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {

    ActivityProductsBinding binding;
    ArrayList<ProductModel> productModels;
    CustomProducts products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        productModels = new ArrayList<>();

        binding.proRecylerView.setVisibility(View.VISIBLE);
        binding.error.setVisibility(View.GONE);


        products = new CustomProducts(productModels,getApplicationContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,false);
        binding.proRecylerView.setLayoutManager(gridLayoutManager);
        binding.proRecylerView.setAdapter(products);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        int catID = getIntent().getIntExtra("catID",52);

        Call<ArrayList<ProductModel>> call = ApiController.getInstance().getApi().getProducts();
        call.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {

                if (response.isSuccessful()){
                    ArrayList<ProductModel> models = response.body();
                    boolean foundProducts = false;
                    for (ProductModel pModel :models){

                        int pCatID = pModel.getCatId();
                        if (catID == pCatID){
                            productModels.add(pModel);
                            foundProducts = true;
                        }

                    }
                    if (foundProducts) {
                        products.notifyDataSetChanged();
                    } else {
                        binding.proRecylerView.setVisibility(View.GONE);
                        binding.error.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {

            }
        });

    }
}