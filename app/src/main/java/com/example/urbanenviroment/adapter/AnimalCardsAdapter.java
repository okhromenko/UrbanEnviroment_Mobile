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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanenviroment.page.animals.AnimalPage;
import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Animals;
import com.example.urbanenviroment.page.animals.CardsMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalCardsAdapter extends RecyclerView.Adapter<AnimalCardsAdapter.AnimalCardsViewHolder> {
    Context context;
    List<Animals> animalCardsList;
    private DocumentSnapshot[] currentDocument;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    boolean is_org;

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

        String str = animalCardsList.get(position).getDescription();
        if (str.length() > 200) {
            str = str.substring(0,200) + "...";
            holder.description_animal_cards.setText(str);
        } else {
            holder.description_animal_cards.setText(animalCardsList.get(position).getDescription());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnimalPage.class);

                intent.putExtra("id_animal", animalCardsList.get(position).getId());
                intent.putExtra("kind_animal", animalCardsList.get(position).getKind());
                intent.putExtra("species_animal", animalCardsList.get(position).getSpecies());
                intent.putExtra("reg_date_animal", animalCardsList.get(position).getReg_data());
                intent.putExtra("name_animal", animalCardsList.get(position).getName_animal());
                intent.putExtra("description_animal", animalCardsList.get(position).getDescription());
                intent.putExtra("sex_animal", animalCardsList.get(position).getSex());
                intent.putExtra("age_animal", animalCardsList.get(position).getAge());
                intent.putExtra("state_animal", animalCardsList.get(position).getState());
                intent.putExtra("image_animal", animalCardsList.get(position).getImg_animal());
                intent.putExtra("org", animalCardsList.get(position).getName_org());
                intent.putExtra("is_org", is_org);

                context.startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null){

            currentDocument = new DocumentSnapshot[animalCardsList.size()];

            db.collection("User").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    if ((Boolean) Boolean.TRUE.equals(document.getBoolean("is_org"))){
                        holder.button_favorite_card.setVisibility(View.GONE);
                        is_org = true;
                    }

                    else {
                        holder.button_favorite_card.setVisibility(View.VISIBLE);
                        is_org = false;
                    }
                }
            });


            db.collection("FavoriteAnimal").whereEqualTo("id_animal", animalCardsList.get(position).getId())
                    .whereEqualTo("userId", mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                QuerySnapshot document = task.getResult();
                                if (document.isEmpty()) {
                                    currentDocument[position] = null;
                                    holder.button_favorite_card.setImageResource(R.drawable.button_favorite);
                                }
                                else{
                                    currentDocument[position] = document.getDocuments().get(0);
                                    holder.button_favorite_card.setImageResource(R.drawable.button_favorite_press);
                                }
                            }
                        }
                    });



            holder.button_favorite_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentDocument[position] == null) {
                        HashMap<String, Object> favoriteAnimal = new HashMap<>();
                        favoriteAnimal.put("id", animalCardsList.get(position).getId());
                        favoriteAnimal.put("name", animalCardsList.get(position).getName_animal());
                        favoriteAnimal.put("state", animalCardsList.get(position).getState());
                        favoriteAnimal.put("species", animalCardsList.get(position).getSpecies());
                        favoriteAnimal.put("description",  animalCardsList.get(position).getDescription());
                        favoriteAnimal.put("age", animalCardsList.get(position).getAge());
                        favoriteAnimal.put("sex", animalCardsList.get(position).getSex());
                        favoriteAnimal.put("kind", animalCardsList.get(position).getKind());
                        favoriteAnimal.put("date_reg", animalCardsList.get(position).getReg_data());

                        favoriteAnimal.put("userId", mAuth.getCurrentUser().getUid());
                        favoriteAnimal.put("username", animalCardsList.get(position).getName_org());
                        favoriteAnimal.put("imageOrg", animalCardsList.get(position).getImg_org());
                        favoriteAnimal.put("image", animalCardsList.get(position).getImg_animal());


                        db.collection("FavoriteAnimal").document()
                                .set(favoriteAnimal).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @SuppressLint("NotifyDataSetChanged")
                                    @Override
                                    public void onSuccess(Void unused) {
                                        notifyDataSetChanged();
                                    }
                                });
                    } else {
                        DocumentReference changeRef = db.collection("FavoriteAnimal").document(currentDocument[position].getId());
                        changeRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onSuccess(Void unused) {
                                notifyDataSetChanged();
                            }
                        });
                    }
                }
            });
        }
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
