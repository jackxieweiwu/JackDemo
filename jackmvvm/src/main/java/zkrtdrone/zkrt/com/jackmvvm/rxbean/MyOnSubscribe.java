package zkrtdrone.zkrt.com.jackmvvm.rxbean;

import dji.thirdparty.rx.Observable;

/**
 * Created by jack_xie on 17-1-16.
 */

public abstract class MyOnSubscribe<C> implements Observable.OnSubscribe<C> {
    private C c;

    public MyOnSubscribe(C c) {
        setT(c);
    }

    public C getT() {
        return c;
    }

    public void setT(C c) {
        this.c = c;
    }


}