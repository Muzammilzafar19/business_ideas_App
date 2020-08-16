package com.business_idea.business_ideas_app.DataClasses;

public class SponserData {
    private String SponserName;
    private String SponserImgUri;
    private String SponserType;
    private String SponserId;

    public SponserData(String sponserName, String sponserImgUri, String sponserType,String sponserid) {
        SponserName = sponserName;
        SponserImgUri = sponserImgUri;
        SponserType = sponserType;
        SponserId=sponserid;
    }

    public String getSponserId() {
        return SponserId;
    }

    public void setSponserId(String sponserId) {
        SponserId = sponserId;
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
