package com.androidafe.dobazar.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.androidafe.dobazar.R;
import com.androidafe.dobazar.databinding.WishedItemsBinding;
import com.androidafe.dobazar.room.WishDB;
import com.androidafe.dobazar.room.Wishes;
import com.androidafe.dobazar.utils.AuthDB;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishViewHolder>{

    List<Wishes> wishesList;
    Context context;
    public WishlistAdapter(List<Wishes> wishes, Context context) {
        this.wishesList = wishes;
        this.context = context;
    }

    @NonNull
    @Override
    public WishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wished_items, parent, false);
        return new WishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishViewHolder holder, int position) {
        Wishes wishes = wishesList.get(position);
        holder.binding.pName.setText(wishes.getProductName());
        holder.binding.pPrice.setText("Price: ৳" + String.valueOf(wishes.getProductPrice()));
        Glide.with(context).load(wishes.getProductImage()).into(holder.binding.productImg);

        holder.binding.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWishItem(wishes);
            }
        });


    }

    @Override
    public int getItemCount() {
        return wishesList.size();
    }



    public class WishViewHolder extends RecyclerView.ViewHolder {
        WishedItemsBinding binding;

        public WishViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = WishedItemsBinding.bind(itemView);
        }
    }


    public void deleteWishItem(Wishes wishes) {

        AuthDB authDB = new AuthDB(context);
        WishDB db = Room.databaseBuilder(context, WishDB.class, authDB.getUserName()+"_wish_db").build();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.wishesDao().deleteWishes(wishes);

                // Update the UI after deletion on the main thread
                ((Activity) context).runOnUiThread(() -> {
                    wishesList.remove(wishes);
                    notifyDataSetChanged();

                });
            }
        });
    }



}