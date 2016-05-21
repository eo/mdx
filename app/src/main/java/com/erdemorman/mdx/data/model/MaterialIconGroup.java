package com.erdemorman.mdx.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MaterialIconGroup implements Parcelable {
    @SerializedName("name")
    private final String mName;

    @SerializedName("icons")
    private final List<MaterialIcon> mIcons;

    public MaterialIconGroup(String name, List<MaterialIcon> icons) {
        mName = name;
        mIcons = icons;
    }

    public String getName() {
        return mName;
    }

    public List<MaterialIcon> getIcons() {
        return mIcons;
    }

    public int getIconCount() {
        return mIcons != null ? mIcons.size() : 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeTypedList(this.mIcons);
    }

    protected MaterialIconGroup(Parcel in) {
        this.mName = in.readString();
        this.mIcons = in.createTypedArrayList(MaterialIcon.CREATOR);
    }

    public static final Parcelable.Creator<MaterialIconGroup> CREATOR = new Parcelable.Creator<MaterialIconGroup>() {
        @Override
        public MaterialIconGroup createFromParcel(Parcel source) {
            return new MaterialIconGroup(source);
        }

        @Override
        public MaterialIconGroup[] newArray(int size) {
            return new MaterialIconGroup[size];
        }
    };
}
