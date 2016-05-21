package com.erdemorman.mdx.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MaterialColorTone implements Parcelable {
    @SerializedName("name")
    private final String mName;

    @SerializedName("color")
    private final String mColor;

    @SerializedName("whiteText")
    private final boolean mIsWhiteText;

    public MaterialColorTone(String name, String color) {
        this(name, color, false);
    }

    public MaterialColorTone(String name, String color, boolean isWhiteText) {
        mName = name;
        mColor = color;
        mIsWhiteText = isWhiteText;
    }

    public String getName() {
        return mName;
    }

    public String getColor() {
        return mColor;
    }

    public boolean isWhiteText() {
        return mIsWhiteText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mColor);
        dest.writeByte(this.mIsWhiteText ? (byte) 1 : (byte) 0);
    }

    protected MaterialColorTone(Parcel in) {
        this.mName = in.readString();
        this.mColor = in.readString();
        this.mIsWhiteText = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MaterialColorTone> CREATOR = new Parcelable.Creator<MaterialColorTone>() {
        @Override
        public MaterialColorTone createFromParcel(Parcel source) {
            return new MaterialColorTone(source);
        }

        @Override
        public MaterialColorTone[] newArray(int size) {
            return new MaterialColorTone[size];
        }
    };
}
