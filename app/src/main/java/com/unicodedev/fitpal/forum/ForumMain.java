package com.unicodedev.fitpal.forum;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.unicodedev.fitpal.HomeActivity;
import com.unicodedev.fitpal.Signin;
import com.unicodedev.fitpal.Profile;
import com.unicodedev.fitpal.R;
import com.unicodedev.fitpal.social.SocialHome;

import java.util.ArrayList;

public class ForumMain extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<QuestionModal> questionArrayList;
    QuestionAdapter questionAdapter;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mauth;
    GoogleSignInClient mGoogleSignInClient;

    ImageView logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_main);
        user = FirebaseAuth.getInstance().getCurrentUser();

        logout_btn = findViewById(R.id.logout_btn);

        mauth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        //Bottom navigation bar
        BottomNavigationView navbar = findViewById(R.id.bottom_navigation);
        navbar.setSelectedItemId(R.id.forum);

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.social:
                        startActivity(new Intent(getApplicationContext(), SocialHome.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.forum:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        //Handle Logout
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ForumMain.this);
                builder.setCancelable(true);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("Logout",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mauth.signOut();
                                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(ForumMain.this, Signin.class));
                                    }
                                });
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //If cancel is pressed
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });




        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();


        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView myQuestionBtn= (TextView) findViewById(R.id.my_question_button);
        myQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyQuestions.class);
                startActivity(i);
                overridePendingTransition(0,0);
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

        db.collection("Forum").whereNotEqualTo("authorID", user.getUid())
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
                            QuestionModal question = dc.getDocument().toObject(QuestionModal.class);
                            question.setId(dc.getDocument().getId());
                            questionArrayList.add(question);
                            questionAdapter.notifyDataSetChanged();
                        }
                        if(dc.getType() == DocumentChange.Type.REMOVED){
                            questionAdapter.notifyDataSetChanged();
                        }

//                        questionAdapter.notifyDataSetChanged();
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