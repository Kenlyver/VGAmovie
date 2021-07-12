package com.example.vgamovie;

import android.graphics.drawable.Drawable;

public class  CustomPoster {
    private String urlmask;
    private String urlImage;
    private String title;
    private String time;

    public CustomPoster(String urlmask,String urlImage, String title, String time) {
        this.urlmask = urlmask;
        this.title = title;
        this.urlImage = urlImage;
        this.time = time;
    }


    public String getUrl() {
        return urlImage;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.urlImage = url;
    }

    public String getMask() {
        return urlmask;
    }

    public String getTime() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMask(String mask) {
        this.urlmask = mask;
    }

    public void setTime(String title) {
        this.time = time;
    }
}
