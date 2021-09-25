package com.unicodedev.fitpal.social;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class PostModal {
    String authorId, title, authorName, id, imageURL;
    Date publishedOn;

    public PostModal(){}

    public PostModal(String authorId, String title, String imageURL, Date publishedOn) {
        this.authorId = authorId;
        this.title = title;
        this.imageURL = imageURL;
        this.publishedOn = publishedOn;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
