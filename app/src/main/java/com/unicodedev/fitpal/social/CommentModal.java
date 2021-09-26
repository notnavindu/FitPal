package com.unicodedev.fitpal.social;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class CommentModal {
    String authorId, text, id, imageURL, postId;
    Date publishedOn;

    public CommentModal() {

    }


    public CommentModal(String authorId, String text, Date publishedOn, String postId) {
        this.authorId = authorId;
        this.text = text;
        this.publishedOn = publishedOn;
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }


    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
