package com.unicodedev.fitpal.forum;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.unicodedev.fitpal.R;

import java.util.ArrayList;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.MyViewHolder> {
    Context context;
    ArrayList<ReplyModal> replyArrayList;

    public ReplyAdapter(Context context, ArrayList<ReplyModal> replyArrayList) {
        this.context = context;
        this.replyArrayList = replyArrayList;
    }

    @NonNull
    @Override
    public ReplyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.forum_reply_card, parent, false);
        return new ReplyAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ReplyModal reply = replyArrayList.get(position);

        String authorID = reply.getAuthorID();

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
        holder.time_ago.setText(reply.getTimeAgo());

        holder.text.setText(reply.getText());

    }

    @Override
    public int getItemCount() {
        return replyArrayList.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text, name, time_ago;
        ImageView profile_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
            name = itemView.findViewById(R.id.name);
            profile_image = itemView.findViewById(R.id.profile_img);
            time_ago = itemView.findViewById(R.id.time_ago);

        }
    }
}

