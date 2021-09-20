package com.unicodedev.fitpal.forum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.unicodedev.fitpal.R;

import java.util.ArrayList;

public class ForumMain extends AppCompatActivity {


    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<QuestionModal> questionArrayList;
    QuestionAdapter questionAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();


        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView myQuestionBtn= (TextView) findViewById(R.id.my_question_button);
        myQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyQuestions.class);
                startActivity(i);
            }
        });

        FloatingActionButton addQuestionBtn = (FloatingActionButton) findViewById(R.id.addQuestionBtn);
        addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddQuestion.class);
                startActivity(i);
            }
        });

        db = FirebaseFirestore.getInstance();
        questionArrayList = new ArrayList<QuestionModal>();
        questionAdapter = new QuestionAdapter(ForumMain.this, questionArrayList);

        recyclerView.setAdapter(questionAdapter);

        getForumQuestions();


    }

    private void getForumQuestions() {

        db.collection("Forum").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                        questionArrayList.add(dc.getDocument().toObject(QuestionModal.class));
                    }

                    questionAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        });

    }


}