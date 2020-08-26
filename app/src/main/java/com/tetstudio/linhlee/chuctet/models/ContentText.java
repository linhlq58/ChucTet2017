package com.tetstudio.linhlee.chuctet.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lequy on 1/18/2017.
 */

public class ContentText implements Parcelable {
    private int id;
    private int categoryId;
    private String content;

    public ContentText(int id, int categoryId, String content) {
        this.id = id;
        this.categoryId = categoryId;
        this.content = content;
    }

    public ContentText() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(categoryId);
        dest.writeString(content);
    }

    public static final Parcelable.Creator<ContentText> CREATOR
            = new Parcelable.Creator<ContentText>() {
        public ContentText createFromParcel(Parcel in) {
            return new ContentText(in);
        }

        public ContentText[] newArray(int size) {
            return new ContentText[size];
        }
    };

    private ContentText(Parcel in) {
        id = in.readInt();
        categoryId = in.readInt();
        content = in.readString();
    }
}
