package com.erdemorman.mdx.ui.base;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the BaseView type that wants to be attached with.
 */
public interface Presenter<T extends BaseView> {

    void attachView(T mvpView);

    void detachView();
}
