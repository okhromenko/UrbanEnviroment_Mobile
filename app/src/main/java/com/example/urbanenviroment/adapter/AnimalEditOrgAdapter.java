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

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.profile.org.EditAnimalPage;

import java.util.List;

public class AnimalEditOrgAdapter extends RecyclerView.Adapter<AnimalEditOrgAdapter.AnimalEditOrgViewHolder> {

    Context context;
    List<Animals> animalsList;

    public AnimalEditOrgAdapter(Context context, List<Animals> animalsList) {
        this.context = context;
        this.animalsList = animalsList;
    }

    @NonNull
    @Override
    public AnimalEditOrgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View animalItem = LayoutInflater.from(context).inflate(R.layout.animal_edit_item, parent, false);
        return new AnimalEditOrgAdapter.AnimalEditOrgViewHolder(animalItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalEditOrgViewHolder holder, int position) {
        int image_animal_id = context.getResources().getIdentifier(
                animalsList.get(position).getImg_animal(), "drawable", context.getPackageName());
        holder.img_animal_edit.setImageResource(image_animal_id);


        holder.kind_animal_edit.setText(animalsList.get(position).getKind());
        holder.name_animal_edit.setText(animalsList.get(position).getName_animal());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditAnimalPage.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalsList.size();
    }


    public static final class AnimalEditOrgViewHolder extends RecyclerView.ViewHolder{

        ImageView img_animal_edit;
        TextView kind_animal_edit, name_animal_edit;

        public AnimalEditOrgViewHolder(@NonNull View itemView) {
            super(itemView);
            img_animal_edit = itemView.findViewById(R.id.img_animal_edit);
            kind_animal_edit = itemView.findViewById(R.id.kind_animal_edit);
            name_animal_edit = itemView.findViewById(R.id.name_animal_edit);
        }
    }
}
