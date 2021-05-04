package com.example.new_mynews.YouTubeData;

public class YouTube_Model {
    String image_message, name_message, text_message, time_message, name_link, thumb_Num_message;

    public YouTube_Model(String image_message, String name_message, String text_message, String time_message, String name_link, String thumb_Num_message) {
        this.image_message = image_message;
        this.name_message = name_message;
        this.text_message = text_message;
        this.time_message = time_message;
        this.name_link = name_link;
        this.thumb_Num_message = thumb_Num_message;

    }

    public String getName_link() {
        return name_link;
    }

    public void setName_link(String name_link) {
        this.name_link = name_link;
    }

    public String getThumb_Num_message() {
        return thumb_Num_message;
    }

    public void setThumb_Num_message(String thumb_Num_message) {
        this.thumb_Num_message = thumb_Num_message;
    }

    public String getImage_message() {
        return image_message;
    }

    public void setImage_message(String image_message) {
        this.image_message = image_message;
    }

    public String getName_message() {
        return name_message;
    }

    public void setName_message(String name_message) {
        this.name_message = name_message;
    }

    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    public String getTime_message() {
        return time_message;
    }

    public void setTime_message(String time_message) {
        this.time_message = time_message;
    }
}
