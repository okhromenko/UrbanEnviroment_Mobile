package com.example.urbanenviroment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.CategoryAnimals;
import com.example.urbanenviroment.page.animals.AnimalPage;
import com.example.urbanenviroment.page.profile.org.AddPhoto;

import java.util.List;

public class CategoryAnimalAdapter extends RecyclerView.Adapter<CategoryAnimalAdapter.CategoryAnimalViewHolder> {

    Context context;
    List<CategoryAnimals> categoryAnimalsList;

    public CategoryAnimalAdapter(Context context, List<CategoryAnimals> categoryAnimalsList) {
        this.context = context;
        this.categoryAnimalsList = categoryAnimalsList;
    }

    @NonNull
    @Override
    public CategoryAnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryItem = LayoutInflater.from(context).inflate(R.layout.list_animal_item, parent, false);
        return new CategoryAnimalAdapter.CategoryAnimalViewHolder(categoryItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAnimalViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(categoryAnimalsList.get(position).getTitle());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPhoto.name_category = categoryAnimalsList.get(position).getTitle();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryAnimalsList.size();
    }

    public static final class CategoryAnimalViewHolder extends RecyclerView.ViewHolder{

        TextView name;

        public CategoryAnimalViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.animal_name_category);
        }
    }
}
