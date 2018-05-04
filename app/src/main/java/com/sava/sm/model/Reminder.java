package com.sava.sm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Reminder implements Parcelable {
    private int mID;
    private String mTitle;
    private String mContent;
    private Date mDate;
    private  int mStatus;

    public Reminder() {
        this.mTitle = "CV";
        mStatus = 1;
    }

    public Reminder(int mID, String mTitle, String mContent, Date mDate, int mStatus) {
        this.mID = mID;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mDate = mDate;
        this.mStatus = mStatus;
    }

    public Reminder(String mTitle, String mContent, Date mDate) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mDate = mDate;
    }

    protected Reminder(Parcel in) {
        mID = in.readInt();
        mTitle = in.readString();
        mContent = in.readString();
        mStatus = in.readInt();
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mID);
        parcel.writeString(mTitle);
        parcel.writeString(mContent);
        parcel.writeInt(mStatus);
    }
}
