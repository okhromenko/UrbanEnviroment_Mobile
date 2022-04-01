package com.example.urbanenviroment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Animals;

import java.util.List;

public class AnimalPhotoDeleteOrgAdapter extends RecyclerView.Adapter<AnimalPhotoDeleteOrgAdapter.AnimalPhotoDeleteOrgViewHolder> {

    Context context;
    List<Animals> animalsList;

    public AnimalPhotoDeleteOrgAdapter(Context context, List<Animals> animalsList) {
        this.context = context;
        this.animalsList = animalsList;
    }

    @NonNull
    @Override
    public AnimalPhotoDeleteOrgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View animalItem = LayoutInflater.from(context).inflate(R.layout.photo_delete_org_item, parent, false);
        return new AnimalPhotoDeleteOrgAdapter.AnimalPhotoDeleteOrgViewHolder(animalItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalPhotoDeleteOrgViewHolder holder, int position) {
        int image_animal_id = context.getResources().getIdentifier(
                animalsList.get(position).getImg_animal(), "drawable", context.getPackageName());
        holder.img_photo_animal_delete.setImageResource(image_animal_id);
    }

    @Override
    public int getItemCount() {
        return animalsList.size();
    }

    public static final class AnimalPhotoDeleteOrgViewHolder extends RecyclerView.ViewHolder{

        ImageView img_photo_animal_delete;

        public AnimalPhotoDeleteOrgViewHolder(@NonNull View itemView) {
            super(itemView);

            img_photo_animal_delete = itemView.findViewById(R.id.img_photo_animal_delete);
        }
    }
}
