package com.example.ruhaiwen.funnylife.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by ruhaiwen on 15-4-7.
 */
public class Collection extends BmobObject{

    public static final String TAG = "Collection";

    private User collector;//收藏者

    private Publication publication;//收藏的条目

    public User getCollector() {
        return collector;
    }

    public void setCollector(User collector) {
        this.collector = collector;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }
}
