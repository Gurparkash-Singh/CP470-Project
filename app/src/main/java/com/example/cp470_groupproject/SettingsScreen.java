package com.example.cp470_groupproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class SettingsScreen extends AppCompatActivity {

    private TextView settingsEmail;
    private TextView settingsUsername;
    private TextView settingsPassword;
    private SharedPreferences sharedPreferences; //temp acc info
    private Button updateAccButton;
    private Button aboutButton;
    private Button updateNameButton;
    private Button updateAgeButton;
    private Button settingsHomeButton;
    private TextView settingsFName;
    private TextView settingsLName;
    private TextView settingsAgeNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //textviews
        settingsEmail = findViewById(R.id.AccInfoEmail);
        settingsUsername = findViewById(R.id.AccInfoUsername);
        settingsPassword = findViewById(R.id.AccInfoPassword);
        settingsFName = findViewById(R.id.FirstNameText);
        settingsLName = findViewById(R.id.LastNameText);
        settingsAgeNum = findViewById(R.id.SettingsAgeNum);
        //buttons
        updateAccButton = findViewById(R.id.UpdateAccButton);
        aboutButton = findViewById(R.id.AboutButton);
        updateNameButton = findViewById(R.id.UpdateNameButton);
        updateAgeButton = findViewById(R.id.UpdateAgeButton);
        //home button
        settingsHomeButton = findViewById(R.id.SettingsHomeButton);
        //get temp stored acc info and display
        sharedPreferences = getSharedPreferences("LoginPreferences", MODE_PRIVATE);
        displayAccInfo();
        displayProfileInfo();
        //about button functionality (uses strings.xml "about_app" string)
        aboutButton.setOnClickListener(v -> {
            Toast.makeText(this, R.string.about_app, Toast.LENGTH_LONG).show();
        });
        // button functionality (with temp stored values from sharedPreferences)
        updateAccButton.setOnClickListener(v -> updateAccount());
        updateNameButton.setOnClickListener(v -> updateName());
        updateAgeButton.setOnClickListener(v -> updateAge());
        //home button functionality
        settingsHomeButton.setOnClickListener(v -> {
            finish();
        });
    }

    //helper. create a layout vertical for text fields dialog. usage: user edits values via keyboard input, layout for this
    private LinearLayout createDialogLayout() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        return layout;
    }

    //helper. creates editable textfield. usage: user edits values via keyboard input, textfields for this
    private EditText createDialogEditText(String hint, String value, int inputType) {
        EditText editText = new EditText(this);
        editText.setHint(hint);
        editText.setText(value);
        editText.setInputType(inputType);
        editText.setSingleLine(true);
        return editText;
    }

    //update account settings: username, password
    private void updateAccount() {
        String currUsername = sharedPreferences.getString("RegisteredUsername", "");//get temp username
        LinearLayout layout = createDialogLayout();

        EditText usernameInput = createDialogEditText("New Username (leave empty to keep current)", currUsername, InputType.TYPE_CLASS_TEXT);
        EditText passwordInput = createDialogEditText("New Password (leave empty to keep current)", "", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //first textfield shows next instead of enter, second textfield shows done, some device settings can interfere if enter is left as defualt
        usernameInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        passwordInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        layout.addView(usernameInput);
        layout.addView(passwordInput);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("update account settings")
                .setView(layout)
                .setNegativeButton("cancel", null)
                .setPositiveButton("Save", null)
                .create();

        dialog.setOnShowListener(d -> {
            Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            saveButton.setOnClickListener(v -> {
                String newUsername = usernameInput.getText().toString().trim();
                String newPassword = passwordInput.getText().toString();
                //check if new password is valid
                if (!newPassword.isEmpty() && TextUtils.isEmpty(newPassword.trim())){
                    passwordInput.setError("password invalid");
                    passwordInput.requestFocus();
                    return;
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //update username and/or password if not left empty
                if (!TextUtils.isEmpty(newUsername)) {
                    editor.putString("RegisteredUsername", newUsername);
                }
                if (!newPassword.isEmpty()) {
                    editor.putString("RegisteredPassword", newPassword);
                }
                editor.apply();
                displayAccInfo();//refresh acc info
                //toast confirmation message
                Toast.makeText(this, "account settings updated", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
            //keyboard 'done' does save
            passwordInput.setOnEditorActionListener(
                    (v, actionId, event) -> {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            saveButton.performClick();
                            return true;
                        }
                        return false;
                    });
        });
        dialog.show();
    }
    //update name settings: firstname,lastname
    private void updateName() {
        //get temp stored names
        String currFName = sharedPreferences.getString("RegisteredFirstName", "");
        String currLName = sharedPreferences.getString("RegisteredLastName", "");

        LinearLayout layout = createDialogLayout();
        //first textfield shows next instead of enter, second textfield shows done, some device settings can interfere if enter is left as defualt
        EditText firstNameInput = createDialogEditText("First Name", currFName, InputType.TYPE_CLASS_TEXT);
        EditText lastNameInput = createDialogEditText("Last Name (optional)", currLName, InputType.TYPE_CLASS_TEXT);
        firstNameInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        lastNameInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        layout.addView(firstNameInput);
        layout.addView(lastNameInput);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Update Name")
                .setView(layout)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", null)
                .create();

        dialog.setOnShowListener(d -> {
            Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            saveButton.setOnClickListener(v-> {
                String newFName = firstNameInput.getText().toString().trim();
                String newLName = lastNameInput.getText().toString().trim();
                //first name mandatory
                if (TextUtils.isEmpty(newFName)) {
                    firstNameInput.setError("first name mandatory");
                    firstNameInput.requestFocus();
                    return;
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("RegisteredFirstName", newFName);
                editor.putString("RegisteredLastName", newLName);
                editor.apply();
                displayProfileInfo();//refresh profile info
                //toast confirmation message
                Toast.makeText(this, "name updated", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
            //keyboard done does save
            lastNameInput.setOnEditorActionListener(
                    (v, actionId, event) -> {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            saveButton.performClick();
                            return true;
                        }
                        return false;
                    });
        });
        dialog.show();
    }
    //update age nume
    private void updateAge() {
        //get temp stored age info
        int currAge = sharedPreferences.getInt("RegisteredAge", -1);
        String ageText = (currAge == -1 ? "" : String.valueOf(currAge));

        LinearLayout layout = createDialogLayout();

        EditText ageInput = createDialogEditText("Age", ageText, InputType.TYPE_CLASS_NUMBER);
        ageInput.setImeOptions(EditorInfo.IME_ACTION_DONE);

        layout.addView(ageInput);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Update Age")
                .setView(layout)
                .setNegativeButton("Cancell", null)
                .setPositiveButton("Save", null)
                .create();

        dialog.setOnShowListener(d -> {
            Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            saveButton.setOnClickListener(v -> {
                String newAgeText = ageInput.getText().toString().trim();
                int newAge = -1;
                //age can be emptry
                if (!TextUtils.isEmpty(newAgeText)) {
                    try {
                        newAge = Integer.parseInt(newAgeText);
                        if (newAge <= 0 || newAge > 130) {
                            ageInput.setError("age invalid");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        ageInput.setError("age invalid");
                        return;
                    }
                }
                sharedPreferences.edit().putInt("RegisteredAge", newAge).apply();
                displayProfileInfo();//refresh
                Toast.makeText(this, "age updated", Toast.LENGTH_SHORT).show();//toast confirmation message
                dialog.dismiss();
            });
            //keybnoard done does savebutton
            ageInput.setOnEditorActionListener(
                    (v, actionId, event) -> {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            saveButton.performClick();
                            return true;
                        }
                        return false;
                    });
        });
        dialog.show();
    }
    //display account info: email, username, password(censored, accurate length though)
    private void displayAccInfo() {
        String email = sharedPreferences.getString("RegisteredEmail", "");
        String username = sharedPreferences.getString("RegisteredUsername", "");
        String password = sharedPreferences.getString("RegisteredPassword", "");
        settingsEmail.setText("Email: " + email);
        settingsUsername.setText("User: " + username);
        String passCensored = ""; // censor the password with '*'
        for (int i=0; i < password.length(); i++) {
            passCensored += "*";}
        settingsPassword.setText("Password: " + passCensored);
    }

    //display profile info: first name. last name, and age
    private void displayProfileInfo() {
        String fName = sharedPreferences.getString("RegisteredFirstName", "");
        String lName = sharedPreferences.getString("RegisteredLastName", "");
        int age = sharedPreferences.getInt("RegisteredAge", -1);
        //display ' (empty) ' if values left empty, if else display store temp values
        settingsFName.setText(fName.isEmpty() ? " (empty) " : fName);
        settingsLName.setText(lName.isEmpty() ? " (empty) " : lName);
        if (age == -1) { settingsAgeNum.setText(" (empty) ");
        } else {
            settingsAgeNum.setText(String.valueOf(age));
        }
    }
}