package com.erdemorman.mdx.data.local;

import android.content.Context;

import com.erdemorman.mdx.data.model.MaterialColor;
import com.erdemorman.mdx.data.model.MaterialIconGroup;
import com.erdemorman.mdx.injection.ActivityContext;
import com.erdemorman.mdx.injection.ApplicationContext;
import com.erdemorman.mdx.util.AssetUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class AssetDataRepository {
    private static final String JSON_FILE_COLORS = "material-colors.json";
    private static final String JSON_FILE_ICON_GROUPS = "material-icon-groups.json";

    private Context mContext;

    @Inject
    public AssetDataRepository(@ActivityContext Context context) {
        mContext = context;
    }

    public Observable<List<MaterialColor>> getMaterialColors() {
        Type type = new TypeToken<List<MaterialColor>>(){}.getType();

        return AssetUtil.fromJsonToObservable(mContext, JSON_FILE_COLORS, type);
    }

    public Observable<List<MaterialIconGroup>> getMaterialIconGroups() {
        Type type = new TypeToken<List<MaterialIconGroup>>(){}.getType();

        return AssetUtil.fromJsonToObservable(mContext, JSON_FILE_ICON_GROUPS, type);
    }

}
