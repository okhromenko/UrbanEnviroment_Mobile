package com.example.urbanenviroment.notification;

import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;

public class Notification extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }
}
