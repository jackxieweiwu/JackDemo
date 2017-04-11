package zkrtdrone.zkrt.com.jackmvvm.util.rxutil;

import dji.thirdparty.rx.Observable;
import dji.thirdparty.rx.Subscriber;
import dji.thirdparty.rx.android.schedulers.AndroidSchedulers;
import dji.thirdparty.rx.functions.Action1;
import dji.thirdparty.rx.schedulers.Schedulers;
import zkrtdrone.zkrt.com.jackmvvm.rxbean.CommonRxTask;
import zkrtdrone.zkrt.com.jackmvvm.rxbean.IOTask;
import zkrtdrone.zkrt.com.jackmvvm.rxbean.MyOnSubscribe;
import zkrtdrone.zkrt.com.jackmvvm.rxbean.UITask;

/**
 * Created by jack_xie on 17-1-16.
 * Rxjava封装工具类
 */

public class RxjavaUtil {
    /**
     * 在ui线程中工作
     * @param uiTask
     */
    public static <T> void doInUIThread(UITask<T> uiTask) {
        Observable.just(uiTask)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UITask<T>>() {
                    @Override
                    public void call(UITask<T> uitask) {
                        uitask.doInUIThread();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    /*** 在IO线程中执行任务 * * @param <T> */
    public static <T> void doInIOThread(IOTask<T> ioTask) {
        Observable.just(ioTask)
                .observeOn(Schedulers.io())
                .subscribe(new Action1<IOTask<T>>() {
                    @Override
                    public void call(IOTask<T> ioTask) {
                        try {
                            ioTask.doInIOThread();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    /** * 执行Rx通用任务 (IO线程中执行耗时操作 执行完成调用UI线程中的方法) * * @param t * @param <T> */
    public static <T> void executeRxTask(CommonRxTask<T> t) {
        MyOnSubscribe<CommonRxTask<T>> onsubscribe = new MyOnSubscribe<CommonRxTask<T>>(t) {
            @Override
            public void call(Subscriber<? super CommonRxTask<T>> subscriber) {
                getT().doInIOThread();
                subscriber.onNext(getT());
                subscriber.onCompleted();
            }
        };
        Observable.create(onsubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CommonRxTask<T>>() {
                    @Override
                    public void call(CommonRxTask<T> t) {
                        t.doInUIThread();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}