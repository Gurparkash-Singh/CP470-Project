package com.example.cp470_groupproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterScreen1 extends AppCompatActivity {

    private EditText registerEmail;
    private EditText registerUsername;
    private EditText registerPassword;
    private Button nextButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        registerEmail = findViewById(R.id.RegisterTextEmailAddress);
        registerUsername = findViewById(R.id.RegisterTextUsername);
        registerPassword = findViewById(R.id.RegisterTextPassword);
        nextButton = findViewById(R.id.NextButton);
        sharedPreferences = getSharedPreferences("LoginPreferences", MODE_PRIVATE);

        nextButton.setOnClickListener(v -> register1());
    }

    //store email/username/password locally/temporarily via sharedpreferences. will be brought to next registration screen. to be used for login info
    private void register1() {
        String email = registerEmail.getText().toString().trim();
        String username = registerUsername.getText().toString().trim();
        String password = registerPassword.getText().toString();
        //validate formatting
        if (TextUtils.isEmpty(email)) {registerEmail.setError("email empty"); return; }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { registerEmail.setError("invalid email format"); return; }
        if (TextUtils.isEmpty(username)) {registerUsername.setError("username empty"); return; }
        if (TextUtils.isEmpty(password)) {registerPassword.setError("password empty"); return; }
        //go to next screen and pass temp ID info
        Intent intent = new Intent(RegisterScreen1.this, RegisterScreen2.class);
        intent.putExtra("RegisteredEmail", email);
        intent.putExtra("RegisteredUsername", username);
        intent.putExtra("RegisteredPassword", password);
        startActivity(intent);
    }
}