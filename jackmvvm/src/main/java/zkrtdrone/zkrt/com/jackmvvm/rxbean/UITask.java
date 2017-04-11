package zkrtdrone.zkrt.com.jackmvvm.rxbean;

/**
 * Created by jack_xie on 17-1-16.
 * 在主线程中执行的任务
 */

public abstract class UITask<T> {
    public abstract void doInUIThread();

    public UITask(T t) {
        setT(t);
    }

    public UITask() {

    }

    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
