package com.unicodedev.fitpal.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unicodedev.fitpal.R;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    Context context;
    ArrayList<QuestionModal> questionArrayList;

    public QuestionAdapter(Context context, ArrayList<QuestionModal> questionArrayList) {
        this.context = context;
        this.questionArrayList = questionArrayList;
    }

    @NonNull
    @Override
    public QuestionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.forum_question_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.MyViewHolder holder, int position) {

        QuestionModal question = questionArrayList.get(position);

        holder.title.setText(question.getQuestion());

    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.card_title);

        }
    }

}
