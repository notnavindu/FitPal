package com.unicodedev.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unicodedev.fitpal.R;
import com.unicodedev.models.Workout;

import java.util.ArrayList;

public class YourWorkoutsAdapter extends RecyclerView.Adapter<YourWorkoutsAdapter.WorkoutsViewHolder> {

    Context context;
    ArrayList<Workout> workoutArrayList;
    FirebaseFirestore db;
    FirebaseAuth auth;


    public YourWorkoutsAdapter(Context context, ArrayList<Workout> workoutArrayList) {
        this.context = context;
        this.workoutArrayList = workoutArrayList;
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public WorkoutsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.workout, parent, false);

        return new YourWorkoutsAdapter.WorkoutsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutsViewHolder holder, int position) {
        Workout workout = workoutArrayList.get(position);
        holder.workoutName.setText(workout.getName());
        holder.repsAndSets.setText("reps/sets " + workout.getRepetition() + "*" + workout.getSets());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("user-workouts")
                        .document(workout.getDocId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    workoutArrayList.remove(workout);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return workoutArrayList.size();
    }

    public static class WorkoutsViewHolder extends RecyclerView.ViewHolder {

        TextView workoutName, repsAndSets;
        FloatingActionButton deleteBtn;

        public WorkoutsViewHolder(@NonNull View itemView) {
            super(itemView);

            workoutName = itemView.findViewById(R.id.workout_name);
            repsAndSets = itemView.findViewById(R.id.reps_and_sets);
            deleteBtn = itemView.findViewById(R.id.delete_btn);

        }
    }
}
