package com.example.bc_kitchen_project.feedback;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bc_kitchen_project.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class RatingAdapter extends FirebaseRecyclerAdapter<Rating, RatingAdapter.RatingsViewholder> {

    public RatingAdapter(@NonNull FirebaseRecyclerOptions<Rating> options) {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void onBindViewHolder(@NonNull RatingsViewholder holder, int position, @NonNull Rating model) {
        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.rat_nameValue.setText(model.getName());

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.rat_ratingValue.setText(model.getRatingValue());

        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.rat_commentValue.setText(model.getComment());
    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public RatingsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating, parent, false);
        return new RatingAdapter.RatingsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class RatingsViewholder extends RecyclerView.ViewHolder {
        TextView rat_nameValue, rat_ratingValue, rat_commentValue;
        public RatingsViewholder(@NonNull View itemView) {
            super(itemView);
            rat_nameValue = itemView.findViewById(R.id.rat_nameValue);
            rat_ratingValue = itemView.findViewById(R.id.rat_ratingValue);
            rat_commentValue = itemView.findViewById(R.id.rat_commentValue);
        }
    }
}