package com.unicodedev.fitpal.forum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;
import com.squareup.picasso.Picasso;
import com.unicodedev.fitpal.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumQuestion extends AppCompatActivity {
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<ReplyModal> replyArrayList;
    ReplyAdapter replyAdapter;
    String questionid;
    LinearLayout upvote_area;
    TextView author_name, question_title, question_description, reply_count, upvote_count;
    ImageView profile_image, upvote_btn;
    EditText reply_input;
    FloatingActionButton add_reply_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_question);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Intent i = getIntent();
        questionid = i.getStringExtra("questionid");

        db = FirebaseFirestore.getInstance();

        author_name = findViewById(R.id.author_name);
        question_title = findViewById(R.id.question_title);
        question_description = findViewById(R.id.question_description);
        profile_image = findViewById(R.id.profile_img);
        reply_input = findViewById(R.id.reply_input);
        add_reply_btn = findViewById(R.id.add_reply_btn);
        reply_count = findViewById(R.id.reply_count);
        upvote_area = findViewById(R.id.upvote_area);
        upvote_btn = findViewById(R.id.upvote_btn);
        upvote_count = findViewById(R.id.upvote_count);

        getQuestion();


        //Handle Likes
        db.collection("Forum").document(questionid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    Object likes = value.getData().get("likes");
                    List<String> likesArray = (List<String>) likes;

                    if(likesArray.contains(user.getUid())){
                        // if user has liked
                        upvote_btn.setImageResource(R.drawable.upvote_icon_active);
                        upvote_area.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // handle unlike
                                likesArray.remove(user.getUid());
                                db.collection("Forum").document(questionid).update("likes", likesArray);


                            }
                        });
                    } else{
                        // if user has not liked
                        upvote_btn.setImageResource(R.drawable.upvote_icon);
                        upvote_area.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // handle like
                                likesArray.add(user.getUid());
                                db.collection("Forum").document(questionid).update("likes", likesArray);

                            }
                        });
                    }

                    String count = String.valueOf(likesArray.size());
                    upvote_count.setText(count);
                }


            }
        });


        add_reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFields()){
                    String reply = reply_input.getText().toString();
                    Date date =java.util.Calendar.getInstance().getTime();
                    List<String> likes = new ArrayList<>();


                    Map<String, Object> data = new HashMap<>();
                    data.put("authorID", user.getUid() );
                    data.put("questionID", questionid);
                    data.put("text", reply);
                    data.put("publishedOn", date);
                    data.put("likes", likes);
                    data.put("isBest", false);

                    db.collection("Replies").document()
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("FirebaseLog", "DocumentSnapshot successfully written!");
                                    reply_input.setText("");
                                    Toast.makeText(getApplicationContext(), "Reply Added", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("FirebaseLog", "Error writing document", e);
                                }
                            });



                }
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        replyArrayList = new ArrayList<ReplyModal>();
        replyAdapter = new ReplyAdapter(ForumQuestion.this, replyArrayList);

        recyclerView.setAdapter(replyAdapter);
        getReplies();

    }

    private void getReplies() {

        db.collection("Replies").whereEqualTo("questionID", questionid).
                addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                int count = 0;
                if(error != null){
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Log.e("Firestore error", error.getMessage());
                    return;
                }


                count = value.size();

                for(DocumentChange dc: value.getDocumentChanges()){

                    if(dc.getType() == DocumentChange.Type.ADDED ){

                        ReplyModal reply = dc.getDocument().toObject(ReplyModal.class);
                        reply.setId(dc.getDocument().getId());
                        replyArrayList.add(reply);
                    }
                    if(dc.getType() == DocumentChange.Type.REMOVED){
                        replyAdapter.notifyDataSetChanged();
                    }

                    replyAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }

                reply_count.setText(String.valueOf(count));

            }



        });
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }



    }


    private void getQuestion() {
        db.collection("Forum").document(questionid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    Map<String, Object> data = task.getResult().getData();

                    String authorID = data.get("authorID").toString();


                    db.collection("Users").document(authorID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    //Get Required Details of the Author
                                    String authorName = document.getString("nickname");
                                    String profilePicUrl = document.getString("profilePicUrl");

                                    Picasso.get().load(profilePicUrl).into(profile_image);

                                    author_name.setText(authorName);

                                } else {
                                    Log.d("Firestore Log", "No such document");
                                }
                            } else {
                                Log.d("Firestore Log", "get failed with ", task.getException());
                            }
                        }
                    });


                    question_title.setText(data.get("question").toString());
                    question_description.setText(data.get("description").toString());

                }
            }
        });
    }

    private boolean validateFields(){
        if (reply_input.length() == 0) {
            reply_input.setError("Question is required");
            return false;
        }

        return true;
    }
}