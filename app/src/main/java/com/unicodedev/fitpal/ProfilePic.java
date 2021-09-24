package com.unicodedev.fitpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.unicodedev.fitpal.forum.QuestionModal;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfilePic extends AppCompatActivity {
    ImageView profile_pic;
    Button submit_btn;
    CardView profile_card;
    Uri imageUri, downloadUrl;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        profile_pic = findViewById(R.id.profile_image);
        submit_btn = findViewById(R.id.submit_btn);
        profile_card = findViewById(R.id.profile_card);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        if(user!=null){
            submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(downloadUrl!=null){

                        String userid = user.getUid();

                        db.collection("Users").document(userid)
                                .update("profilePicUrl", downloadUrl.toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("FirebaseLog", "DocumentSnapshot successfully written!");
                                        Intent i = new Intent(ProfilePic.this, HomeActivity.class);
                                        startActivity(i);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("FirebaseLog", "Error writing document", e);
                                    }
                                });
                    } else{
                        Toast.makeText(ProfilePic.this, "Image not Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            profile_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choosePicture();
                }
            });
        }


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
            assert data != null;
            imageUri = data.getData();
            profile_pic.setImageURI(imageUri);
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
                Toast.makeText(ProfilePic.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(ProfilePic.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
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