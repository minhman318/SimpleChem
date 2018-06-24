package com.manminh.simplechem.search;

import android.os.Parcel;
import android.os.Parcelable;

public class Detail implements Parcelable {
    public String mName;
    public String mContent;

    public Detail(String name, String content) {
        mName = name;
        mContent = content;
    }

    protected Detail(Parcel in) {
        mName = in.readString();
        mContent = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mContent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Detail> CREATOR = new Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel in) {
            return new Detail(in);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };
}
