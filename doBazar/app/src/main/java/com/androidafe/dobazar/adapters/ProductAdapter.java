package com.androidafe.dobazar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidafe.dobazar.R;
import com.androidafe.dobazar.activities.DetailActivity;
import com.androidafe.dobazar.databinding.ItemCardLayoutBinding;
import com.androidafe.dobazar.databinding.SliderLayoutBinding;
import com.androidafe.dobazar.model.ProductModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.SliderViewHolder>{


    ArrayList<ProductModel> productModels;
    Context context;

    public ProductAdapter(Context context, ArrayList<ProductModel> productModels) {
        this.productModels = productModels;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_layout, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        ProductModel model = productModels.get(position);
        holder.layoutBinding.productTitle.setText(model.getpName());
        holder.layoutBinding.productDesc.setText(model.getpDesc());
        holder.layoutBinding.productPrice.setText(model.getpPrice()+"tk");


        Glide.with(context).load(model.getpImage()).into(holder.layoutBinding.productImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("pName", model.getpName());
                intent.putExtra("pDesc", model.getpDesc());
                intent.putExtra("pPrice", model.getpPrice());
                intent.putExtra("pImage", model.getpImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        ItemCardLayoutBinding layoutBinding;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutBinding = ItemCardLayoutBinding.bind(itemView);
        }
    }
}