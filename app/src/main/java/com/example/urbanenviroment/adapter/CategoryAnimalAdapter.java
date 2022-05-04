package com.example.urbanenviroment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.CategoryAnimals;
import com.example.urbanenviroment.page.animals.AnimalPage;
import com.example.urbanenviroment.page.profile.org.AddPhoto;

import java.util.List;

public class CategoryAnimalAdapter extends RecyclerView.Adapter<CategoryAnimalAdapter.CategoryAnimalViewHolder> {

    Context context;
    List<CategoryAnimals> categoryAnimalsList;
    int row_index = -1;

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
                row_index = position;
                notifyDataSetChanged();
            }
        });

        if (row_index == position) {
            holder.background.setCardBackgroundColor(ContextCompat.getColor(context, R.color.basic_blue));
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.background.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_gray_1));
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.dark_gray_2));
        }

    }

    @Override
    public int getItemCount() {
        return categoryAnimalsList.size();
    }

    public static final class CategoryAnimalViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        CardView background;

        public CategoryAnimalViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.animal_name_category);
            background = itemView.findViewById(R.id.background_color);
        }
    }
}
