package com.example.splitease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

// Main activity for the application, where users can add and view expenses
public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper; // DatabaseHelper instance for database operations
    private RecyclerView recyclerView; // RecyclerView to display the list of expenses
    private ExpenseAdapter expenseAdapter; // Adapter for the RecyclerView
    private ArrayList<Expense> expenseList; // List to hold expenses
    private LinearLayout emptyView; // Layout to display when there are no expenses

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a LinearLayout programmatically to set as the content view
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout); // Set the layout as the content view

        // Button to add a new expense
        Button buttonAddExpense = new Button(this);
        buttonAddExpense.setText("Add Expense");
        layout.addView(buttonAddExpense); // Add button to the layout

        // Button to show existing expenses
        Button buttonShowExpenses = new Button(this);
        buttonShowExpenses.setText("Show Expenses");
        layout.addView(buttonShowExpenses); // Add button to the layout

        // Layout to show when there are no expenses
        emptyView = new LinearLayout(this);
        emptyView.setVisibility(View.GONE); // Initially hidden
        TextView emptyText = new TextView(this);
        emptyText.setText("No expenses available"); // Message for no expenses
        emptyView.addView(emptyText); // Add message to emptyView
        layout.addView(emptyView); // Add emptyView to the main layout

        // Set up RecyclerView to display expenses
        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set layout manager for RecyclerView
        layout.addView(recyclerView); // Add RecyclerView to the layout

        // Initialize DatabaseHelper and the expense list
        dbHelper = new DatabaseHelper(this);
        expenseList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(expenseList); // Initialize adapter with expense list
        recyclerView.setAdapter(expenseAdapter); // Set the adapter to the RecyclerView

        loadExpenses(); // Load expenses from the database

        // Set up button click listeners
        buttonAddExpense.setOnClickListener(v -> addExpense()); // Listener to add a new expense
        buttonShowExpenses.setOnClickListener(v -> showExpenses()); // Listener to show existing expenses
    }

    // Method to load expenses from the database and update the RecyclerView
    private void loadExpenses() {
        expenseList.clear(); // Clear existing expenses
        expenseList.addAll(dbHelper.getAllExpenses()); // Fetch expenses from the database
        expenseAdapter.notifyDataSetChanged(); // Notify adapter about data changes
        updateEmptyView(); // Update empty view based on the current expense list
    }

    // Method to update visibility of the empty view based on the expense list
    private void updateEmptyView() {
        if (expenseList.isEmpty()) {
            recyclerView.setVisibility(View.GONE); // Hide RecyclerView if no expenses
            emptyView.setVisibility(View.VISIBLE); // Show empty view
        } else {
            recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView if there are expenses
            emptyView.setVisibility(View.GONE); // Hide empty view
        }
    }

    // Method to navigate to the ShowExpensesActivity
    private void showExpenses() {
        Intent intent = new Intent(MainActivity.this, ShowExpensesActivity.class);
        startActivity(intent); // Start ShowExpensesActivity
    }

    // Method to navigate to the AddExpenseActivity
    private void addExpense() {
        Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
        startActivity(intent); // Start AddExpenseActivity
    }
}
