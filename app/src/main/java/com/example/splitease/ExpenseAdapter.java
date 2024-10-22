package com.example.splitease;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Adapter class to manage and display a list of expenses in a RecyclerView
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    // List to store the expenses that will be displayed
    private ArrayList<Expense> expenseList;

    // Constructor to initialize the adapter with an expense list
    public ExpenseAdapter(ArrayList<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    // This method creates and inflates the view for each item in the RecyclerView
    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate a simple layout for each list item (two text views in this case)
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ExpenseViewHolder(view); // Return the view holder containing the inflated view
    }

    // This method binds data (an expense) to the views in the view holder
    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        // Get the expense at the current position in the list
        Expense expense = expenseList.get(position);
        // Set the text for amount and description in the respective text views
        holder.amountTextView.setText("Amount: " + expense.getAmount());
        holder.descriptionTextView.setText("Description: " + expense.getDescription());
    }

    // This method returns the total number of items in the RecyclerView (size of the expense list)
    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    // Inner class that represents the ViewHolder for each expense item
    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        // TextViews to display the expense amount and description
        TextView amountTextView;
        TextView descriptionTextView;

        // Constructor to initialize the TextViews from the inflated layout
        ExpenseViewHolder(View itemView) {
            super(itemView);
            // Link the TextViews with the layout views
            amountTextView = itemView.findViewById(android.R.id.text1);
            descriptionTextView = itemView.findViewById(android.R.id.text2);
        }
    }
}
