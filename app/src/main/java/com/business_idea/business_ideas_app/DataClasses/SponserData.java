package com.business_idea.business_ideas_app.DataClasses;

public class SponserData {
    private String SponserName;
    private String SponserImgUri;
    private String SponserType;

    public SponserData(String sponserName, String sponserImgUri, String sponserType) {
        SponserName = sponserName;
        SponserImgUri = sponserImgUri;
        SponserType = sponserType;
    }

    public String getSponserName() {
        return SponserName;
    }

    public void setSponserName(String sponserName) {
        SponserName = sponserName;
    }

    public String getSponserImgUri() {
        return SponserImgUri;
    }

    public void setSponserImgUri(String sponserImgUri) {
        SponserImgUri = sponserImgUri;
    }

    public String getSponserType() {
        return SponserType;
    }

    public void setSponserType(String sponserType) {
        SponserType = sponserType;
    }
}
