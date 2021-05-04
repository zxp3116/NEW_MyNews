package com.example.new_mynews.ui.News.Ｈeadline_Three;

public class News_Headline_Model_THREE {
    String image_Headline, title_Headline, time_Headline, url;

    public News_Headline_Model_THREE(String title_Headline, String image_Headline, String time_Headline, String url) {
        this.image_Headline = image_Headline;
        this.title_Headline = title_Headline;
        this.time_Headline = time_Headline;
        this.url = url;

    }

    public String getImage_Headline() {
        return image_Headline;
    }

    public void setImage_Headline(String image_Headline) {
        this.image_Headline = image_Headline;
    }

    public String getTitle_Headline() {
        return title_Headline;
    }

    public void setTitle_Headline(String title_Headline) {
        this.title_Headline = title_Headline;
    }

    public String getTime_Headline() {
        return time_Headline;
    }

    public void setTime_Headline(String time_Headline) {
        this.time_Headline = time_Headline;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
