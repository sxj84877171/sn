package com.sunvote.txpad.base;

import com.sunvote.util.LogUtil;

import rx.Subscriber;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:观察者
 */
public class BaseSubscriber<T> extends Subscriber<T> {
    /**
     */
    @Override
    public void onCompleted() {

    }

    /**
     * Notifies the Observer that the {@link } has experienced an error condition.
     * <p>
     * If the {@link } calls this method, it will not thereafter call {@link #onNext} or
     * {@link #onCompleted}.
     *
     * @param e the exception encountered by the Observable
     */
    @Override
    public void onError(Throwable e) {
        LogUtil.e("BaseSubscriber",e);
        onCompleted();
    }

    /**
     * Provides the Observer with a new item to observe.
     * <p>
     * The {@link } may call this method 0 or more times.
     * <p>
     * The {@code Observable} will not call this method again after it calls either {@link #onCompleted} or
     * {@link #onError}.
     *
     * @param t the item emitted by the Observable
     */
    @Override
    public void onNext(T t){
    }
}
