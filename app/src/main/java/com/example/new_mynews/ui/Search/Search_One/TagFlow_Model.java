package com.example.new_mynews.ui.Search.Search_One;

import com.github.hymanme.tagflowlayout.bean.TagBean;

public class TagFlow_Model extends TagBean {
    String text;

    public TagFlow_Model(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
