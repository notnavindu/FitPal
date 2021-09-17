package com.unicodedev.fitpal.forum;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
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
        String authorID = question.getAuthorID();

        if(authorID != null){

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            //Getting user ID
            DocumentReference docRef = db.collection("Users").document(authorID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            //Get Required Details of the Author
                            String authorName = document.getString("nickname");
                            String profilePicUrl = document.getString("profilePicUrl");

                            Picasso.get().load(profilePicUrl).into(holder.profile_image);

                            holder.name.setText(authorName);
                        } else {
                            Log.d("Firestore Log", "No such document");
                        }
                    } else {
                        Log.d("Firestore Log", "get failed with ", task.getException());
                    }
                }
            });

        }

        holder.title.setText(question.getQuestion());
        holder.time_ago.setText(question.getTimeAgo());
        
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ForumQuestion.class);
                i.putExtra("questionid", question.getId());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder{
        
        CardView card;
        TextView title, name, time_ago;
        ImageView profile_image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.card_title);
            name = itemView.findViewById(R.id.card_name);
            profile_image = itemView.findViewById(R.id.profile_img);
            time_ago = itemView.findViewById(R.id.time_ago);
            card = itemView.findViewById(R.id.card);

        }
    }

}
