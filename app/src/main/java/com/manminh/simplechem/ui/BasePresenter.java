package com.manminh.simplechem.ui;

/**
 * Base class of presenters
 *
 * @param <V> is the view type
 */
public abstract class BasePresenter<V extends IView> implements IPresenter<V> {

    // The view, is actually an activity or fragment
    private V mView;

    @Override
    public final void attachView(V view) {
        mView = view;
    }

    @Override
    public final void detachView() {
        mView = null;
    }

    public final V getView() {
        return mView;
    }

    public final boolean isAttached() {
        return mView != null;
    }
}
