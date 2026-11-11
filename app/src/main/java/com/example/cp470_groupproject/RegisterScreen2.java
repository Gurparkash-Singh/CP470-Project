package com.example.cp470_groupproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;
import java.util.Set;

public class RegisterScreen2 extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText ageNumber;
    private Button doneButton;
    private SharedPreferences sharedPreferences;
    private CheckBox[] genreCheckBoxes;
    private String email;
    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        firstName = findViewById(R.id.EnterFirstName);
        lastName = findViewById(R.id.EnterLastName);
        ageNumber = findViewById(R.id.EnterAgeNumber);
        doneButton = findViewById(R.id.DoneButton);
        genreCheckBoxes = new CheckBox[] {
                findViewById(R.id.ChBxAction),
                findViewById(R.id.ChBxAdventure),
                findViewById(R.id.ChBxAnimation),
                findViewById(R.id.ChBxAnime),
                findViewById(R.id.ChBxBiography),
                findViewById(R.id.ChBxCrime),
                findViewById(R.id.ChBxComedy),
                findViewById(R.id.ChBxDocumentary),
                findViewById(R.id.ChBxDrama),
                findViewById(R.id.ChBxFamily),
                findViewById(R.id.ChBxFantasy),
                findViewById(R.id.ChBxHistory),
                findViewById(R.id.ChBxHorror),
                findViewById(R.id.ChBxMusical),
                findViewById(R.id.ChBxMystery),
                findViewById(R.id.ChBxRomance),
                findViewById(R.id.ChBxSciFi),
                findViewById(R.id.ChBxSeasonal),
                findViewById(R.id.ChBxSport),
                findViewById(R.id.ChBxThriller)
        };
        //get temp ID info from Registerscreen1
        Intent intent = getIntent();
        email = intent.getStringExtra("RegisteredEmail");
        username = intent.getStringExtra("RegisteredUsername");
        password = intent.getStringExtra("RegisteredPassword");
        if (email == null || username == null || password == null
            || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "empty account info", Toast.LENGTH_SHORT).show();
            finish();return;}

        sharedPreferences = getSharedPreferences("LoginPreferences", MODE_PRIVATE);
        doneButton.setOnClickListener(v -> register2());
    }

    private void register2() {
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String ageNum = ageNumber.getText().toString().trim();
        //for now i arbitrarily decided only fName will be mandatory
        if (TextUtils.isEmpty(fName)) {
            firstName.setError("first name field mandatory"); return;
        }
        int age = 12;//default can change. esrb ratings E and E10 included but not others
        if (!TextUtils.isEmpty(ageNum)) {
            try {
                age = Integer.parseInt(ageNum);
                if (age <= 0 || age > 130) {
                    ageNumber.setError("invalid age number");return;}
            } catch (NumberFormatException e) {
                ageNumber.setError("invalid age");return;
            }
        }
        //get preferred genres to temp save ie sharedpreferences
        Set<String> selectedGenres = new HashSet<>();
        for (CheckBox checkBox : genreCheckBoxes) {
            if (checkBox.isChecked()) {
                selectedGenres.add(checkBox.getText().toString());
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("RegisteredEmail", email);
        editor.putString("RegisteredUsername", username);
        editor.putString("RegisteredPassword", password);
        editor.putString("RegisteredFirstName", fName);
        editor.putString("RegisteredLastName", lName);
        editor.putInt("RegisteredAge", age);
        editor.putStringSet("RegisteredPreferredGenres", selectedGenres);
        editor.apply();

        Toast.makeText(this, "full registration complete", Toast.LENGTH_SHORT).show();//final registration done message

        Intent intent = new Intent(RegisterScreen2.this, LoginScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);//reuse prev activity
        startActivity(intent);
        finish();//close activity when done
    }
}