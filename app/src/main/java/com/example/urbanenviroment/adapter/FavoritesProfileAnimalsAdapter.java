package com.example.urbanenviroment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanenviroment.AnimalPage;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Animals;

import java.util.List;

public class FavoritesProfileAnimalsAdapter extends RecyclerView.Adapter<FavoritesProfileAnimalsAdapter.FavoritesProfileAnimalsViewHolder> {

    Context context;
    List<Animals> FavoriteAnimalList;

    public FavoritesProfileAnimalsAdapter(Context context, List<Animals> animalsList) {
        this.context = context;
        this.FavoriteAnimalList = animalsList;
    }

    @NonNull
    @Override
    public FavoritesProfileAnimalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardsItem = LayoutInflater.from(context).inflate(R.layout.favorites_profile_animals_item, parent, false);
        return new FavoritesProfileAnimalsAdapter.FavoritesProfileAnimalsViewHolder(cardsItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesProfileAnimalsViewHolder holder, int position) {
        int image_favorite_animals_id = context.getResources().getIdentifier(
                FavoriteAnimalList.get(position).getImg_animal(), "drawable", context.getPackageName());
        holder.img_animal.setImageResource(image_favorite_animals_id);

        holder.name_org.setText(FavoriteAnimalList.get(position).getName_org());
        holder.name_animal.setText(FavoriteAnimalList.get(position).getName_animal());
        holder.king_animal.setText(FavoriteAnimalList.get(position).getKind());
        holder.age_animal.setText(FavoriteAnimalList.get(position).getAge());
        holder.sex_animal.setText(FavoriteAnimalList.get(position).getSex());
        holder.state_animal.setText(FavoriteAnimalList.get(position).getState());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnimalPage.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return FavoriteAnimalList.size();
    }

    public static final class FavoritesProfileAnimalsViewHolder extends RecyclerView.ViewHolder{

        ImageView img_animal;
        TextView name_org, name_animal, king_animal, age_animal, sex_animal, state_animal;

        public FavoritesProfileAnimalsViewHolder(@NonNull View itemView) {
            super(itemView);

            img_animal = itemView.findViewById(R.id.favorites_animals_img_animal);
            name_org = itemView.findViewById(R.id.favorites_animals_name_org);
            name_animal = itemView.findViewById(R.id.favorites_animals_name_animal);
            king_animal = itemView.findViewById(R.id.favorites_animals_kind);
            age_animal = itemView.findViewById(R.id.favorite_animals_age);
            sex_animal = itemView.findViewById(R.id.favorite_animals_sex);
            state_animal = itemView.findViewById(R.id.favorite_animals_state);
        }
    }

}
