package com.example.splitease;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

// Activity to show a list of expenses
public class ShowExpensesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper; // DatabaseHelper instance for accessing database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenses); // Set the layout for displaying expenses

        // Initialize DatabaseHelper to interact with the database
        databaseHelper = new DatabaseHelper(this);
        ListView listView = findViewById(R.id.listView_expenses); // Find the ListView in the layout

        // Get the list of expenses from the database
        ArrayList<Expense> expenses = databaseHelper.getAllExpenses();
        ArrayList<String> expenseDescriptions = new ArrayList<>(); // List to hold formatted expense descriptions

        // Loop through each expense and format its description and amount
        for (Expense expense : expenses) {
            expenseDescriptions.add(expense.getDescription() + " - " + expense.getAmount()); // Add formatted string to the list
        }

        // Set up the ArrayAdapter to display the expense descriptions in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expenseDescriptions);
        listView.setAdapter(adapter); // Bind the adapter to the ListView
    }
}
