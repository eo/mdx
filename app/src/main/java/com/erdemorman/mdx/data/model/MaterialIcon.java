package com.erdemorman.mdx.data.model;

import com.google.gson.annotations.SerializedName;

public class MaterialIcon {
    @SerializedName("name")
    private final String mName;

    public MaterialIcon(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
