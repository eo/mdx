package com.erdemorman.mdx.ui.base;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the view that
 * can be accessed from the children classes by calling getView().
 */
public class BasePresenter<T extends BaseView> implements Presenter<T> {
    private T mView;

    @Override
    public void attachView(T mvpView) {
        mView = mvpView;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    public T getView() {
        return mView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(BaseView) before" +
                    " requesting data to the Presenter");
        }
    }
}
