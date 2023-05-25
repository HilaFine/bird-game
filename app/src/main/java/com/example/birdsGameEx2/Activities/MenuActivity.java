package com.example.birdsGameEx2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.birdsGameEx2.Models.Game;
import com.example.ex1.R;
import com.google.android.material.button.MaterialButton;

public class MenuActivity extends AppCompatActivity {
    public static final int SLOW = 0;
    public static final int FAST = 1;
    public static final int SENSORS = 2;
    public static final int BUTTONS = 3;

    private MaterialButton menuBTN_slow;
    private MaterialButton menuBTN_fast;
    private MaterialButton menuBTN_ButtonsMode;
    private MaterialButton menuBTN_SensorsMode;
    private MaterialButton menuBTN_start;
    private MaterialButton menuBTN_goToRecords;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        setButtons();
    }

    private void setButtons() {
        Game game = new Game();
        Intent intent = new Intent(this, MainActivity.class);
        menuBTN_slow.setOnClickListener(v -> {
            game.setSpeed(SLOW);
        });
        menuBTN_fast.setOnClickListener(v -> {
            game.setSpeed(FAST);
        });
        menuBTN_SensorsMode.setOnClickListener(v -> {
            game.setMode(SENSORS);
        });
        menuBTN_ButtonsMode.setOnClickListener(v -> {
            game.setMode(BUTTONS);
        });
        menuBTN_start.setOnClickListener(v -> {

            intent.putExtra("speed", game.getSpeed());
            //intent.putExtra("mode", game.getMode());
            startActivity(intent);
            finish();
        });

    }

    private void findViews() {
        menuBTN_slow = findViewById(R.id.MENU_BTN_Slow);
        menuBTN_fast = findViewById(R.id.MENU_BTN_Fast);
        menuBTN_ButtonsMode = findViewById(R.id.MENU_BTN_BUTTONS);
        menuBTN_SensorsMode = findViewById(R.id.MENU_BTN_SENSORS);
        menuBTN_start = findViewById(R.id.MENU_BTN_START);
        menuBTN_goToRecords = findViewById(R.id.MENU_BTN_GoToRecords);

    }


}
