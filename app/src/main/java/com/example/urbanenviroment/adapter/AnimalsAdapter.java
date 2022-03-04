package com.example.urbanenviroment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Animals;

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
    public void onBindViewHolder(@NonNull AnimalsViewHolder holder, int position) {
        int image_animal_id = context.getResources().getIdentifier(
                animalsList.get(position).getImg_animal(), "drawable", context.getPackageName());
        holder.img_animal_home.setImageResource(image_animal_id);

        int img_org_home_id = context.getResources().getIdentifier(
                animalsList.get(position).getImg_org(), "drawable", context.getPackageName());
        holder.img_org_home.setImageResource(img_org_home_id);

        holder.name_org_home.setText(animalsList.get(position).getName_org());
        holder.date_home.setText(animalsList.get(position).getDate());

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
