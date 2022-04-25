package com.example.urbanenviroment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanenviroment.page.animals.AnimalPage;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.animals.CardsMainActivity;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull FavoritesProfileAnimalsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Picasso.get().load(FavoriteAnimalList.get(position).getImg_animal()).into(holder.img_animal);

        holder.name_org.setText(FavoriteAnimalList.get(position).getName_org());
        holder.name_animal.setText(FavoriteAnimalList.get(position).getName_animal());
        holder.king_animal.setText(FavoriteAnimalList.get(position).getKind());
        holder.age_animal.setText(FavoriteAnimalList.get(position).getAge());
        holder.sex_animal.setText(FavoriteAnimalList.get(position).getSex());
        holder.state_animal.setText(FavoriteAnimalList.get(position).getState());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteAnimal");

        ParseUser parseUser = ParseUser.getCurrentUser();
        ParseObject id_animal = ParseObject.createWithoutData("Animals", FavoriteAnimalList.get(position).getId());

        query.whereEqualTo("id_animal", id_animal);
        query.whereEqualTo("id_user", parseUser);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null)
                    holder.button_favorite_profile_animal.setImageResource(R.drawable.button_favorite_press);
                else
                    holder.button_favorite_profile_animal.setImageResource(R.drawable.button_favorite);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnimalPage.class);

                intent.putExtra("id", FavoriteAnimalList.get(position).getId());
                intent.putExtra("kind_animal", FavoriteAnimalList.get(position).getKind());
                intent.putExtra("species_animal", FavoriteAnimalList.get(position).getSpecies());
                intent.putExtra("reg_date_animal", FavoriteAnimalList.get(position).getReg_data());
                intent.putExtra("name_animal", FavoriteAnimalList.get(position).getName_animal());
                intent.putExtra("description_animal", FavoriteAnimalList.get(position).getDescription());
                intent.putExtra("sex_animal", FavoriteAnimalList.get(position).getSex());
                intent.putExtra("age_animal", FavoriteAnimalList.get(position).getAge());
                intent.putExtra("state_animal", FavoriteAnimalList.get(position).getState());
                intent.putExtra("image_animal", FavoriteAnimalList.get(position).getImg_animal());
                intent.putExtra("address", FavoriteAnimalList.get(position).getAddress());

                context.startActivity(intent);
            }
        });

        holder.button_favorite_profile_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteAnimal");

                ParseUser parseUser = ParseUser.getCurrentUser();
                ParseObject id_animal = ParseObject.createWithoutData("Animals", FavoriteAnimalList.get(position).getId());

                query.whereEqualTo("id_animal", id_animal);
                query.whereEqualTo("id_user", parseUser);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object == null){
                            holder.button_favorite_profile_animal.setImageResource(R.drawable.button_favorite_press);

                            ParseObject favorite_animal = new ParseObject("FavoriteAnimal");
                            favorite_animal.put("id_user", parseUser);
                            favorite_animal.put("id_animal", id_animal);

                            favorite_animal.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null){
                                        Intent intent = new Intent(context, CardsMainActivity.class);
                                        context.startActivity(intent);
                                    }
                                }
                            });
                        }
                        else {
                            holder.button_favorite_profile_animal.setImageResource(R.drawable.button_favorite);
                            object.deleteInBackground();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return FavoriteAnimalList.size();
    }

    public static final class FavoritesProfileAnimalsViewHolder extends RecyclerView.ViewHolder{

        ImageButton button_favorite_profile_animal;
        ImageView img_animal;
        TextView name_org, name_animal, king_animal, age_animal, sex_animal, state_animal;

        public FavoritesProfileAnimalsViewHolder(@NonNull View itemView) {
            super(itemView);

            button_favorite_profile_animal = itemView.findViewById(R.id.button_favorite_profile_animal);
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
