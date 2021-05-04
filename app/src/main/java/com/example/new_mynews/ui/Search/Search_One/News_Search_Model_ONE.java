package com.example.new_mynews.ui.Search.Search_One;

public class News_Search_Model_ONE {
    String changeDate, searchTitle, pubDate, link, source;

    public News_Search_Model_ONE(String changeDate, String searchTitle, String pubDate, String link, String source) {
        this.changeDate = changeDate;
        this.searchTitle = searchTitle;
        this.pubDate = pubDate;
        this.link = link;
        this.source = source;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
