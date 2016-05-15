package com.erdemorman.mdx.data.model;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DrawableRes;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public class MaterialIcon {
    @SerializedName("name")
    private final String mName;
    private final transient Integer mDrawableId;

    public MaterialIcon(String name, Integer drawableId) {
        mName = name;
        mDrawableId = drawableId;
    }

    public String getName() {
        return mName;
    }

    public @DrawableRes Integer getDrawableId() {
        return mDrawableId;
    }


    public static class GsonDeserializer implements JsonDeserializer<MaterialIcon> {
        private final Context mContext;
        private final Resources mResources;

        public GsonDeserializer(Context context) {
            mContext = context;
            mResources = context.getResources();
        }

        @Override
        public MaterialIcon deserialize(JsonElement json, Type typeOfT,
                                        JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject jsonObject = json.getAsJsonObject();

            String iconName = jsonObject.get("name").getAsString();
            Integer iconDrawableId = getDrawableId(iconName);

            return new MaterialIcon(iconName, iconDrawableId);
        }

        private Integer getDrawableId(String iconName) {
            String resourceIdName = getResourceIdName(iconName);

            return mResources.getIdentifier(resourceIdName, "drawable", mContext.getPackageName());
        }

        private String getResourceIdName(String iconName) {
            return "ic_" + iconName.replace(' ', '_');
        }
    }
}
