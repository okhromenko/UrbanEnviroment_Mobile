package com.example.urbanenviroment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.animals.AnimalPage;
import com.example.urbanenviroment.page.animals.CardsMainActivity;
import com.example.urbanenviroment.page.profile.org.DeletePhoto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull AnimalPhotoDeleteOrgViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(animalsList.get(position).getImg_animal()).into(holder.img_photo_animal_delete);

        holder.btn_delete_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

                db.collection("User").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document_user = task.getResult();
                        db.collection("User").document(mAuth.getUid()).update("count_photo",
                                document_user.getLong("count_photo") - 1);
                    }
                });

                db.collection("Collection").document(animalsList.get(position).getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent intent = new Intent(context, DeletePhoto.class);
                                context.startActivity(intent);
                            }
                        });
            }
        });
    }



    @Override
    public int getItemCount() {
        return animalsList.size();
    }

    public static final class AnimalPhotoDeleteOrgViewHolder extends RecyclerView.ViewHolder{

        ImageView img_photo_animal_delete, btn_delete_collection;

        public AnimalPhotoDeleteOrgViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_delete_collection = itemView.findViewById(R.id.btn_delete_collection);
            img_photo_animal_delete = itemView.findViewById(R.id.img_photo_animal_delete);
        }
    }
}
