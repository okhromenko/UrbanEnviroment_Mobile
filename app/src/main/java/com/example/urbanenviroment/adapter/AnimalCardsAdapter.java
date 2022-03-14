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

import com.example.urbanenviroment.AmimalPage;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Animals;

import java.util.List;

public class AnimalCardsAdapter extends RecyclerView.Adapter<AnimalCardsAdapter.AnimalCardsViewHolder> {

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
    public void onBindViewHolder(@NonNull AnimalCardsViewHolder holder, int position) {
        int image_animal_cards_id = context.getResources().getIdentifier(
                animalCardsList.get(position).getImg_animal(), "drawable", context.getPackageName());
        holder.img_animal_cards.setImageResource(image_animal_cards_id);

        holder.name_org_cards.setText(animalCardsList.get(position).getName_org());
        holder.kind_cards.setText(animalCardsList.get(position).getKind());
        holder.name_animal_cards.setText(animalCardsList.get(position).getName_animal());
        holder.description_animal_cards.setText(animalCardsList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AmimalPage.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalCardsList.size();
    }

    public static final class AnimalCardsViewHolder extends RecyclerView.ViewHolder{

        ImageView img_animal_cards;
        TextView name_org_cards, kind_cards, name_animal_cards, description_animal_cards;

        public AnimalCardsViewHolder(@NonNull View itemView) {
            super(itemView);

            img_animal_cards = itemView.findViewById(R.id.img_animal_cards);
            name_org_cards = itemView.findViewById(R.id.name_org_cards);
            kind_cards = itemView.findViewById(R.id.kind_cards);
            name_animal_cards = itemView.findViewById(R.id.name_animal_cards);
            description_animal_cards = itemView.findViewById(R.id.description_animal_cards);
        }
    }

}
