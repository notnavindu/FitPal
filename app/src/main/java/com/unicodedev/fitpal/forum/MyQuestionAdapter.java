package com.unicodedev.fitpal.forum;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.unicodedev.fitpal.R;

import java.util.ArrayList;

public class MyQuestionAdapter extends RecyclerView.Adapter<MyQuestionAdapter.MyViewHolder> {

    Context context;
    ArrayList<QuestionModal> questionArrayList;

    public MyQuestionAdapter(Context context, ArrayList<QuestionModal> questionArrayList) {
        this.context = context;
        this.questionArrayList = questionArrayList;
    }

    @NonNull
    @Override
    public MyQuestionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.forum_myquestions_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyQuestionAdapter.MyViewHolder holder, int position) {

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

            holder.title.setText(question.getQuestion());
            holder.time_ago.setText(question.getTimeAgo());

            holder.delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.collection("Forum").document(question.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context.getApplicationContext(), "Question Deleted", Toast.LENGTH_SHORT).show();
                            questionArrayList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context.getApplicationContext(), "Failed to Delete", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }



    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, name, time_ago;
        ImageView profile_image, delete_btn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.card_title);
            name = itemView.findViewById(R.id.card_name);
            profile_image = itemView.findViewById(R.id.profile_img);
            delete_btn = itemView.findViewById(R.id.delete_btn);
            time_ago = itemView.findViewById(R.id.time_ago);
        }
    }

}

