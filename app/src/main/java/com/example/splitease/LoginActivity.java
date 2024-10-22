package com.example.splitease;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView; // Importing TextView for the register link
import android.widget.Toast;

// Activity class for user login
public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword; // Fields for user input (email and password)
    private Button buttonLogin; // Button to trigger the login action
    private TextView buttonRegister; // TextView for user to navigate to the registration screen
    private DatabaseHelper dbHelper; // DatabaseHelper instance for user validation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Set the layout for this activity

        // Initialize views
        initViews();

        // Initialize DatabaseHelper to interact with the database
        dbHelper = new DatabaseHelper(this);

        // Set OnClickListener for the Login button
        buttonLogin.setOnClickListener(v -> loginUser());

        // Set OnClickListener for the Register button
        buttonRegister.setOnClickListener(v -> navigateToRegister());
    }

    private void initViews() {
        // Link the EditText and Button views from the layout
        editTextEmail = findViewById(R.id.editTextEmail); // Updated ID for email input
        editTextPassword = findViewById(R.id.editTextPassword); // ID for password input
        buttonLogin = findViewById(R.id.buttonLogin); // ID for login button
        buttonRegister = findViewById(R.id.textViewRegister); // ID for register link
    }

    private void loginUser() {
        // Retrieve the input values from the EditTexts
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // Check if the provided credentials are valid using DatabaseHelper
        if (dbHelper.validateUser(email, password)) {
            // Successful login, proceed to MainActivity
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show(); // Show success message
            Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Create intent to navigate to MainActivity
            startActivity(intent); // Start MainActivity
            finish(); // Close LoginActivity to prevent returning to it
        } else {
            // Show error message for invalid credentials
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToRegister() {
        // Start the RegisterActivity for user registration
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent); // Navigate to the registration screen
    }
}
