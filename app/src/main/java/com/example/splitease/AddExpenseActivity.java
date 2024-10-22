package com.example.splitease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddExpenseActivity extends AppCompatActivity {
    // DatabaseHelper will be used to interact with the database
    private DatabaseHelper dbHelper;
    // EditText for inputting the amount and description of the expense
    private EditText editAmount, editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for this activity
        setContentView(R.layout.activity_add_expense);

        // Initialize the EditText fields for amount and description from the layout
        editAmount = findViewById(R.id.editAmount);
        editDescription = findViewById(R.id.editDescription);
        // Initialize the Add button from the layout
        Button buttonAdd = findViewById(R.id.buttonAdd);

        // Create an instance of DatabaseHelper for database operations
        dbHelper = new DatabaseHelper(this);

        // Set a click listener on the Add button to trigger the addExpense() method when clicked
        buttonAdd.setOnClickListener(v -> addExpense());
    }

    // Method to add an expense to the database
    private void addExpense() {
        // Get the text entered in the amount and description fields
        String amountStr = editAmount.getText().toString();
        String description = editDescription.getText().toString();

        // Check if both fields are not empty
        if (!amountStr.isEmpty() && !description.isEmpty()) {
            try {
                // Convert the amount to a double value
                double amount = Double.parseDouble(amountStr);
                // Call the method to add the expense data to the database
                dbHelper.addExpense(amount, description);
                // Show a success message
                Toast.makeText(this, "Expense added successfully", Toast.LENGTH_SHORT).show();
                // Close the current activity and go back to the previous one
                finish();
            } catch (NumberFormatException e) {
                // If the amount is not a valid number, show an error message
                Toast.makeText(this, "Invalid amount entered", Toast.LENGTH_SHORT).show();
            }
        } else {
            // If either field is empty, show a message to fill all fields
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
