package com.erdemorman.mdx.data;

import com.erdemorman.mdx.data.local.AssetDataService;
import com.erdemorman.mdx.data.model.MaterialColor;
import com.erdemorman.mdx.data.model.MaterialIconGroup;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DataManager {
    private final AssetDataService mAssetDataService;

    @Inject
    public DataManager(AssetDataService assetDataService) {
        mAssetDataService = assetDataService;
    }

    public Observable<List<MaterialColor>> getMaterialColors() {
        Type type = new TypeToken<List<MaterialColor>>(){}.getType();

        return mAssetDataService.fromJsonToObservable("material-colors.json", type);
    }

    public Observable<List<MaterialIconGroup>> getMaterialIconGroups() {
        Type type = new TypeToken<List<MaterialIconGroup>>(){}.getType();

        return mAssetDataService.fromJsonToObservable("material-icon-groups.json", type);
    }
}
