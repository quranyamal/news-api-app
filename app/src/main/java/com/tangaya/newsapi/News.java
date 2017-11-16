package com.tangaya.newsapi;

/**
 * Created by amal on 11/15/2017.
 */

public class News {
    private String imageSrc;
    private String title;
    private String content;

    public News(String imageSrc, String title, String content) {
        this.imageSrc = imageSrc;
        this.title = title;
        this.content = content;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}