package com.unicodedev.fitpal.forum;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;
import com.unicodedev.fitpal.R;

import java.util.ArrayList;
import java.util.List;

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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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



            //Handle Likes
            db.collection("Replies").document(reply.getId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value.exists()){
                        Object likes = value.getData().get("likes");
                        List<String> likesArray = (List<String>) likes;

                        if(likesArray.contains(user.getUid())){
                            // if user has liked
                            holder.upvote_btn.setImageResource(R.drawable.upvote_icon_active);
                            holder.upvote_area.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    // handle unlike
                                    likesArray.remove(user.getUid());
                                    db.collection("Replies").document(reply.getId()).update("likes", likesArray);


                                }
                            });
                        } else{
                            // if user has not liked
                            holder.upvote_btn.setImageResource(R.drawable.upvote_icon);
                            holder.upvote_area.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // handle like
                                    likesArray.add(user.getUid());
                                    db.collection("Replies").document(reply.getId()).update("likes", likesArray);

                                }
                            });
                        }

                        String count = String.valueOf(likesArray.size());
                        holder.upvote_count.setText(count);
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
        LinearLayout upvote_area;
        TextView text, name, time_ago, upvote_count;
        ImageView profile_image, upvote_btn,best_ans_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
            name = itemView.findViewById(R.id.name);
            profile_image = itemView.findViewById(R.id.profile_img);
            time_ago = itemView.findViewById(R.id.time_ago);
            upvote_area = itemView.findViewById(R.id.upvote_area);
            upvote_count = itemView.findViewById(R.id.upvote_count);
            upvote_btn = itemView.findViewById(R.id.upvote_btn);
            best_ans_btn = itemView.findViewById(R.id.best_ans_btn);

        }
    }
}

