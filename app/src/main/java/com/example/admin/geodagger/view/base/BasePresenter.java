package com.example.admin.geodagger.view.base;

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);

    void detachView();
}
