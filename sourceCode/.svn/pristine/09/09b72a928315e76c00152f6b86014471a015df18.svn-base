package com.sunvote.txpad.base;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者： 孙向锦
 * 时间： 2016/12/6
 * 版本：1.0.0.0
 * 邮编：410000
 * 程序功能：
 * 用于管理RxBus的事件和Rxjava相关代码的生命周期处理
 */
public class RxManager {

    /**
     * RxBus 单例
     */
    public RxBus mRxBus = RxBus.$();

    /**
     * 管理观察源
     */
    private Map<String, Observable<?>> mObservables = new HashMap<>();

    /**
     * 管理订阅者者
     */
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    /**
     * 注册事件
     *
     * @param eventName 事件名
     * @param action1   对应事件的Action
     */
    public void on(String eventName, Action1<Object> action1) {
        Observable<?> mObservable = mRxBus.register(eventName);
        mObservables.put(eventName, mObservable);
        mCompositeSubscription.add(mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }));
    }

    /**
     * 添加订阅者
     *
     * @param m
     */
    public void add(Subscription m) {
        mCompositeSubscription.add(m);
    }

    /**
     * 清除订阅者和事件信息
     */
    public void clear() {
        mCompositeSubscription.unsubscribe();// 取消订阅
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet())
            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除观察
    }

    /**
     * 提交发生的某种事件，并且把发生的参数传入
     *
     * @param tag     事件标记
     * @param content 事件发生内容
     */
    public void post(Object tag, Object content) {
        mRxBus.post(tag, content);
    }
}
