package dji.midware.ui.d;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import dji.common.error.DJIError;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.GetCallback;
import dji.keysdk.callback.KeyListener;
import dji.midware.ui.base.BaseView;
import dji.thirdparty.rx.Observable;
import dji.thirdparty.rx.Subscription;
import dji.thirdparty.rx.android.schedulers.AndroidSchedulers;
import dji.thirdparty.rx.functions.Func1;
import dji.thirdparty.rx.schedulers.Schedulers;


/**
 * Created by jack_xie on 17-6-1.
 */

public class UiDG {
    private volatile Subscription subscriptionA;
    private Observable<Long> observableB;
    private Map<BaseView, List<DJIKey>> mapC;
    private Map<BaseView, List<KeyListener>> mapD;

    private void b() {
        this.d();
        if(mapC != null && !mapC.isEmpty()) {
            Iterator var1 = mapC.keySet().iterator();

            while(var1.hasNext()) {
                BaseView var2 = (BaseView)var1.next();
                this.b((List)mapC.remove(var2), var2);
            }
        }

    }

    public void a(List<DJIKey> var1, BaseView var2) {
        if(KeyManager.getInstance() == null) {
            mapC.put(var2, var1);
            this.c();
        } else {
            this.b(var1, var2);
        }

    }

    private void c() {
        if(subscriptionA == null || subscriptionA.isUnsubscribed()) {
            subscriptionA = observableB.subscribe();
        }

    }

    private void d() {
        if(subscriptionA!= null && !subscriptionA.isUnsubscribed()) {
            subscriptionA.unsubscribe();
            subscriptionA = null;
        }

    }

    public void a(DJIKey var1, BaseView var2) {
        if(KeyManager.getInstance() != null) {
            this.c(var1, var2);
        }
    }

    private void b(final List<DJIKey> var1, final BaseView var2) {
        if(mapD.containsKey(var2)) {
            mapD.remove(var2);
            uiDGB(var2).flatMap(new Func1<Boolean,Observable<Boolean>>() {
                @Override
                public Observable<Boolean> call(Boolean var1x) {
                    return uidgC(var1, var2);
                }
            }).subscribe();
        } else {
            uidgC(var1, var2).subscribe();
        }
    }

    private Observable<Boolean> uidgC(List<DJIKey> var1, final BaseView var2) {
        return Observable.from(var1).flatMap(new Func1<DJIKey,Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(DJIKey o) {
                return b(o, var2);
            }
        }).subscribeOn(Schedulers.computation());
    }

    private Observable<Boolean> b(DJIKey var1, final BaseView var2) {
        Observable var3 = Observable.just(var1).flatMap(new Func1<DJIKey,Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(final DJIKey var1) {
                if(var1 != null) {
                    KeyManager.getInstance().getValue(var1, new GetCallback() {
                        public void onSuccess(Object var1x) {
                            if(var1x != null) {
                                var2.transformValueObservable(var1x, var1).flatMap(new Func1<Boolean,Observable<?>>() {
                                    @Override
                                    public Observable<?> call(Boolean var1x) {
                                        return var2.updateWidgetObservable(var1).observeOn(AndroidSchedulers.mainThread());
                                    }
                                }).subscribe();
                            }

                        }

                        public void onFailure(@NonNull DJIError var1x) {
                        }
                    });
                    KeyListener var2x = new KeyListener() {
                        public void onValueChange(Object var1x, Object var2x) {
                            if(var2x != null) {
                                var2.transformValueObservable(var2x, var1).flatMap(new Func1<Boolean,Observable<?>>() {
                                    @Override
                                    public Observable<?> call(Boolean var1x) {
                                        return var2.updateWidgetObservable(var1).observeOn(AndroidSchedulers.mainThread());
                                    }
                                }).subscribe();
                            }

                        }
                    };
                    KeyManager.getInstance().addListener(var1, var2x);
                    Object var3 = (List)mapD.get(var2);
                    if(var3 == null) {
                        var3 = new CopyOnWriteArrayList();
                    }

                    ((List)var3).add(var2x);
                    mapD.put(var2, (List<KeyListener>) var3);
                }

                return Observable.just(Boolean.valueOf(true));
            }
        }).subscribeOn(Schedulers.computation());
        return var3;
    }

    public void a(BaseView var1) {
        if(mapC.containsKey(var1)) {
            mapC.remove(var1);
        }

        this.uiDGB(var1).subscribe();
    }

    private Observable uiDGB(final BaseView var1) {
        return var1 != null && var1.getDependentKeys() != null?Observable.from(var1.getDependentKeys()).flatMap(new Func1<DJIKey,Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(DJIKey var1x) {
                List var2 = (List)mapD.remove(var1);
                if(var2 != null) {
                    Iterator var3 = var2.iterator();

                    while(var3.hasNext()) {
                        KeyListener var4 = (KeyListener)var3.next();
                        if(KeyManager.getInstance() != null) {
                            KeyManager.getInstance().removeListener(var4);
                        }
                    }
                }

                return Observable.just(Boolean.valueOf(true));
            }
        }):Observable.just(Boolean.valueOf(true));
    }

    private UiDG() {
        observableB = Observable.timer(100L, TimeUnit.MILLISECONDS).map(new Func1<Long,Long>() {
            @Override
            public Long call(Long var1) {
                if(KeyManager.getInstance() != null) {
                    b();
                }

                return Long.valueOf(1L);
            }
        }).repeat().subscribeOn(Schedulers.computation());
        this.mapC = new ConcurrentHashMap();
        this.mapD = new ConcurrentHashMap();
        UiDA.a();
    }

    public static UiDG a() {
        return UiDG.a.a;
    }

    private Observable<Boolean> c(DJIKey var1, final BaseView var2) {
        Observable var3 = Observable.just(var1).flatMap(new Func1<DJIKey,Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(final DJIKey var1) {
                KeyManager.getInstance().getValue(var1, new GetCallback() {
                    public void onSuccess(Object var1x) {
                        if(var1x != null) {
                            var2.transformValueObservable(var1x, var1).flatMap(new Func1<Boolean,Observable<?>>() {
                                @Override
                                public Observable<?> call(Boolean var1x) {
                                    return var2.updateWidgetObservable(var1).observeOn(AndroidSchedulers.mainThread());
                                }
                            }).subscribe();
                        }

                    }

                    public void onFailure(@NonNull DJIError var1x) {
                    }
                });
                return Observable.just(Boolean.valueOf(true));
            }
        }).subscribeOn(Schedulers.computation());
        return var3;
    }

    private static class a {
        private static UiDG a = new UiDG();
    }
}
