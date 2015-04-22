package com.example.ruhaiwen.funnylife.entity;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * @author kevin
 * @date 2015-4-10
 */
public class Publication extends BmobObject{

    /**
     * 发布条目的用户
     */
	private User author;
    /**
     * 发布条目的描述
     */
	private String content;
    /**
     * 发布条目里图片的名称
     */
    private String pictureName;
    /**
     * 发布条目里图片的url
     */
    private String pictureUrl;
    /**
     * 发布条目的点赞数
     */
	private int loveNum;
    /**
     * 发布条目的评论数
     */
	private int commentNum;
    /**
     * 发布条目是否为当前用户已点赞的（数据拉取到本地后根据当前用户的信息重设）
     */
    private boolean isMylove;
    /**
     * 发布条目是否为当前用户已收藏的（数据拉取后根据当前用户的信息重设）
     */
    private boolean isMyCollection;
    /**
     * 关联关系,关联评论
     */
    private BmobRelation comment;

    public BmobRelation getComment() {
        return comment;
    }

    public void setComment(BmobRelation comment) {
        this.comment = comment;
    }

    public boolean isMyCollection() {
        return isMyCollection;
    }

    public void setMyCollection(boolean isMyCollection) {
        this.isMyCollection = isMyCollection;
    }

    public boolean isMylove() {
        return isMylove;
    }

    public void setMylove(boolean isMylove) {
        this.isMylove = isMylove;
    }

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

}
