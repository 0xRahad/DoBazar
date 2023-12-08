package com.androidafe.dobazar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidafe.dobazar.R;
import com.androidafe.dobazar.databinding.ItemCardLayoutBinding;
import com.androidafe.dobazar.model.ProductModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomProducts extends RecyclerView.Adapter<CustomProducts.MyViewHolder> {
    ArrayList<ProductModel> modelList;
    Context context;

    public CustomProducts(ArrayList<ProductModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomProducts.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomProducts.MyViewHolder holder, int position) {
        ProductModel model = modelList.get(position);
        holder.binding.productTitle.setText(model.getpName());
        holder.binding.productDesc.setText(model.getpDesc());
        Glide.with(context).load(model.getpImage()).into(holder.binding.productImg);
        holder.binding.productPrice.setText("৳ "+String.valueOf(model.getpPrice()));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCardLayoutBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCardLayoutBinding.bind(itemView);
        }
    }
}
