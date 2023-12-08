package com.androidafe.dobazar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidafe.dobazar.R;
import com.androidafe.dobazar.databinding.SliderLayoutBinding;
import com.androidafe.dobazar.model.ProductModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{


    private static final int MAX_ITEMS_TO_DISPLAY = 5;
    ArrayList<ProductModel> productModels;
    Context context;


    public SliderAdapter(ArrayList<ProductModel> productModels, Context context) {
        this.productModels = productModels;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        ProductModel model = productModels.get(position);
        Glide.with(context).load(model.getpImage()).into(holder.layoutBinding.hlImg);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return Math.min(productModels.size(), MAX_ITEMS_TO_DISPLAY);
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        SliderLayoutBinding layoutBinding;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutBinding = SliderLayoutBinding.bind(itemView);
        }
    }


}