package zkrtdrone.zkrt.com.jackmvvm.rxbean;

/**
 * Created by jack_xie on 17-1-16.
 * 在IO线程中执行的任务
 */

public abstract class IOTask<T> {
    private T t;


    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }


    public IOTask() {
        setT(t);
    }

    public abstract void doInIOThread() throws Exception;
}
