package com.example.ruhaiwen.funnylife.entity;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Publication extends BmobObject{


	private User author;
	private String content;
    private String pictureName;
    private String pictureUrl;
	private int loveNum;
	private int commentNum;
	private BmobRelation collectors;//收藏者


    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPictureName(String pictureName){
        this.pictureName = pictureName;
    }

    public String getPictureName(){
        return pictureName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getLoveNum() {
        return loveNum;
    }

    public void setLoveNum(int loveNum) {
        this.loveNum = loveNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public BmobRelation getCollectors() {
        return collectors;
    }

    public void setCollectors(BmobRelation collectors) {
        this.collectors = collectors;
    }
}
