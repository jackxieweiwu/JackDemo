package zkrtdrone.zkrt.com.jackmvvm.rxbean;


/**
 * Created by jack_xie on 17-1-16.
 * 通用的Rx执行任务
 */

public abstract class CommonRxTask<T> {
    public CommonRxTask(T t) {
        setT(t);
    }

    public CommonRxTask() {

    }

    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public abstract void doInIOThread();

    public abstract void doInUIThread();
}