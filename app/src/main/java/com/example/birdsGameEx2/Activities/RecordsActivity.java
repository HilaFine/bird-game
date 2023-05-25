package com.example.birdsGameEx2.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.birdsGameEx2.Fragments.ListFragment;
import com.example.birdsGameEx2.Fragments.MapFragment;
import com.example.ex1.R;

public class RecordsActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;
    private TextView info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        listFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.records_list_fragment,listFragment).commit();

    }
}
