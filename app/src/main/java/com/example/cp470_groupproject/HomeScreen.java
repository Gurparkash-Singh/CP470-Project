package com.example.cp470_groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeScreen extends AppCompatActivity {

    private ImageButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button Watchlist = findViewById(R.id.watchlist_home);
        Watchlist.setOnClickListener(v -> {
            /*@Override
            public void onClick(View view) {
                Intent intent = new Intent(this, this); //LINK BUTTONS HERE
                startActivity(intent);
            }*/
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        });
        Button Group = findViewById(R.id.groups_home);
        Group.setOnClickListener(v -> {
            /*@Override
            public void onClick(View view) {
                Intent intent = new Intent(this, this); //LINK BUTTONS HERE
                startActivity(intent);
            }*/
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        });
        ImageButton Home = findViewById(R.id.home_btn);
        Home.setOnClickListener(v -> {
            /*@Override
            public void onClick(View view) {
                Intent intent = new Intent(this, this); //LINK BUTTONS HERE
                startActivity(intent);
            }*/
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        });
        ImageButton Search = findViewById(R.id.search_btn);
        Search.setOnClickListener(v -> {
            /*@Override
            public void onClick(View view) {
                Intent intent = new Intent(this, this); //LINK BUTTONS HERE
                startActivity(intent);
            }*/
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        });
        ImageButton Social = findViewById(R.id.social_btn);
        Social.setOnClickListener(v -> {
            /*@Override
            public void onClick(View view) {
                Intent intent = new Intent(this, this); //LINK BUTTONS HERE
                startActivity(intent);
            }*/
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        });
        settingsButton = findViewById(R.id.settings_btn);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, SettingsScreen.class);
            startActivity(intent);
        });
    }
}