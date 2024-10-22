package com.example.splitease;

import java.util.ArrayList;

// Class to handle fetching all expenses from the database
public class GetAllExpenses {
    // Instance of DatabaseHelper to interact with the database
    private DatabaseHelper dbHelper;

    // Constructor to initialize the DatabaseHelper object
    public GetAllExpenses(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper; // Assign the provided dbHelper
    }

    // Method to fetch all expenses from the database
    public ArrayList<Expense> fetchAllExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>(); // Create a list to hold the expenses

        try {
            // Get the list of expenses from the database using dbHelper
            expenses = dbHelper.getAllExpenses();
        } catch (Exception e) {
            // Handle any exceptions that might occur during database access
            e.printStackTrace(); // Print the stack trace for debugging
        }

        return expenses; // Return the fetched list of expenses
    }
}
