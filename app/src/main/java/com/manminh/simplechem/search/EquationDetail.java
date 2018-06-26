package com.manminh.simplechem.search;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * One of equation details
 * Ex: mName: "Hiện tượng"; mContent: "Có khí thoát ra"
 */
public class EquationDetail implements Parcelable {
    public String mName;
    public String mContent;

    public EquationDetail(String name, String content) {
        mName = name;
        mContent = content;
    }

    protected EquationDetail(Parcel in) {
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

    public static final Creator<EquationDetail> CREATOR = new Creator<EquationDetail>() {
        @Override
        public EquationDetail createFromParcel(Parcel in) {
            return new EquationDetail(in);
        }

        @Override
        public EquationDetail[] newArray(int size) {
            return new EquationDetail[size];
        }
    };
}
