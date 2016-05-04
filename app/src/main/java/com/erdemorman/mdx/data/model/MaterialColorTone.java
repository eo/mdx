package com.erdemorman.mdx.data.model;

import com.google.gson.annotations.SerializedName;

public class MaterialColorTone {
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
}
