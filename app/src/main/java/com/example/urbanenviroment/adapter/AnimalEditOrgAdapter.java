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

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.animals.AnimalPage;
import com.example.urbanenviroment.page.profile.org.DeletePhoto;
import com.example.urbanenviroment.page.profile.org.EditAnimal;
import com.example.urbanenviroment.page.profile.org.EditAnimalPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull AnimalEditOrgViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(animalsList.get(position).getImg_animal()).into(holder.img_animal_edit);

        holder.kind_animal_edit.setText(animalsList.get(position).getKind());
        holder.name_animal_edit.setText(animalsList.get(position).getName_animal());

        holder.button_setting_edit_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditAnimalPage.class);

                intent.putExtra("id", animalsList.get(position).getId());
                intent.putExtra("kind_animal", animalsList.get(position).getKind());
                intent.putExtra("species_animal", animalsList.get(position).getSpecies());
                intent.putExtra("reg_date_animal", animalsList.get(position).getReg_data());
                intent.putExtra("name_animal", animalsList.get(position).getName_animal());
                intent.putExtra("description_animal", animalsList.get(position).getDescription());
                intent.putExtra("sex_animal", animalsList.get(position).getSex());
                intent.putExtra("age_animal", animalsList.get(position).getAge());
                intent.putExtra("state_animal", animalsList.get(position).getState());
                intent.putExtra("image_animal", animalsList.get(position).getImg_animal());

                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnimalPage.class);

                intent.putExtra("id", animalsList.get(position).getId());
                intent.putExtra("is_org", true);
                intent.putExtra("kind_animal", animalsList.get(position).getKind());
                intent.putExtra("species_animal", animalsList.get(position).getSpecies());
                intent.putExtra("reg_date_animal", animalsList.get(position).getReg_data());
                intent.putExtra("name_animal", animalsList.get(position).getName_animal());
                intent.putExtra("description_animal", animalsList.get(position).getDescription());
                intent.putExtra("sex_animal", animalsList.get(position).getSex());
                intent.putExtra("age_animal", animalsList.get(position).getAge());
                intent.putExtra("state_animal", animalsList.get(position).getState());
                intent.putExtra("image_animal", animalsList.get(position).getImg_animal());

                context.startActivity(intent);
            }
        });

        holder.button_delete_edit_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                db.collection("Animal").document(animalsList.get(position).getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        storageRef.getStorage().getReferenceFromUrl(document.getString("image")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                db.collection("Animal").document(document.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Intent intent = new Intent(context, EditAnimal.class);
                                        context.startActivity(intent);
                                    }
                                });
                            }
                        });
                    }
                });
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
        ImageButton button_setting_edit_animal, button_delete_edit_animal;

        public AnimalEditOrgViewHolder(@NonNull View itemView) {
            super(itemView);
            button_delete_edit_animal = itemView.findViewById(R.id.button_delete_edit_animal);
            button_setting_edit_animal = itemView.findViewById(R.id.button_setting_edit_animal);
            img_animal_edit = itemView.findViewById(R.id.img_animal_edit);
            kind_animal_edit = itemView.findViewById(R.id.kind_animal_edit);
            name_animal_edit = itemView.findViewById(R.id.name_animal_edit);
        }
    }
}
