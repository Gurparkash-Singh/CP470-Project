package com.example.cp470_groupproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class LoginScreen extends AppCompatActivity {

    private EditText loginUserEmail;
    private EditText loginPassword;
    private Button loginButton;
    private Button registerButton;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loginUserEmail = findViewById(R.id.EnterUserEmail);
        loginPassword = findViewById(R.id.EnterPassword);
        loginButton = findViewById(R.id.LoginButton);
        sharedPreferences = getSharedPreferences("LoginPreferences", MODE_PRIVATE);

        String storedUserEmail = sharedPreferences.getString("DefaultUserEmail", "");
        loginUserEmail.setText(storedUserEmail);
        loginButton.setOnClickListener(v -> login());

        //register button
        registerButton = findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, RegisterScreen1.class);
            startActivity(intent);
        });
    }

    //called when login button clicked, switches to home screen
    private void login() {
        String userEmail = loginUserEmail.getText().toString().trim();
        String password = loginPassword.getText().toString();
        // user/email & password formatting
        if (TextUtils.isEmpty(userEmail)) {loginUserEmail.setError("empty username or email");return;}
        if (TextUtils.isEmpty(password.trim())) {loginPassword.setError("empty password");return;}
        //retrieve the stored temp register info
        String registeredEmail = sharedPreferences.getString("RegisteredEmail", "");
        String registeredUsername = sharedPreferences.getString("RegisteredUsername", "");
        String registeredPassword = sharedPreferences.getString("RegisteredPassword", "");
        Log.i("LoginScreen", "Registered Email: " + registeredEmail);
        Log.i("LoginScreen", "Registered Username: " + registeredUsername);
        Log.i("LoginScreen", "Password: " + registeredPassword);
        //check valid  temp email/user and password matches
        boolean validUserEmail =
                (!registeredEmail.isEmpty() && userEmail.equalsIgnoreCase(registeredEmail))
                || (!registeredUsername.isEmpty() && userEmail.equalsIgnoreCase(registeredUsername));
        boolean validPassword =
                !registeredPassword.isEmpty() && password.equals(registeredPassword);
        if (!validUserEmail || !validPassword) {
            Toast.makeText(this, "incorrect username/email or password", Toast.LENGTH_SHORT).show(); return;
        }
        //check if user/email & password combination matches a valid permanent ID tbd later
        //store username/email on SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DefaultUserEmail", userEmail);
        editor.apply();
        Log.i("LoginScreen", "email stored on SharedPreferences");
        Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LoginScreen", "onResume() called");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LoginScreen", "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LoginScreen", "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LoginScreen", "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LoginScreen", "onDestroy() called");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("LoginScreen", "onSaveInstanceState() called");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("LoginScreen", "onRestoreInstanceState() called");
    }
}