package com.androidafe.dobazar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidafe.dobazar.databinding.ActivityDetailBinding;
import com.androidafe.dobazar.room.CartDB;
import com.androidafe.dobazar.room.Carts;
import com.androidafe.dobazar.room.CartsDao;
import com.androidafe.dobazar.room.WishDB;
import com.androidafe.dobazar.room.Wishes;
import com.androidafe.dobazar.room.WishesDao;
import com.androidafe.dobazar.utils.AuthDB;
import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private String productName, productDesc, productImage;
    private int productPrice;
    private int quantity = 1;
    private int newPrice;

    AuthDB authDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent i = getIntent();
        productName = i.getStringExtra("pName");
        productDesc = i.getStringExtra("pDesc");
        productPrice = i.getIntExtra("pPrice",10);
        productImage = i.getStringExtra("pImage");

        authDB = new AuthDB(getApplicationContext());
        newPrice = productPrice;
        binding.pName.setText(productName);
        binding.productDetails.setText(productDesc);
        binding.proPrice.setText("৳ "+String.valueOf(productPrice));
        Glide.with(this).load(productImage).into(binding.productImg);
        quantity();


        //onclick
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>1){
                    newPrice = productPrice*quantity;
                }
                addCartsToDB(productName,newPrice,quantity,productImage);
                Toast.makeText(DetailActivity.this, "Product added to cart successfully", Toast.LENGTH_SHORT).show();
            }
        });

        binding.buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        binding.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToWishList(productName,productPrice,productImage);

            }
        });



    }

    void addToWishList(String pName, int pPrice, String pImage){

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {

                WishDB db = Room.databaseBuilder(getApplicationContext(),
                        WishDB.class,authDB.getUserName()+"_wish_db").build();
                WishesDao wishesDao = (WishesDao) db.wishesDao();
                wishesDao.insertWishData(new Wishes(pName,pPrice,pImage));
                Log.d("d", "run: inserted successfully");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this, "Product wishlisted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    void addCartsToDB(String pName, int pPrice, int pQuantity, String pImage){



        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {



                CartDB db = Room.databaseBuilder(getApplicationContext(),
                        CartDB.class, authDB.getUserName()+"_cart_db").build();
                CartsDao dao = (CartsDao) db.userDao();
                dao.insertCartsData(new Carts(pName,pPrice,pQuantity,pImage));
                Log.d("d", "run: inserted successfully");
            }
        });

    }


    void quantity() {

        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < 10) {
                    quantity = quantity + 1;
                    binding.quantityNo.setText(String.valueOf(quantity));
                }
            }
        });
        binding.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = quantity - 1;
                if (quantity<=0){
                    quantity = 1;
                }
                binding.quantityNo.setText(String.valueOf(quantity));
            }
        });
    }
}