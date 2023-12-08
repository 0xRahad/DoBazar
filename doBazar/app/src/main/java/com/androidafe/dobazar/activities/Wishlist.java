package com.androidafe.dobazar.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidafe.dobazar.R;
import com.androidafe.dobazar.adapters.WishlistAdapter;
import com.androidafe.dobazar.databinding.ActivityWishlistBinding;
import com.androidafe.dobazar.room.WishDB;
import com.androidafe.dobazar.room.Wishes;
import com.androidafe.dobazar.utils.AuthDB;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Wishlist extends AppCompatActivity {

    private List<Wishes> wishesList;
    private ActivityWishlistBinding binding;
    private WishlistAdapter adapter;
    private WishDB wishDB;
    private TextView totalPriceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWishlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        totalPriceView = findViewById(R.id.totalPrice);
        wishesList = new ArrayList<>();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AuthDB authDB = new AuthDB(getApplicationContext());
        String dbName = authDB.getUserName() + "_wish_db";

        SharedPreferences sharedPreferences = getSharedPreferences("dbStatus", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("dbName", dbName);
        editor.apply();

        // Check if it's the same user or a new user
        if (!authDB.isSameUser()) {
            // Different user, create a new database
            createNewDatabase(authDB.getUserName());
        } else {
            // Same user, open the previous database
            openPreviousDatabase(authDB.getUserName());
        }
    }

    private void createNewDatabase(String username) {
        String dbName = username + "_wish_db";
        // Store the new database name
        SharedPreferences.Editor editor = getSharedPreferences("dbStatus", MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.apply();

        // Create the new database
        WishDB db = Room.databaseBuilder(getApplicationContext(), WishDB.class, dbName).build();

        loadAndDisplayData();
    }

    private void openPreviousDatabase(String username) {
        String dbName = username + "_wish_db";

        wishDB = Room.databaseBuilder(getApplicationContext(), WishDB.class, dbName).build();

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            wishesList = wishDB.wishesDao().getWishes();

            runOnUiThread(() -> {
                loadAndDisplayData();
            });
        });
    }

    private void loadAndDisplayData() {
        adapter = new WishlistAdapter(wishesList, Wishlist.this);
        RecyclerView wishRecyclerView = findViewById(R.id.wishRecylerView); // Replace with the actual ID
        wishRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        wishRecyclerView.setAdapter(adapter);
    }
}
