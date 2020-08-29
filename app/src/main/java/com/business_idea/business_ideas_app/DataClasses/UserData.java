package com.business_idea.business_ideas_app.DataClasses;

public class UserData {
    private String Uid;
    private String ImageUrl;
    private String UserName;
    private String UserEmail;
    private String UserAge;

    private String UserGender;
    private String UserType;
    private String UserAbout;
    private String UserCountry;

    public UserData(String uid,String userName, String userEmail, String userAge, String userGender, String userType, String userAbout,String imageurl,String usercountry) {
        Uid=uid;
        ImageUrl=imageurl;
        UserName = userName;
        UserEmail = userEmail;
        UserAge = userAge;
        UserGender=userGender;
        UserType = userType;
        UserAbout = userAbout;
        UserCountry=usercountry;
    }

    public String getUserCountry() {
        return UserCountry;
    }

    public void setUserCountry(String userCountry) {
        UserCountry = userCountry;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUserGender() {
        return UserGender;
    }

    public void setUserGender(String userGender) {
        UserGender = userGender;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserAge() {
        return UserAge;
    }

    public void setUserAge(String userAge) {
        UserAge = userAge;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getUserAbout() {
        return UserAbout;
    }

    public void setUserAbout(String userAbout) {
        UserAbout = userAbout;
    }
}
