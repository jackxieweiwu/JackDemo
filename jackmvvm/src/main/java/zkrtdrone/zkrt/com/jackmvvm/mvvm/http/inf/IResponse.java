package zkrtdrone.zkrt.com.jackmvvm.mvvm.http.inf;

/**
 * Created by jack_xie on 17-4-9.
 * 数据响应接口
 */

public interface IResponse {
    /**
     * 响应的数据回调
     */
    public void onResponse(String data);

    /**
     * 错误返回回掉
     */
    public void onError(Object error);
}
