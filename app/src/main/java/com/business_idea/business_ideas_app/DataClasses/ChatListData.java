package com.business_idea.business_ideas_app.DataClasses;

public class ChatListData {
    private String ChaterName;
    private String ChaterType;
    private String ChaterImg;
    private String ChaterId;

    public ChatListData(String chaterName, String chaterType, String chaterImg,String chaterid) {
        ChaterName = chaterName;
        ChaterType = chaterType;
        ChaterImg = chaterImg;
        ChaterId=chaterid;

    }

    public String getChaterId() {
        return ChaterId;
    }

    public void setChaterId(String chaterId) {
        ChaterId = chaterId;
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
