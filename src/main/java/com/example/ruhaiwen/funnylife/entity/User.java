package com.example.ruhaiwen.funnylife.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;


/**
 * @author kevin
 * @date 2015-4-10
 */

public class User extends BmobUser{

	public static final String TAG = "User";

    /**
     * 用户头像url
     */
    private String userImageUrl;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 签名
     */
	private String signature;
    /**
     * 性别
     */
    private String sex;
    /**
     * 关联关系,发布的条目
     */
    private BmobRelation publications;
    /**
     * 关联关系,已收藏的条目
     */
    private BmobRelation collections;
    /**
     * 关联关系,已点赞的条目
     */
    private BmobRelation myLoves;
    /**
     * 关联关系,已评论的条目
     */
    private BmobRelation myComments;

    public BmobRelation getMyLoves() {
        return myLoves;
    }

    public void setMyLoves(BmobRelation myLoves) {
        this.myLoves = myLoves;
    }

    public BmobRelation getMyComments() {
        return myComments;
    }

    public void setMyComments(BmobRelation myComments) {
        this.myComments = myComments;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BmobRelation getCollections() {
        return collections;
    }

    public void setCollections(BmobRelation collections) {
        this.collections = collections;
    }

    public BmobRelation getPublications() {
        return publications;
    }

    public void setPublications(BmobRelation publications) {
        this.publications = publications;
    }
}
