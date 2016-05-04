package com.erdemorman.mdx.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MaterialColor {
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
}
