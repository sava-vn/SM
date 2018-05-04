package com.sava.sm.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by Mr.Sang on 1/20/2018.
 */

public class MyNote implements Parcelable {
    public static final int KIEU_NGAY_THANG_NAM = 1;
    public static final int KIEU_THU_NGAY_THANG = 2;
    private int mID;
    private String mTitle;
    private String mContent;
    private String mDate;
    private boolean status;

    public MyNote() {
    }

    public MyNote(String mTitle, String mContent, Date date, boolean status) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date);
        String day          = (String) DateFormat.format("dd",   date);
        String monthString  = (String) DateFormat.format("MMM",  date);
        String monthNumber  = (String) DateFormat.format("MM",   date);
        String year         = (String) DateFormat.format("yyyy", date);
        this.mDate = day + "/" +  monthNumber + "/" + year + "/" + dayOfTheWeek + "/" + monthString;
        this.status = status;
    }

    protected MyNote(Parcel in) {
        mID = in.readInt();
        mTitle = in.readString();
        mContent = in.readString();
        mDate = in.readString();
        status = in.readByte() != 0;
    }

    public static final Creator<MyNote> CREATOR = new Creator<MyNote>() {
        @Override
        public MyNote createFromParcel(Parcel in) {
            return new MyNote(in);
        }

        @Override
        public MyNote[] newArray(int size) {
            return new MyNote[size];
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

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
        parcel.writeString(mDate);
        parcel.writeByte((byte) (status ? 1 : 0));
    }

    public String getDateString(int typeDate) {
        StringTokenizer stringTokenizer = new StringTokenizer(mDate, "/");
        String dayOfMonth = stringTokenizer.nextToken();
        String month = stringTokenizer.nextToken();
        String year = stringTokenizer.nextToken();
        String dayOfWeek = stringTokenizer.nextToken();
        String sMonth = stringTokenizer.nextToken();
        switch (typeDate) {
            case KIEU_NGAY_THANG_NAM:
                return dayOfMonth + "/" + month + "/" + year;
            case KIEU_THU_NGAY_THANG:
                return dayOfWeek + " " + dayOfMonth + "," + sMonth;
        }
        return "";
    }
}
