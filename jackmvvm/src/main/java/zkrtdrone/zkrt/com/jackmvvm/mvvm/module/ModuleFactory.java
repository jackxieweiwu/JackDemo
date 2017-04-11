package zkrtdrone.zkrt.com.jackmvvm.mvvm.module;

import android.util.SparseIntArray;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.ObjUtil;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.L;

/**
 * Created by jack_xie on 17-4-9.
 */

public class ModuleFactory {
    private Map<Set<Integer>, AbsModule.OnCallback> mPool = new HashMap<>();
    private SparseIntArray mKeyIndex = new SparseIntArray();
    private static final String TAG = "ModuleFactory";

    public ModuleFactory() {

    }

    public void addCallback(int key, AbsModule.OnCallback callback) {
        if (checkKey(key, callback)) {
            L.e(TAG, "key 已经和 callback对应");
            return;
        }
        if (mPool.containsValue(callback)) {
            Set<Integer> oldKeys = ObjUtil.getKeyByValue(mPool, callback);
            if (oldKeys != null) {
                if (!oldKeys.contains(key)) {
                    oldKeys.add(key);
                    mKeyIndex.put(key, callback.hashCode());
                }
            } else {
                oldKeys = new HashSet<>();
                oldKeys.add(key);
                mPool.put(oldKeys, callback);
                mKeyIndex.put(key, callback.hashCode());
            }
        } else {
            Set<Integer> newKeys = new HashSet<>();
            newKeys.add(key);
            mPool.put(newKeys, callback);
            mKeyIndex.put(key, callback.hashCode());
        }
    }

    /**
     * 检查key和callback的对应关系
     *
     * @return true : key已经和value对应，false : key没有和value对应
     */
    private boolean checkKey(int key, AbsModule.OnCallback callback) {
        return mKeyIndex.indexOfKey(key) != -1
                || mKeyIndex.indexOfValue(callback.hashCode()) != -1
                && mKeyIndex.valueAt(callback.hashCode()) == key;
    }
}
