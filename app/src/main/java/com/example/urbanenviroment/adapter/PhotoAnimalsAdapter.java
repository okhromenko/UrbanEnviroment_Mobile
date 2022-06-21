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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class PhotoAnimalsAdapter extends RecyclerView.Adapter<PhotoAnimalsAdapter.AnimalsViewHolder> {

    Context context;
    List<Collection> collectionList;

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

        Picasso.get().load(collectionList.get(position).getImg_collection()).into(holder.img_animal_home);
        Picasso.get().load(collectionList.get(position).getImg_org()).into(holder.img_org_home);

        holder.name_org_home.setText(collectionList.get(position).getName_org());
        holder.date_home.setText(collectionList.get(position).getReg_date());

        db.collection("Animal").document(collectionList.get(position).getId_animal()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AnimalPage.class);

                        if (mAuth != null && document.getString("userId").equals(mAuth.getUid()))
                            intent.putExtra("edit_del_buttons", true);

                        intent.putExtra("id_animal", collectionList.get(position).getId_animal());
                        intent.putExtra("name_animal", document.getString("name"));
                        intent.putExtra("state_animal", document.getString("state"));
                        intent.putExtra("species_animal", document.getString("species"));
                        intent.putExtra("description_animal", document.getString("description"));

                        intent.putExtra("age_animal", document.getString("age"));
                        intent.putExtra("sex_animal", document.getString("sex"));
                        intent.putExtra("image_animal", collectionList.get(position).getImg_collection());
                        intent.putExtra("main_animal", document.getString("image"));
                        intent.putExtra("photoPage", true);

                        String reg_date = new SimpleDateFormat("dd.MM.yyyy").format(document.getDate("date_reg"));
                        intent.putExtra("reg_date_animal", reg_date);

                        intent.putExtra("kind_animal", collectionList.get(position).getKind());
                        intent.putExtra("org", collectionList.get(position).getName_org());


                        context.startActivity(intent);
                    }
                });
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
