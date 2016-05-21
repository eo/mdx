package com.erdemorman.mdx.ui.icons;

import android.text.TextUtils;

import com.erdemorman.mdx.data.DataManager;
import com.erdemorman.mdx.data.model.MaterialIcon;
import com.erdemorman.mdx.data.model.MaterialIconGroup;
import com.erdemorman.mdx.ui.base.BasePresenter;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class IconsPresenter extends BasePresenter<IconsView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;
    private List<MaterialIconGroup> mIconGroupsCache;

    @Inject
    public IconsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void detachView() {
        super.detachView();

        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void loadIcons() {
        loadIcons(null);
    }

    public void loadIcons(final CharSequence query) {
        checkViewAttached();
        if (mIconGroupsCache == null) {
            mSubscription = mDataManager.getMaterialIconGroups()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<MaterialIconGroup>>() {
                        @Override
                        public void call(List<MaterialIconGroup> iconGroups) {
                            mIconGroupsCache = iconGroups;

                            if (!TextUtils.isEmpty(query)) {
                                filterAndLoadIcons(query);
                            } else {
                                getView().showIconGroups(iconGroups);
                            }
                        }
                    });
        } else if (!TextUtils.isEmpty(query)) {
            filterAndLoadIcons(query);
        } else {
            getView().showIconGroups(mIconGroupsCache);
        }
    }

    private void filterAndLoadIcons(final CharSequence query) {
        Observable.from(mIconGroupsCache)
                .map(new Func1<MaterialIconGroup, MaterialIconGroup>() {
                    @Override
                    public MaterialIconGroup call(MaterialIconGroup materialIconGroup) {
                        final String lowercaseQuery = String.valueOf(query).toLowerCase();
                        MaterialIconGroup filteredIconGroup = materialIconGroup;

                        if (!materialIconGroup.getName().toLowerCase().contains(lowercaseQuery)) {
                            List<MaterialIcon> filteredIcons = Lists.newArrayList(
                                    Collections2.filter(
                                            materialIconGroup.getIcons(),
                                            new Predicate<MaterialIcon>() {
                                                @Override
                                                public boolean apply(MaterialIcon icon) {
                                                    return icon.getName().contains(lowercaseQuery);
                                                }
                                            }
                                    )
                            );

                            filteredIconGroup = new MaterialIconGroup(materialIconGroup.getName(),
                                    filteredIcons);
                        }

                        return filteredIconGroup;
                    }
                })
                .filter(new Func1<MaterialIconGroup, Boolean>() {
                    @Override
                    public Boolean call(MaterialIconGroup materialIconGroup) {
                        return materialIconGroup.getIconCount() > 0;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MaterialIconGroup>>() {
                    @Override
                    public void call(List<MaterialIconGroup> iconGroups) {
                        getView().showIconGroups(iconGroups);
                    }
                });
    }
}
