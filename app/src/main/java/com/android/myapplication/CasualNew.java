package com.android.myapplication;

public class CasualNew {
    String title,subtitle, information, photo, url;

    public CasualNew() {
        this.title = "title";
        this.subtitle = "subtitle";
        this.information = "information";
        this.photo = "photo";
        this.url = "url";
    }
    public CasualNew(String title, String subtitle, String information, String photo, String url) {
        this.title = title;
        this.subtitle = subtitle;
        this.information = information;
        this.photo = photo;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
