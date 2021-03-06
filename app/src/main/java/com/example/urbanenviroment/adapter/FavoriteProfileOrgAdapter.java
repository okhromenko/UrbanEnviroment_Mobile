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
import com.example.urbanenviroment.model.Organizations;
import com.example.urbanenviroment.page.org.OrganizationsPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class FavoriteProfileOrgAdapter extends RecyclerView.Adapter<FavoriteProfileOrgAdapter.FavoriteProfileOrgViewHolder> {

    private DocumentSnapshot[] currentDocument;
    Context context;
    List<Organizations> organizationsList;
    Boolean is_org;

    public FavoriteProfileOrgAdapter(Context context, List<Organizations> helpList) {
        this.context = context;
        this.organizationsList = helpList;
    }

    @NonNull
    @Override
    public FavoriteProfileOrgAdapter.FavoriteProfileOrgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orgItem = LayoutInflater.from(context).inflate(R.layout.organization_item, parent, false);
        return new FavoriteProfileOrgAdapter.FavoriteProfileOrgViewHolder(orgItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteProfileOrgAdapter.FavoriteProfileOrgViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.button_favorite_org.setImageResource(R.drawable.button_favorite_press);

        Picasso.get().load(organizationsList.get(position).getImg_org()).into(holder.img_org_org);

        holder.name_org_org.setText(organizationsList.get(position).getName_org());

        if (organizationsList.get(position).getAddress().equals("??????????") || organizationsList.get(position).getAddress().isEmpty())
            holder.address_org.setText("?????????? ???? ????????????");
        else
            holder.address_org.setText(organizationsList.get(position).getAddress());

        if (organizationsList.get(position).getDescription() != null && !organizationsList.get(position).getDescription().isEmpty()) {
            String str = organizationsList.get(position).getDescription();
            if (str.length() > 200) {
                str = str.substring(0, 200) + "...";
                holder.description_org.setText(str);
            } else {
                holder.description_org.setText(organizationsList.get(position).getDescription());
            }
        }

        holder.count_animal_org.setText(organizationsList.get(position).getCount_animal());
        holder.count_ads_org.setText(organizationsList.get(position).getCount_ads());
        holder.count_photo_org.setText(organizationsList.get(position).getCount_photo());
        holder.date_org.setText(organizationsList.get(position).getDate());

        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (mAuth != null) {
            currentDocument = new DocumentSnapshot[organizationsList.size()];

            DocumentReference docRef = db.collection("User").document(mAuth.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    is_org = document.getBoolean("is_org");

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, OrganizationsPage.class);
                            intent.putExtra("id", organizationsList.get(position).getId());
                            intent.putExtra("name", organizationsList.get(position).getName_org());
                            intent.putExtra("image", organizationsList.get(position).getImg_org());
                            intent.putExtra("address", organizationsList.get(position).getAddress());
                            intent.putExtra("email", organizationsList.get(position).getEmail());
                            intent.putExtra("phone", organizationsList.get(position).getPhone());
                            intent.putExtra("description", organizationsList.get(position).getDescription());
                            intent.putExtra("requisits", organizationsList.get(position).getRequisits());
                            intent.putExtra("count_animal", organizationsList.get(position).getCount_animal());
                            intent.putExtra("count_ads", organizationsList.get(position).getCount_ads());
                            intent.putExtra("count_photo", organizationsList.get(position).getCount_photo());
                            intent.putExtra("date", organizationsList.get(position).getDate());
                            intent.putExtra("website", organizationsList.get(position).getWebsite());
                            intent.putExtra("is_org", is_org);
                            context.startActivity(intent);
                        }
                    });
                }
            });
        }



        if (mAuth != null) {
            currentDocument = new DocumentSnapshot[organizationsList.size()];

            holder.button_favorite_org.setVisibility(View.VISIBLE);

            holder.button_favorite_org.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    db.collection("FavoriteOrg").document(organizationsList.get(position).getId()).delete();
                    organizationsList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return organizationsList.size();
    }

    public static final class FavoriteProfileOrgViewHolder extends RecyclerView.ViewHolder {

        ImageButton button_favorite_org;
        ImageView img_org_org;
        TextView name_org_org, address_org, description_org, count_animal_org, count_ads_org,
                count_photo_org, date_org;

        public FavoriteProfileOrgViewHolder(@NonNull View itemView) {
            super(itemView);

            button_favorite_org = itemView.findViewById(R.id.button_favorite_org);
            img_org_org = itemView.findViewById(R.id.img_org_org);
            name_org_org = itemView.findViewById(R.id.name_org_org);
            address_org = itemView.findViewById(R.id.address_org_org);
            description_org = itemView.findViewById(R.id.description_org);
            count_animal_org = itemView.findViewById(R.id.count_animal_org);
            count_ads_org = itemView.findViewById(R.id.count_ads_org);
            count_photo_org = itemView.findViewById(R.id.count_photo_org);
            date_org = itemView.findViewById(R.id.date_org);
        }
    }
}