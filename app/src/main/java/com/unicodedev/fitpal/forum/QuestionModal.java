package com.unicodedev.fitpal.forum;

import java.util.Date;

public class QuestionModal {
    String authorID, question, description;
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
}
