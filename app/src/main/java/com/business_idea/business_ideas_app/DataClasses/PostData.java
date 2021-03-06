package com.business_idea.business_ideas_app.DataClasses;

public class PostData {
    private String Title;
    private String AuthorName;
    private String Blog;
    private String BlogImageUri;
    private String Uid;
    private String UserImage;
    private String PostDateTime;
    private String Rating;
    private String PushId;
    private String IdeaCategory;
    private String Investment;

    public PostData(String title, String authorName, String blog, String blogImageUri, String uid, String userImage, String postDateTime,String rating,String pushId , String ideacategory,String investment) {
        Title = title;
        AuthorName = authorName;
        Blog = blog;
        BlogImageUri = blogImageUri;
        Uid = uid;
        UserImage = userImage;
        PostDateTime = postDateTime;
        Rating = rating;
        PushId=pushId;
        IdeaCategory=ideacategory;
        Investment=investment;


    }

    public String getIdeaCategory() {
        return IdeaCategory;
    }

    public void setIdeaCategory(String ideaCategory) {
        IdeaCategory = ideaCategory;
    }

    public String getInvestment() {
        return Investment;
    }

    public void setInvestment(String investment) {
        Investment = investment;
    }

    public String getPushId() {
        return PushId;
    }

    public void setPushId(String pushId) {
        PushId = pushId;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getBlog() {
        return Blog;
    }

    public void setBlog(String blog) {
        Blog = blog;
    }

    public String getBlogImageUri() {
        return BlogImageUri;
    }

    public void setBlogImageUri(String blogImageUri) {
        BlogImageUri = blogImageUri;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public String getPostDateTime() {
        return PostDateTime;
    }

    public void setPostDateTime(String postDateTime) {
        PostDateTime = postDateTime;
    }
}
