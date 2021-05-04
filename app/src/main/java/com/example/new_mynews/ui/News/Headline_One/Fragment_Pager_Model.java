package com.example.new_mynews.ui.News.Headline_One;

public class Fragment_Pager_Model {
    String title, urlToImage, url;

    public Fragment_Pager_Model(String title, String urlToImage, String url) {

        this.title = title;
        this.urlToImage = urlToImage;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
