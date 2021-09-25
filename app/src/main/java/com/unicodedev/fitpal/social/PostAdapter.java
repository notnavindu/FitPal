package com.unicodedev.fitpal.social;

import android.content.Context;
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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder>{
    Context context;
    ArrayList<PostModal> questionArrayList;



    public PostAdapter(Context context, ArrayList<PostModal> questionArrayList) {
        this.context = context;
        this.questionArrayList = questionArrayList;
    }

    @NonNull
    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.social_post_card, parent, false);
        return new PostAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.MyViewHolder holder, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        PostModal question = questionArrayList.get(position);
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

            db.collection("Replies").whereEqualTo("questionID", question.getId())
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                            if(error != null){
                                Log.e("Firestore Log", error.getMessage());
                                return;
                            }{
                                String count = String.valueOf(value.size());
                                holder.reply_count.setText(count);
                            }


                        }
                    });


            //Handle Likes
            db.collection("Social").document(question.getId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value.exists()){
                        Object likes = value.getData().get("likes");
                        List<String> likesArray = (List<String>) likes;

                        if(likesArray != null){
                            if(likesArray.contains(user.getUid())){
                                // if user has liked
                                holder.upvote_btn.setImageResource(R.drawable.upvote_icon_active);
                                holder.upvote_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // handle unlike
                                        likesArray.remove(user.getUid());
                                        db.collection("Social").document(question.getId()).update("likes", likesArray);
                                    }
                                });
                            } else{
                                // if user has not liked
                                holder.upvote_btn.setImageResource(R.drawable.upvote_icon);
                                holder.upvote_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // handle like
                                        likesArray.add(user.getUid());
                                        db.collection("Social").document(question.getId()).update("likes", likesArray);

                                    }
                                });
                            }
                        }


                        String count = String.valueOf(likesArray.size());
                        holder.upvote_count.setText(count);
                    }


                }
            });

            holder.title.setText(question.getTitle());
            holder.time_ago.setText(question.getTimeAgo());
            Log.d("ASSSSS", "aa:" + question.getTitle());
            Picasso.get().load(question.getImageURL()).into(holder.post_image);

//            holder.card.setOnClickListener(view -> {
//
//                Intent i = new Intent(context, ForumQuestion.class);
//                i.putExtra("questionid", question.getId());
//                context.startActivity(i);
//            });

        }
    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        RelativeLayout upvote_area;
        TextView title, name, time_ago, reply_count, upvote_count;
        ImageView profile_image, upvote_btn, post_image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            name = itemView.findViewById(R.id.user_name);
            profile_image = itemView.findViewById(R.id.profile_image);
            time_ago = itemView.findViewById(R.id.posted_date);
            card = itemView.findViewById(R.id.card_view);
            reply_count = itemView.findViewById(R.id.reply_count);
            upvote_btn = itemView.findViewById(R.id.upvote_btn);
            upvote_area = itemView.findViewById(R.id.upvote_area);
            upvote_count = itemView.findViewById(R.id.upvote_count);
            post_image = itemView.findViewById(R.id.post_image);

        }
    }
}
