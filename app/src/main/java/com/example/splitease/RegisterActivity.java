package com.example.splitease;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// Activity for user registration
public class RegisterActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword; // Input fields for username and password
    private Button buttonRegister; // Button to register the user
    private TextView textViewLogin; // TextView to navigate back to the login screen
    private DatabaseHelper dbHelper; // DatabaseHelper instance for database operations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Set the layout for registration

        // Initialize views
        initViews();

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Set OnClickListener for the Register button
        buttonRegister.setOnClickListener(v -> registerUser());

        // Set OnClickListener for the Login text view to navigate back to login
        textViewLogin.setOnClickListener(v -> navigateToLogin());
    }

    private void initViews() {
        // Find and link the UI elements to their respective variables
        editTextUsername = findViewById(R.id.editTextUsername); // Input for username
        editTextPassword = findViewById(R.id.editTextPassword); // Input for password
        buttonRegister = findViewById(R.id.buttonRegister); // Register button
        textViewLogin = findViewById(R.id.textViewLogin); // Link to the login text view
    }

    // Method to register a new user
    private void registerUser() {
        String username = editTextUsername.getText().toString().trim(); // Get and trim the username input
        String password = editTextPassword.getText().toString().trim(); // Get and trim the password input

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show(); // Show error if fields are empty
            return; // Exit the method if validation fails
        }

        // Attempt to register the user using DatabaseHelper
        try {
            dbHelper.addUser(username, password); // Register the user with the provided username and password
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show(); // Success message
            navigateToLogin(); // Navigate to the login screen after successful registration
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show(); // Show error message if username exists
        } catch (Exception e) {
            Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show(); // Generic error message for any other issues
        }
    }

    // Method to navigate to the LoginActivity
    private void navigateToLogin() {
        // Start the LoginActivity
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent); // Start login activity
        finish(); // Close RegisterActivity
    }
}
