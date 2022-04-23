package com.example.urbanenviroment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanenviroment.page.animals.AnimalPage;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Animals;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimalsAdapter extends RecyclerView.Adapter<AnimalsAdapter.AnimalsViewHolder> {

    Context context;
    List<Animals> animalsList;

    public AnimalsAdapter(Context context, List<Animals> animalsList) {
        this.context = context;
        this.animalsList = animalsList;
    }

    @NonNull
    @Override
    public AnimalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View animalItem = LayoutInflater.from(context).inflate(R.layout.animal_item, parent, false);
        return new AnimalsAdapter.AnimalsViewHolder(animalItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(animalsList.get(position).getImg_animal()).into(holder.img_animal_home);

        Picasso.get().load(animalsList.get(position).getImg_org()).into(holder.img_org_home);

        holder.name_org_home.setText(animalsList.get(position).getName_org());
        holder.date_home.setText(animalsList.get(position).getReg_data());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnimalPage.class);

                intent.putExtra("kind_animal", animalsList.get(position).getKind());
                intent.putExtra("species_animal", animalsList.get(position).getSpecies());
                intent.putExtra("reg_date_animal", animalsList.get(position).getReg_data());
                intent.putExtra("name_animal", animalsList.get(position).getName_animal());
                intent.putExtra("description_animal", animalsList.get(position).getDescription());
                intent.putExtra("sex_animal", animalsList.get(position).getSex());
                intent.putExtra("age_animal", animalsList.get(position).getAge());
                intent.putExtra("state_animal", animalsList.get(position).getState());
                intent.putExtra("image_animal", animalsList.get(position).getImg_animal());
                intent.putExtra("address", animalsList.get(position).getAddress());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalsList.size();
    }

    public static final class AnimalsViewHolder extends RecyclerView.ViewHolder{

        ImageView img_animal_home, img_org_home;
        TextView date_home, name_org_home;

        public AnimalsViewHolder(@NonNull View itemView) {
            super(itemView);

            img_animal_home = itemView.findViewById(R.id.img_animal_home);
            img_org_home = itemView.findViewById(R.id.img_org_home);
            date_home = itemView.findViewById(R.id.date_home);
            name_org_home = itemView.findViewById(R.id.name_org_home);
        }
    }

}
