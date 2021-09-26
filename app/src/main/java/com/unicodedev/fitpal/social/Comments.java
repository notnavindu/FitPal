package com.unicodedev.fitpal.social;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.unicodedev.fitpal.R;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Comments extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<CommentModal> commentArray;
    CommentsAdapter questionAdapter;

    FirebaseFirestore db;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_comments);

        ImageView image = findViewById(R.id.image);
        EditText comment = findViewById(R.id.comment_input);
        FloatingActionButton fab = findViewById(R.id.add_comment_button);

        Intent i = getIntent();
        String postId = i.getStringExtra("postId");
        String imageURL = i.getStringExtra("image");

        Picasso.get().load(imageURL).into(image);

        user = FirebaseAuth.getInstance().getCurrentUser();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();


        commentArray = new ArrayList<CommentModal>();
        questionAdapter = new CommentsAdapter(Comments.this, commentArray);

        recyclerView.setAdapter(questionAdapter);

        getForumQuestions(postId);

        // Add comment
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Comments.this, comment.getText().toString(), Toast.LENGTH_SHORT).show();

                Map<String, Object> data = new HashMap<>();
                String authorId = user.getUid();
                Date date = java.util.Calendar.getInstance().getTime();

                data.put("authorId", authorId);
                data.put("postID", postId);
                data.put("text", comment.getText().toString());
                data.put("publishedOn", date);


                db.collection("Comments").document()
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("FirebaseLog", "DocumentSnapshot successfully written!");
                                comment.getText().clear();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("FirebaseLog", "Error writing document", e);
                            }
                        });

            }
        });
    }

    private void getForumQuestions(String postId) {



        db.collection("Comments").whereEqualTo("postID",postId ).orderBy("publishedOn", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc: value.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED ){
                                CommentModal question = dc.getDocument().toObject(CommentModal.class);
                                question.setId(dc.getDocument().getId());
                                commentArray.add(question);
                            }
                            if(dc.getType() == DocumentChange.Type.REMOVED){
                                questionAdapter.notifyDataSetChanged();
                            }

                            questionAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }

                        }
                    }
                });
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }


    }
}