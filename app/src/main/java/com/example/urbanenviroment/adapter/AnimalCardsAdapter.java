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
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimalCardsAdapter extends RecyclerView.Adapter<AnimalCardsAdapter.AnimalCardsViewHolder> {

    boolean flag_favorite = false;

    Context context;
    List<Animals> animalCardsList;

    public AnimalCardsAdapter(Context context, List<Animals> animalCardsList) {
        this.context = context;
        this.animalCardsList = animalCardsList;
    }

    @NonNull
    @Override
    public AnimalCardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardsItem = LayoutInflater.from(context).inflate(R.layout.animal_cards_item, parent, false);
        return new AnimalCardsAdapter.AnimalCardsViewHolder(cardsItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalCardsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Picasso.get().load(animalCardsList.get(position).getImg_animal()).into(holder.img_animal_cards);

        holder.name_org_cards.setText(animalCardsList.get(position).getName_org());
        holder.kind_cards.setText(animalCardsList.get(position).getKind());
        holder.name_animal_cards.setText(animalCardsList.get(position).getName_animal());
        holder.description_animal_cards.setText(animalCardsList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnimalPage.class);

                intent.putExtra("kind_animal", animalCardsList.get(position).getKind());
                intent.putExtra("species_animal", animalCardsList.get(position).getSpecies());
                intent.putExtra("reg_date_animal", animalCardsList.get(position).getReg_data());
                intent.putExtra("name_animal", animalCardsList.get(position).getName_animal());
                intent.putExtra("description_animal", animalCardsList.get(position).getDescription());
                intent.putExtra("sex_animal", animalCardsList.get(position).getSex());
                intent.putExtra("age_animal", animalCardsList.get(position).getAge());
                intent.putExtra("state_animal", animalCardsList.get(position).getState());
                intent.putExtra("image_animal", animalCardsList.get(position).getImg_animal());

                context.startActivity(intent);
            }
        });

        holder.button_favorite_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_favorite){
                    holder.button_favorite_card.setImageResource(R.drawable.button_favorite);
                    flag_favorite = false;
                }
                else{
                    holder.button_favorite_card.setImageResource(R.drawable.button_favorite_press);
                    flag_favorite = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalCardsList.size();
    }

    public static final class AnimalCardsViewHolder extends RecyclerView.ViewHolder{

        ImageButton button_favorite_card;
        ImageView img_animal_cards;
        TextView name_org_cards, kind_cards, name_animal_cards, description_animal_cards;

        public AnimalCardsViewHolder(@NonNull View itemView) {
            super(itemView);

            button_favorite_card = itemView.findViewById(R.id.button_favorite_card);
            img_animal_cards = itemView.findViewById(R.id.img_animal_cards);
            name_org_cards = itemView.findViewById(R.id.name_org_cards);
            kind_cards = itemView.findViewById(R.id.kind_cards);
            name_animal_cards = itemView.findViewById(R.id.name_animal_cards);
            description_animal_cards = itemView.findViewById(R.id.description_animal_cards);
        }
    }

}
