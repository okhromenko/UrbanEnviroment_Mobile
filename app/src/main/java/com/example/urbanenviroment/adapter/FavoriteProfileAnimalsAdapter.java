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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteProfileAnimalsAdapter extends RecyclerView.Adapter<FavoriteProfileAnimalsAdapter.FavoritesProfileAnimalsViewHolder> {

    Context context;
    List<Animals> FavoriteAnimalList;
    private DocumentSnapshot currentDocument;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    boolean is_org;

    public FavoriteProfileAnimalsAdapter(Context context, List<Animals> animalsList) {
        this.context = context;
        this.FavoriteAnimalList = animalsList;
    }

    @NonNull
    @Override
    public FavoritesProfileAnimalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardsItem = LayoutInflater.from(context).inflate(R.layout.favorites_profile_animals_item, parent, false);
        return new FavoriteProfileAnimalsAdapter.FavoritesProfileAnimalsViewHolder(cardsItem);
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
                intent.putExtra("org", FavoriteAnimalList.get(position).getName_org());

                context.startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {

            db.collection("User").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    if ((Boolean) Boolean.TRUE.equals(document.getBoolean("is_org"))) {
                        holder.button_favorite_profile_animal.setVisibility(View.GONE);
                        is_org = true;
                    } else {
                        holder.button_favorite_profile_animal.setVisibility(View.VISIBLE);
                        is_org = false;
                    }
                }
            });

            holder.button_favorite_profile_animal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.collection("FavoriteAnimal").document(FavoriteAnimalList.get(position).getId()).delete();
                    FavoriteAnimalList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
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
