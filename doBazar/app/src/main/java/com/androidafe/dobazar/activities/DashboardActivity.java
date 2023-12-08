package com.androidafe.dobazar.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.androidafe.dobazar.R;
import com.androidafe.dobazar.adapters.ProductAdapter;
import com.androidafe.dobazar.adapters.SliderAdapter;
import com.androidafe.dobazar.databinding.ActivityDashboardBinding;
import com.androidafe.dobazar.model.ProductModel;
import com.androidafe.dobazar.utils.ApiController;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    ImageView menuBtn;

    ProductAdapter productAdapter;
    ArrayList<ProductModel> products;

    TextView headerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        menuBtn = findViewById(R.id.navIcon);

        products = new ArrayList<>();


        showSlider();


        SharedPreferences pref = getSharedPreferences("status" ,MODE_PRIVATE);
        String hederName = pref.getString("userName", "No Name");
        View headerView = navigationView.getHeaderView(0);
        headerName = headerView.findViewById(R.id.headerUsrName);
        headerName.setText(hederName.toUpperCase());

        productAdapter = new ProductAdapter(this,products);

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        binding.bestSellingPlists.setLayoutManager(gridLayoutManager);
        binding.bestSellingPlists.setAdapter(productAdapter);
        getProducts();


        toggle = new ActionBarDrawerToggle(DashboardActivity.this,drawerLayout, R.string.openDrawer,R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserActivity.class));
            }
        });


        // Drawer item Click event ------
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId() == R.id.carts) {
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    // Close the drawer manually
                    drawerLayout.closeDrawer(GravityCompat.START);

                    return true; // Indicate that the item selection has been handled
                } else if (item.getItemId() == R.id.wishlist) {
                    startActivity(new Intent(getApplicationContext(), Wishlist.class));
                    // Close the drawer manually
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                else if (item.getItemId() == R.id.categories) {
                    startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                    // Close the drawer manually
                    drawerLayout.closeDrawer(GravityCompat.START);

                }else if (item.getItemId() == R.id.logout) {

                    SharedPreferences sharedPreferences = getSharedPreferences("status", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loginStatus",false);
                    editor.apply();

                    startActivity(new Intent(DashboardActivity.this,MainActivity.class));
                    finish();

                }

                return false;
            }
        });

    }


    void showSlider(){

        SliderAdapter sliderAdapter = new SliderAdapter(products,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.displayRecylerView.setLayoutManager(layoutManager);
        binding.displayRecylerView.setAdapter(sliderAdapter);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.displayRecylerView);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (layoutManager.findLastCompletelyVisibleItemPosition() < (sliderAdapter.getItemCount() - 1)) {
                    layoutManager.smoothScrollToPosition(
                            binding.displayRecylerView,
                            new RecyclerView.State(),
                            layoutManager.findLastCompletelyVisibleItemPosition() + 1
                    );
                } else {
                    layoutManager.smoothScrollToPosition(
                            binding.displayRecylerView,
                            new RecyclerView.State(),
                            0
                    );
                }
            }
        }, 0, 3000);

    }

    void getProducts() {
        Call<ArrayList<ProductModel>> call = ApiController.getInstance().getApi().getProducts();
        call.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if (response.isSuccessful()) {
                    List<ProductModel> productList = response.body();
                    if (productList != null) {
                        products.addAll(productList);
                        productAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(DashboardActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DashboardActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}