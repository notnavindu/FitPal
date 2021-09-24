package com.unicodedev.fitpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.unicodedev.fitpal.forum.ForumMain;
import com.unicodedev.fitpal.social.SocialHome;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Profile extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mauth;
    ProgressDialog progressDialog, updateProgressDialog;
    FirebaseStorage storage;
    StorageReference storageReference;
    GoogleSignInClient mGoogleSignInClient;

    Uri imageUri, downloadUrl;

    ImageView profile_image, logout_btn;
    EditText nameInput;
    Button submit_btn;
    CardView profile_card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mauth = FirebaseAuth.getInstance();
        user = mauth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        String userId = user.getUid();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);


        profile_image = findViewById(R.id.profile_image);
        nameInput = findViewById(R.id.nameInput);
        submit_btn = findViewById(R.id.submit_btn);
        profile_card = findViewById(R.id.profile_card);
        logout_btn = findViewById(R.id.logout_btn);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Profile Data...");
        progressDialog.show();
        //Preload data to fields
        DocumentReference docRef = db.collection("Users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Get Required Details of the User
                        String name = document.getString("nickname");
                        String profilePicUrl = document.getString("profilePicUrl");

                        Picasso.get().load(profilePicUrl).into(profile_image);
                        nameInput.setText(name);

                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    } else {
                        Log.d("Firestore Log", "No such document");
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "No Profile", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Firestore Log", "get failed with ", task.getException());
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Handle image upload
        profile_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        //Handle updating database
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setCancelable(true);
                builder.setTitle("Are you sure you want to continue?");
                builder.setMessage("This will change your profile details");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                updateProgressDialog = new ProgressDialog(Profile.this);
                                updateProgressDialog.setCancelable(false);
                                updateProgressDialog.setMessage("Updating Profile Data...");
                                updateProgressDialog.show();

                                Map<String, Object> data = new HashMap<>();
                                data.put("nickname", nameInput.getText().toString());
                                if(downloadUrl != null){
                                    data.put("profilePicUrl", downloadUrl.toString());
                                }

                                db.collection("Users").document(userId).update(data);

                                if(updateProgressDialog.isShowing()){
                                    updateProgressDialog.dismiss();
                                }

                                Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        //Handle Logout
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
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
                                        startActivity(new Intent(Profile.this, Signin.class));
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




        //Bottom navigation bar
        BottomNavigationView navbar = findViewById(R.id.bottom_navigation);
        navbar.setSelectedItemId(R.id.profile);

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
                        startActivity(new Intent(getApplicationContext(), ForumMain.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });
    }

    private void choosePicture() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 ){
            imageUri = data.getData();
            profile_image.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Uploading Image...");
        progress.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+ randomKey);

// Register observers to listen for when the download is done or if it fails
        riversRef.putFile(imageUri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                progress.dismiss();
                Toast.makeText(Profile.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progress.dismiss();

                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        downloadUrl = uri;
                    }
                });

                Toast.makeText(Profile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100* snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progress.setMessage("Percentage: " + (int) progressPercent + "%" );
            }
        });
    }
}