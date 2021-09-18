package com.unicodedev.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unicodedev.fitpal.R;
import com.unicodedev.models.Exercise;

import java.util.ArrayList;

public class DefaultExerciseAdapter extends RecyclerView.Adapter<DefaultExerciseAdapter.ExerciseViewHolder> {

    Context context;
    ArrayList<Exercise> exerciseArrayList;

    public DefaultExerciseAdapter(Context context, ArrayList<Exercise> exerciseArrayList) {
        this.context = context;
        this.exerciseArrayList = exerciseArrayList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.exercise, parent, false);

        return new ExerciseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultExerciseAdapter.ExerciseViewHolder holder, int position) {

        Exercise exercise = exerciseArrayList.get(position);
        holder.exerciseName.setText(exercise.getName());
        holder.repsAndSets.setText("reps/sets " + exercise.getRepetition() + "*" + exercise.getSets());
    }

    @Override
    public int getItemCount() {
        return exerciseArrayList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName, repsAndSets;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exercise_name);
            repsAndSets = itemView.findViewById(R.id.reps_and_sets);
        }
    }
}
