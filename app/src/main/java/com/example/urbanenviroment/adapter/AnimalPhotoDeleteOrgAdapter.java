package com.example.urbanenviroment.adapter;

import static com.parse.Parse.getApplicationContext;

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
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimalPhotoDeleteOrgAdapter extends RecyclerView.Adapter<AnimalPhotoDeleteOrgAdapter.AnimalPhotoDeleteOrgViewHolder> {

    Context context;
    List<Animals> animalsList;
    boolean flag;

    public AnimalPhotoDeleteOrgAdapter(Context context, List<Animals> animalsList, boolean flag) {
        this.context = context;
        this.animalsList = animalsList;
        this.flag = flag;
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

        if (flag)
            holder.btn_delete_collection.setVisibility(View.GONE);

        else{
            holder.btn_delete_collection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Collection");

                    query.whereEqualTo("objectId", animalsList.get(position).getId());
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException ex) {
                            if (object != null){
                                object.deleteInBackground();

                                Intent intent = new Intent(context, DeletePhoto.class);
                                context.startActivity(intent);
                            }
                        }
                    });
                }
            });
        }

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
