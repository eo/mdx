package com.erdemorman.mdx.ui.icons;

import com.erdemorman.mdx.data.DataManager;
import com.erdemorman.mdx.data.model.MaterialIcon;
import com.erdemorman.mdx.data.model.MaterialIconGroup;
import com.erdemorman.mdx.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class IconsPresenter extends BasePresenter<IconsView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

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
        checkViewAttached();
        mSubscription = mDataManager.getMaterialIconGroups()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MaterialIconGroup>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: show error
                    }

                    @Override
                    public void onNext(List<MaterialIconGroup> iconGroups) {
                        getView().showIconGroups(iconGroups);
                    }
                });
    }
}
