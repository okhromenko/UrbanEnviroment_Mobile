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

import com.example.urbanenviroment.model.Collection;
import com.example.urbanenviroment.page.animals.AnimalPage;
import com.example.urbanenviroment.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAnimalsAdapter extends RecyclerView.Adapter<PhotoAnimalsAdapter.AnimalsViewHolder> {

    Context context;
    List<Collection> collectionList;
    boolean is_org;

    public PhotoAnimalsAdapter(Context context, List<Collection> collectionList) {
        this.context = context;
        this.collectionList = collectionList;
    }

    @NonNull
    @Override
    public AnimalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View animalItem = LayoutInflater.from(context).inflate(R.layout.animal_item, parent, false);
        return new PhotoAnimalsAdapter.AnimalsViewHolder(animalItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            db.collection("User").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    if ((Boolean) Boolean.TRUE.equals(document.getBoolean("is_org")))
                        is_org = true;
                    else
                        is_org = false;
                }
            });
        }

        Picasso.get().load(collectionList.get(position).getImg_collection()).into(holder.img_animal_home);
        Picasso.get().load(collectionList.get(position).getImg_org()).into(holder.img_org_home);

        holder.name_org_home.setText(collectionList.get(position).getName_org());
        holder.date_home.setText(collectionList.get(position).getReg_date());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnimalPage.class);

                intent.putExtra("id_animal", collectionList.get(position).getId_animal());
                intent.putExtra("kind_animal", collectionList.get(position).getKind());
                intent.putExtra("species_animal", collectionList.get(position).getSpecies());
                intent.putExtra("reg_date_animal", collectionList.get(position).getReg_date_animal());
                intent.putExtra("name_animal", collectionList.get(position).getName_animal());
                intent.putExtra("description_animal", collectionList.get(position).getDescription());
                intent.putExtra("sex_animal", collectionList.get(position).getSex());
                intent.putExtra("age_animal", collectionList.get(position).getAge());
                intent.putExtra("state_animal", collectionList.get(position).getState());
                intent.putExtra("image_animal", collectionList.get(position).getImg_animal());
                intent.putExtra("org", collectionList.get(position).getName_org());
                intent.putExtra("is_org", is_org);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
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
