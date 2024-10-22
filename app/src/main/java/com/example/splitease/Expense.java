package com.example.splitease;

// Class representing an Expense object
public class Expense {
    // Fields to store the id, amount, and description of the expense
    private int id;
    private double amount;
    private String description;

    // Constructor to initialize the expense object with values for id, amount, and description
    public Expense(int id, double amount, String description) {
        this.id = id; // Assign the id
        this.amount = amount; // Assign the amount
        this.description = description; // Assign the description
    }

    // Getter method to retrieve the expense id
    public int getId() {
        return id;
    }

    // Getter method to retrieve the expense amount
    public double getAmount() {
        return amount;
    }

    // Getter method to retrieve the expense description
    public String getDescription() {
        return description;
    }
}
