package com.unicodedev.fitpal.forum;



import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QuestionModal {
    String authorID, question, description, authorName,id;
    Date publishedOn;


    public QuestionModal(){}

    public QuestionModal(String authorID, String question, String description, Date publishedOn) {
        this.authorID = authorID;
        this.question = question;
        this.description = description;
        this.publishedOn = publishedOn;



    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTimeAgo(){
        PrettyTime p = new PrettyTime();
        return p.format(publishedOn);

    }





}
