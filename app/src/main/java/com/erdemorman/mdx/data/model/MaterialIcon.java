package com.erdemorman.mdx.data.model;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public class MaterialIcon implements Parcelable {
    @SerializedName("name")
    private final String mName;
    private final Integer mDrawableId;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeValue(this.mDrawableId);
    }

    protected MaterialIcon(Parcel in) {
        this.mName = in.readString();
        this.mDrawableId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<MaterialIcon> CREATOR = new Parcelable.Creator<MaterialIcon>() {
        @Override
        public MaterialIcon createFromParcel(Parcel source) {
            return new MaterialIcon(source);
        }

        @Override
        public MaterialIcon[] newArray(int size) {
            return new MaterialIcon[size];
        }
    };

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
