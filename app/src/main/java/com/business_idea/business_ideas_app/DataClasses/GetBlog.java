package com.business_idea.business_ideas_app.DataClasses;

public class GetBlog {
    private String title;
    private String author;
    private String blogtime;
    private  String blogwritten;
    private String imgblogger;
    private String imgblog;
    private String imglike;
    private String imgshare;

    public GetBlog(String title, String author, String blogtime, String blogwritten, String imgblogger, String imgblog, String imglike, String imgshare) {
        this.title = title;
        this.author = author;
        this.blogtime = blogtime;
        this.blogwritten = blogwritten;
        this.imgblogger = imgblogger;
        this.imgblog = imgblog;
        this.imglike = imglike;
        this.imgshare = imgshare;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBlogtime() {
        return blogtime;
    }

    public void setBlogtime(String blogtime) {
        this.blogtime = blogtime;
    }

    public String getBlogwritten() {
        return blogwritten;
    }

    public void setBlogwritten(String blogwritten) {
        this.blogwritten = blogwritten;
    }

    public String getImgblogger() {
        return imgblogger;
    }

    public void setImgblogger(String imgblogger) {
        this.imgblogger = imgblogger;
    }

    public String getImgblog() {
        return imgblog;
    }

    public void setImgblog(String imgblog) {
        this.imgblog = imgblog;
    }

    public String getImglike() {
        return imglike;
    }

    public void setImglike(String imglike) {
        this.imglike = imglike;
    }

    public String getImgshare() {
        return imgshare;
    }

    public void setImgshare(String imgshare) {
        this.imgshare = imgshare;
    }
}
