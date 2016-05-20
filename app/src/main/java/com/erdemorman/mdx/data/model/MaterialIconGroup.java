package com.erdemorman.mdx.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MaterialIconGroup {
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
}
