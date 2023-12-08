package com.androidafe.dobazar.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidafe.dobazar.R;
import com.androidafe.dobazar.activities.ProductsActivity;
import com.androidafe.dobazar.databinding.CatItemsBinding;
import com.androidafe.dobazar.model.CategoryModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder>{

    List<CategoryModel> catList;
    Context context;
    public CatAdapter(List<CategoryModel> catList, Context context) {
        this.catList = catList;
        this.context = context;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cat_items, parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        CategoryModel model = catList.get(position);
        holder.binding.catName.setText(model.getCatName());
        holder.binding.catDesc.setText(model.getCatDesc());
        Glide.with(context).load(model.getCatImg()).into(holder.binding.catImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("catID", model.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return catList.size();
    }



    public class CatViewHolder extends RecyclerView.ViewHolder {
        CatItemsBinding binding;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CatItemsBinding.bind(itemView);
        }
    }



}