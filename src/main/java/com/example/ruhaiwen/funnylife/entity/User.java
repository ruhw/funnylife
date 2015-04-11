package com.example.ruhaiwen.funnylife.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;


/**
 * @author kingofglory email: kingofglory@yeah.net blog: http:www.google.com
 * @date 2014-3-14 TODO
 */

public class User extends BmobUser{

	public static final String TAG = "User";

    private String nickname;//昵称
	
	private String signature;//签名

    private String sex;//性别

    private BmobRelation publications;//发布的条目

    private BmobRelation favorites;//收藏的条目

    private BmobRelation relations;//相关的条目

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

    public BmobRelation getRelations() {
        return relations;
    }

    public void setRelations(BmobRelation relations) {
        this.relations = relations;
    }

    public BmobRelation getFavorites() {
        return favorites;
    }

    public void setFavorites(BmobRelation favorites) {
        this.favorites = favorites;
    }

    public BmobRelation getPublications() {
        return publications;
    }

    public void setPublications(BmobRelation publications) {
        this.publications = publications;
    }
}
