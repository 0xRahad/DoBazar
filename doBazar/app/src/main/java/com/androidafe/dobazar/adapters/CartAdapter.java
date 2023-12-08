package com.androidafe.dobazar.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.androidafe.dobazar.R;
import com.androidafe.dobazar.databinding.CartItemsBinding;
import com.androidafe.dobazar.room.CartDB;
import com.androidafe.dobazar.room.Carts;
import com.androidafe.dobazar.utils.AuthDB;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    List<Carts> carts;
    Context context;
    private TextView totalPriceTextView; // Added TextView for total price
    int newQuantity;


    public CartAdapter(List<Carts> carts, Context context, TextView totalPriceTextView) {
        this.carts = carts;
        this.context = context;
        this.totalPriceTextView = totalPriceTextView;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_items, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Carts cart = carts.get(position);
        holder.binding.pName.setText(cart.getProductName());
        holder.binding.pPrice.setText("৳" + String.valueOf(cart.getProductPrice()));
        holder.binding.pQuantity.setText("Quantity: " + String.valueOf(cart.getProductQuantity()));
        Glide.with(context).load(cart.getProductImage()).into(holder.binding.productImg);

        holder.binding.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCartItem(cart);
            }
        });

        newQuantity = cart.getProductQuantity();

        holder.binding.quantityNo.setText(String.valueOf(newQuantity));

        holder.binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newQuantity < 10) {
                    newQuantity = newQuantity + 1;
                    holder.binding.quantityNo.setText(String.valueOf(newQuantity));
                }
            }
        });

        holder.binding.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newQuantity = newQuantity - 1;

                if (newQuantity<=1){
                    newQuantity = cart.getProductQuantity();
                }
                holder.binding.quantityNo.setText(String.valueOf(newQuantity));
            }
        });

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }



    public class CartViewHolder extends RecyclerView.ViewHolder {
        CartItemsBinding binding;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CartItemsBinding.bind(itemView);
        }
    }


    public void deleteCartItem(Carts cart) {
        AuthDB authDB = new AuthDB(context);
        CartDB db = Room.databaseBuilder(context, CartDB.class, authDB.getUserName()+"_cart_db").build();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.userDao().deleteCartItem(cart);

                // Update the UI after deletion on the main thread
                ((Activity) context).runOnUiThread(() -> {
                    carts.remove(cart);
                    notifyDataSetChanged();
                    // Calculate total price and update the TextView
                    double totalPrice = calculateTotalPrice();
                    totalPriceTextView.setText("৳" + String.format("%.2f", totalPrice));
                });
            }
        });
    }

    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Carts cart : carts) {
            totalPrice += cart.getProductPrice();
        }
        return totalPrice;
    }






}