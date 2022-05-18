package com.example.urbanenviroment.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.CategoryAnimals;
import com.example.urbanenviroment.page.animals.AnimalPage;
import com.example.urbanenviroment.page.filter.FilterAnimal;
import com.example.urbanenviroment.page.filter.FilterHelp;
import com.example.urbanenviroment.page.profile.org.AddPhoto;

import java.util.ArrayList;
import java.util.List;

public class CategoryAnimalAdapter extends RecyclerView.Adapter<CategoryAnimalAdapter.CategoryAnimalViewHolder> {

    Context context;
    List<CategoryAnimals> categoryAnimalsList;
    int row_index = -1;
    boolean flag_highlight;
    int type_recycler;

    public CategoryAnimalAdapter(Context context, List<CategoryAnimals> categoryAnimalsList, boolean flag_highlight, int type_recycler) {
        this.context = context;
        this.categoryAnimalsList = categoryAnimalsList;
        this.flag_highlight = flag_highlight;
        this.type_recycler = type_recycler;
    }

    @NonNull
    @Override
    public CategoryAnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryItem;

        if(flag_highlight){
            categoryItem = LayoutInflater.from(context).inflate(R.layout.list_filter_item, parent, false);
        }
        else{
            categoryItem = LayoutInflater.from(context).inflate(R.layout.list_animal_item, parent, false);
        }
        return new CategoryAnimalAdapter.CategoryAnimalViewHolder(categoryItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAnimalViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (!flag_highlight){
            holder.name.setText(categoryAnimalsList.get(position).getTitle());

            holder.name.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    AddPhoto.name_category = categoryAnimalsList.get(position).getTitle();
                    row_index = position;
                    notifyDataSetChanged();

                    String name_category = categoryAnimalsList.get(position).getTitle();

                    if (type_recycler == 1 && FilterAnimal.click_animal_list_animal.stream().noneMatch(c -> c.getTitle().equals(name_category))){
                        FilterAnimal.click_animal_list_animal.add(new CategoryAnimals(name_category));
                        FilterAnimal.click_filter_animal(1);
                    }
                    else if (type_recycler == 2 && FilterHelp.click_org_list_help.stream().noneMatch(c -> c.getTitle().equals(name_category))){
                        FilterHelp.click_org_list_help.add(new CategoryAnimals(name_category));
                        FilterHelp.click_filter_animal();
                    }
                    else if (type_recycler == 3 && FilterAnimal.click_org_list_animal.stream().noneMatch(c -> c.getTitle().equals(name_category))){
                        FilterAnimal.click_org_list_animal.add(new CategoryAnimals(name_category));
                        FilterAnimal.click_filter_animal(3);
                    }
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
        else{
            holder.name_filter.setText(categoryAnimalsList.get(position).getTitle());

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    String category_name = categoryAnimalsList.get(position).getTitle();

                    switch (type_recycler){
                        case 1:
                            FilterAnimal.click_animal_list_animal.remove(category_name);
                            break;
                        case 2:
                            FilterHelp.click_org_list_help.remove(category_name);
                            break;
                        case 3:
                            FilterAnimal.click_org_list_animal.remove(category_name);
                            break;
                    }
                    categoryAnimalsList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categoryAnimalsList.size();
    }

    public static final class CategoryAnimalViewHolder extends RecyclerView.ViewHolder{

        TextView name, name_filter;
        CardView background;
        ImageView delete;


        public CategoryAnimalViewHolder(@NonNull View itemView) {
            super(itemView);

            delete = itemView.findViewById(R.id.delete_category_list);
            name_filter = itemView.findViewById(R.id.text_name_filter);
            name = itemView.findViewById(R.id.animal_name_category);
            background = itemView.findViewById(R.id.background_color);
        }
    }
}
