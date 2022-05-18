package com.example.urbanenviroment.page;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.urbanenviroment.R;

public class Dialog_Search extends DialogFragment implements View.OnClickListener {

    final String LOG_TAG = "myLogs";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_search, null);
        v.findViewById(R.id.button_sort_dialog_decrease).setOnClickListener((View.OnClickListener) this);
        v.findViewById(R.id.button_sort_dialog_increasing).setOnClickListener((View.OnClickListener) this);
        v.findViewById(R.id.button_sort_dialog_count_animal).setOnClickListener((View.OnClickListener) this);
        v.findViewById(R.id.button_sort_dialog_count_ads).setOnClickListener((View.OnClickListener) this);
        v.findViewById(R.id.button_sort_dialog_count_photo).setOnClickListener((View.OnClickListener) this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "dialog_search: " + ((Button) v).getText());
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "dialog_search: onDismiss");
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "dialog_search: onCancel");
    }
}
