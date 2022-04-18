package com.example.urbanenviroment.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanenviroment.R;
import com.example.urbanenviroment.model.Notifications;
import com.example.urbanenviroment.page.help.HelpPage;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    Context context;
    List<Notifications> notificationsList;

    public NotificationsAdapter(Context context, List<Notifications> notificationsList){
        this.context = context;
        this.notificationsList = notificationsList;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View notificationsItem = LayoutInflater.from(context).inflate(R.layout.notifications_item, parent, false);
        return new NotificationsAdapter.NotificationsViewHolder(notificationsItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {

        String  name_org, type, other_info, fill_1, fill_2, fill_3;

        int img_org_notifications_id = context.getResources().getIdentifier(
                notificationsList.get(position).getImg_org(), "drawable", context.getPackageName());
        holder.img_org_notifications.setImageResource(img_org_notifications_id);
        //holder.name_org_notifications.setText(notificationsList.get(position).getName_org());
        //holder.type_notifications.setText(notificationsList.get(position).getType_notifications());

        name_org = notificationsList.get(position).getName_org();
        type = notificationsList.get(position).getType_notifications();
        other_info = notificationsList.get(position).getOther_info();
        fill_1 ="Организация ";
        fill_2 =" добавила новое ";
        fill_3 =": ";

        Spannable text = new SpannableString( fill_1 + name_org + fill_2 + type + fill_3 + other_info);

        text.setSpan(new StyleSpan(Typeface.BOLD), fill_1.length(), (fill_1.length() + name_org.length()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new StyleSpan(Typeface.BOLD), (fill_1.length() + name_org.length() + fill_2.length()), (fill_1.length() + name_org.length() + fill_2.length() + type.length()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setSpan(new StyleSpan(Typeface.BOLD), (fill_1.length() + name_org.length() + fill_2.length() + type.length() + fill_3.length()), (fill_1.length() + name_org.length() + fill_2.length() + type.length() + fill_3.length() + other_info.length()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.text_notifications.setText(text);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HelpPage.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public static final class NotificationsViewHolder extends RecyclerView.ViewHolder{

        ImageView img_org_notifications;
        //TextView name_org_notifications, type_notifications;
        TextView text_notifications;


        public NotificationsViewHolder(@NonNull View itemView) {
            super(itemView);

            img_org_notifications = itemView.findViewById(R.id.img_org_notifications);
            //name_org_notifications = itemView.findViewById(R.id.name_org_notifications);
            //type_notifications = itemView.findViewById(R.id.type_notifications);
            text_notifications = itemView.findViewById(R.id.text_notification);
        }
    }

}
