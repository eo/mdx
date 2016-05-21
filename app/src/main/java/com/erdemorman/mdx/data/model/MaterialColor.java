package com.erdemorman.mdx.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MaterialColor implements Parcelable {
    @SerializedName("name")
    private final String mName;

    @SerializedName("tones")
    private final List<MaterialColorTone> mTones;

    public MaterialColor(String name, List<MaterialColorTone> tones) {
        mName = name;
        mTones = tones;
    }

    public String getName() {
        return mName;
    }

    public List<MaterialColorTone> getTones() {
        return mTones;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeTypedList(this.mTones);
    }

    protected MaterialColor(Parcel in) {
        this.mName = in.readString();
        this.mTones = in.createTypedArrayList(MaterialColorTone.CREATOR);
    }

    public static final Parcelable.Creator<MaterialColor> CREATOR = new Parcelable.Creator<MaterialColor>() {
        @Override
        public MaterialColor createFromParcel(Parcel source) {
            return new MaterialColor(source);
        }

        @Override
        public MaterialColor[] newArray(int size) {
            return new MaterialColor[size];
        }
    };
}
