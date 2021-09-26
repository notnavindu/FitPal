package com.unicodedev.fitpal.social;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.unicodedev.fitpal.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CreatePost extends AppCompatActivity {

    Button selectImage;
    Button createPost;
    ImageView imagePreview;
    ProgressBar pb;
    EditText captionET;


    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String path;
    public Uri imageData;

    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_new_post);

        selectImage = findViewById(R.id.choose_photo_btn);
        createPost = findViewById(R.id.create_post_btn);
        imagePreview = findViewById(R.id.IVPreviewImage);
        captionET = findViewById(R.id.captionInput);
        pb = findViewById(R.id.progress);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        createPost.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    path = "social/" + user.getUid() + "/" + UUID.randomUUID();
                    StorageReference stref = storage.getReference(path);

                    String caption = captionET.getText().toString();

                    if (imageData == null) {
                        Toast.makeText(CreatePost.this, "Please Select an image", Toast.LENGTH_SHORT).show();
                    } else {
                        pb.setVisibility(View.VISIBLE);
                        createPost.setEnabled(false);

                        UploadTask task = stref.putFile(imageData);
                        task.addOnSuccessListener(CreatePost.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(CreatePost.this, "DONE", Toast.LENGTH_SHORT).show();

                            }
                        });

                        Task<Uri> getUrlTask = task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }
                                return stref.getDownloadUrl();
                            }
                        });

                        getUrlTask.addOnCompleteListener(CreatePost.this, new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUrl = task.getResult();

                                    Map<String, Object> data = new HashMap<>();
                                    List<String> likes = new ArrayList<>();
                                    String authorId = user.getUid();
                                    Date date = java.util.Calendar.getInstance().getTime();

                                    data.put("authorId", authorId);
                                    data.put("imageURL", downloadUrl.toString());
                                    data.put("title", caption);
                                    data.put("likes", likes);
                                    data.put("publishedOn", date);

                                    Log.d("TESTING", "ADOOO");
                                    Log.d("TESTING", data.toString());

                                    db.collection("Social").document()
                                            .set(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("FirebaseLog", "DocumentSnapshot successfully written!");
                                                    pb.setVisibility(View.INVISIBLE);
                                                    createPost.setEnabled(true);
                                                    Toast.makeText(CreatePost.this, "Successfully Posted!", Toast.LENGTH_SHORT).show();

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
                    }
                }


            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                imageData = selectedImageUri;

                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imagePreview.setImageURI(selectedImageUri);
                }
            }
        }
    }

    private boolean validateFields() {
        if (captionET.length() == 0) {
            captionET.setError("Caption is required");
            return false;
        }
        return true;
    }
}