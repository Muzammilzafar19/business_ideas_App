package com.business_idea.business_ideas_app.DataClasses;

public class ChatListData {
    private String ChaterName;
    private String ChaterType;
    private String ChaterImg;

    public ChatListData(String chaterName, String chaterType, String chaterImg) {
        ChaterName = chaterName;
        ChaterType = chaterType;
        ChaterImg = chaterImg;
    }

    public String getChaterName() {
        return ChaterName;
    }

    public void setChaterName(String chaterName) {
        ChaterName = chaterName;
    }

    public String getChaterType() {
        return ChaterType;
    }

    public void setChaterType(String chaterType) {
        ChaterType = chaterType;
    }

    public String getChaterImg() {
        return ChaterImg;
    }

    public void setChaterImg(String chaterImg) {
        ChaterImg = chaterImg;
    }
}
