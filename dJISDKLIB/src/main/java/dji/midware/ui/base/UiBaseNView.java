package dji.midware.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import dji.keysdk.DJIKey;
import dji.midware.ui.d.UiDG;
import dji.thirdparty.rx.Observable;
import dji.thirdparty.rx.Subscriber;
import dji.thirdparty.rx.android.schedulers.AndroidSchedulers;
import dji.thirdparty.rx.functions.Func1;

/**
 * Created by root on 17-6-1.
 */

public abstract class UiBaseNView extends FrameLayout implements BaseView {
    protected static final String TAG = "DULFrameLayout";
    private UiBaseGView widgetStyle;
    private List<DJIKey> dependentKeys;
    protected Context context;

    public UiBaseNView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.widgetStyle = UiBaseGView.a;
        this.context = var1;
        if(!this.isInEditMode()) {
            this.initView(var1, (AttributeSet)null, 0);
            this.initKey();
        }
    }

    public void setWidgetStyle(UiBaseGView var1) {
        this.widgetStyle = var1;
    }

    public void destroy() {
        UiDG.a().a(this);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(!this.isInEditMode()) {
            this.registerDependentKeys();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.destroy();
    }

    public Observable<Boolean> transformValueObservable(final Object var1, final DJIKey var2) {
        return Observable.just(var2).flatMap(new Func1<DJIKey,Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(DJIKey var1x) {
                transformValue(var1, var2);
                return Observable.just(Boolean.valueOf(true));
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> updateWidgetObservable(final DJIKey var1) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> var1x) {
               updateWidget(var1);
                if(!var1x.isUnsubscribed()) {
                    var1x.onNext(Boolean.valueOf(true));
                    var1x.onCompleted();
                }

            }
        });
    }

    public float aspectRatio() {
        return 1.0F;
    }

    protected void addDependentKey(DJIKey var1) {
        if(this.dependentKeys == null) {
            this.dependentKeys = new ArrayList();
        }

        if(!this.dependentKeys.contains(var1)) {
            this.dependentKeys.add(var1);
        }

    }

    public List<DJIKey> getDependentKeys() {
        return this.dependentKeys;
    }

    public void registerDependentKeys() {
        if(this.getDependentKeys() != null) {
            UiDG.a().a(this.getDependentKeys(), this);
        }

    }

    public void updateWidget(DJIKey var1) {
    }
}
