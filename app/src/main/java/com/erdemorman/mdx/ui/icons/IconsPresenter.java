package com.erdemorman.mdx.ui.icons;

import android.text.TextUtils;

import com.erdemorman.mdx.data.DataManager;
import com.erdemorman.mdx.data.model.MaterialIconGroup;
import com.erdemorman.mdx.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
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
    public void attachView(IconsView mvpView) {
        super.attachView(mvpView);
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
                .filter(new Func1<MaterialIconGroup, Boolean>() {
                    @Override
                    public Boolean call(MaterialIconGroup materialIconGroup) {
                        return materialIconGroup.getName().toLowerCase().contains(
                                String.valueOf(query).toLowerCase());
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
