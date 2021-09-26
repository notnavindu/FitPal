package com.unicodedev.fitpal.social;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.unicodedev.fitpal.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder>{
    Context context;
    ArrayList<CommentModal> questionArrayList;



    public CommentsAdapter(Context context, ArrayList<CommentModal> questionArrayList) {
        this.context = context;
        this.questionArrayList = questionArrayList;
    }

    @NonNull
    @Override
    public CommentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.social_comment_card, parent, false);
        return new CommentsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.MyViewHolder holder, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        CommentModal question = questionArrayList.get(position);
        String authorID = question.getAuthorId();

        if(authorID != null){
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


            holder.title.setText(question.getText());
            holder.time_ago.setText(question.getTimeAgo());

        }
    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        RelativeLayout upvote_area;
        TextView title, name, time_ago;
        ImageView profile_image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.text);
            name = itemView.findViewById(R.id.user_name);
            profile_image = itemView.findViewById(R.id.profile_image);
            time_ago = itemView.findViewById(R.id.posted_date);
            card = itemView.findViewById(R.id.card_view);


        }
    }
}
