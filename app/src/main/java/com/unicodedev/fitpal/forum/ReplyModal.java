package com.unicodedev.fitpal.forum;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class ReplyModal {
     String authorID, questionID, text, id;
     Date publishedOn;

    public ReplyModal(){}

    public ReplyModal(String authorID, String questionID, String text, String id, Date publishedOn) {
        this.authorID = authorID;
        this.questionID = questionID;
        this.text = text;
        this.id = id;
        this.publishedOn = publishedOn;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }


    public String getTimeAgo(){
        PrettyTime p = new PrettyTime();
        return p.format(publishedOn);

    }

}
