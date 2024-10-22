package com.example.splitease;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database name and version constants
    private static final String DATABASE_NAME = "splitwise.db";
    private static final int DATABASE_VERSION = 1;

    // Constants for the expenses table
    public static final String TABLE_EXPENSES = "expenses";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DESCRIPTION = "description";

    // Constants for the users table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Constructor to initialize the database helper with the context
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create the expenses table
        String createExpensesTable = "CREATE TABLE " + TABLE_EXPENSES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AMOUNT + " REAL, " +
                COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(createExpensesTable); // Execute the SQL query

        // SQL query to create the users table (without email)
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " + // Username is the primary key
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable); // Execute the SQL query
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If database version changes, drop the old tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db); // Recreate tables
    }

    // Method to add an expense to the database
    public void addExpense(double amount, String description) {
        // Get writable access to the database
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a ContentValues object to store expense data
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_DESCRIPTION, description);

        try {
            // Insert the new expense into the expenses table
            db.insert(TABLE_EXPENSES, null, values);
        } finally {
            db.close(); // Always close the database after use
        }
    }

    // Method to retrieve all expenses from the database
    public ArrayList<Expense> getAllExpenses() {
        ArrayList<Expense> expenseList = new ArrayList<>(); // List to hold expenses
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query to get all rows from the expenses table
            cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSES, null);
            if (cursor != null && cursor.moveToFirst()) {
                // Loop through each row and create an Expense object
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    @SuppressLint("Range") double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT));
                    @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                    expenseList.add(new Expense(id, amount, description)); // Add the expense to the list
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close(); // Close the cursor after use
            }
            db.close(); // Always close the database after use
        }
        return expenseList; // Return the list of expenses
    }

    // Method to add a user to the database
    public void addUser(String username, String password) {
        // Check if the username already exists
        if (isUsernameExists(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username); // Store the username
        values.put(COLUMN_PASSWORD, password); // Store the password

        try {
            // Insert the new user into the users table
            db.insert(TABLE_USERS, null, values);
        } finally {
            db.close(); // Always close the database after use
        }
    }

    // Method to check if a username already exists in the database
    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query to check if the username exists
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?", new String[]{username});
            return cursor != null && cursor.getCount() > 0; // Return true if the username exists
        } finally {
            if (cursor != null) {
                cursor.close(); // Close the cursor after use
            }
            db.close(); // Always close the database after use
        }
    }

    // Method to validate user credentials (username and password)
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query to check if the username and password match
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                    COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
            return cursor != null && cursor.moveToFirst(); // Return true if the user is found
        } finally {
            if (cursor != null) {
                cursor.close(); // Close the cursor after use
            }
            db.close(); // Always close the database after use
        }
    }
}
