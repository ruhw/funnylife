package com.example.ruhaiwen.funnylife.entity;

import cn.bmob.v3.BmobObject;


/**
 * @author kevin
 * @date 2015-4-10
 */

public class Comment extends BmobObject{

    /**
     * 评论的用户
     */
	private User commentator;
    /**
     * 评论所属的发布条目
     */
    private Publication publication;
    /**
     * 评论的内容
     */
	private String commentContent;

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public User getCommentator() {
        return commentator;
    }

    public void setCommentator(User commentator) {
        this.commentator = commentator;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
