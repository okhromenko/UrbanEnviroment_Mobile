package com.example.urbanenviroment.adapter;

import static com.parse.Parse.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Collection;
import com.example.urbanenviroment.page.profile.org.DeletePhoto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CollectionImageAnimalPageAdapter extends RecyclerView.Adapter<CollectionImageAnimalPageAdapter.CollectionImageAnimalPageViewHolder> {

    public interface OnImageClickListener{
        void onImageClick(String position);
    }

    private final OnImageClickListener onImageClickListener;
    Context context;
    List<Collection> collectionList;
    String image;


    public CollectionImageAnimalPageAdapter(Context context, List<Collection> collectionList, OnImageClickListener onImageClickListener, String image) {
        this.context = context;
        this.collectionList = collectionList;
        this.onImageClickListener = onImageClickListener;
        this.image = image;
    }


    @NonNull
    @Override
    public CollectionImageAnimalPageAdapter.CollectionImageAnimalPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View animalItem = LayoutInflater.from(context).inflate(R.layout.photo_delete_org_item, parent, false);
        return new CollectionImageAnimalPageAdapter.CollectionImageAnimalPageViewHolder(animalItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionImageAnimalPageAdapter.CollectionImageAnimalPageViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Collection collection = collectionList.get(position);

        Picasso.get().load(collectionList.get(position).getImg_collection()).into(holder.img_photo_animal);
        holder.btn_delete_collection.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bucket = collectionList.get(position).getImg_collection();
                Picasso.get().load(image).into(holder.img_photo_animal);

                onImageClickListener.onImageClick(bucket);

                collectionList.get(position).setImg_collection(image);
                image = bucket;
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public static final class CollectionImageAnimalPageViewHolder extends RecyclerView.ViewHolder{

        ImageView img_photo_animal, btn_delete_collection;

        public CollectionImageAnimalPageViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_delete_collection = itemView.findViewById(R.id.btn_delete_collection);
            img_photo_animal = itemView.findViewById(R.id.img_photo_animal_delete);
        }
    }
}
