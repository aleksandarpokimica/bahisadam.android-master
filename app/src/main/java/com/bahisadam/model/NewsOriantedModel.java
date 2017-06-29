package com.bahisadam.model;

/**
 * @author GorkemKarayel on 22.05.2017.
 */

public class NewsOriantedModel{

    private String mId;
    private String mImageId;
    private String mTitle;

    public NewsOriantedModel(String mId, String mImageId, String mTitle) {
        this.mId = mId;
        this.mImageId = mImageId;
        this.mTitle = mTitle;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmImageId() {
        return mImageId;
    }

    public void setmImageId(String mImageId) {
        this.mImageId = mImageId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

}
