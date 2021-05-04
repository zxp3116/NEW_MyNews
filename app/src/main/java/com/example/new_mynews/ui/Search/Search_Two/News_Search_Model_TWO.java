package com.example.new_mynews.ui.Search.Search_Two;

public class News_Search_Model_TWO {
    String changeDate,titile,image_URL,id,time;
    public News_Search_Model_TWO(String changeDate,String titile,String image_URL,String id,String time){
        this.changeDate = changeDate;
        this.titile = titile;
        this.image_URL = image_URL;
        this.id = id;
        this.time = time;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getImage_URL() {
        return image_URL;
    }

    public void setImage_URL(String image_URL) {
        this.image_URL = image_URL;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
