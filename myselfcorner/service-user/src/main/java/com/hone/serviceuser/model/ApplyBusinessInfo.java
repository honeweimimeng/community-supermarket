package com.hone.serviceuser.model;

public class ApplyBusinessInfo extends BusinessInfo{
    private String uid;
    private String phonenumber;
    private String licenseurl;
    private String idcardtopurl;
    private String idcardbottomurl;
    private String longitude;
    private String latitude;
    private String iconurl;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setIdcardbottomurl(String idcardbottomurl) {
        this.idcardbottomurl = idcardbottomurl;
    }

    public void setIdcardtopurl(String idcardtopurl) {
        this.idcardtopurl = idcardtopurl;
    }

    public void setLicenseurl(String licenseurl) {
        this.licenseurl = licenseurl;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getUid() {
        return uid;
    }

    public String getIdcardbottomurl() {
        return idcardbottomurl;
    }

    public String getIdcardtopurl() {
        return idcardtopurl;
    }

    public String getLicenseurl() {
        return licenseurl;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getIconurl() {
        return iconurl;
    }
}