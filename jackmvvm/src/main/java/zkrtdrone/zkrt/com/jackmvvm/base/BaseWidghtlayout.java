package zkrtdrone.zkrt.com.jackmvvm.base;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;

/**
 * Created by jack_xie on 16-12-19.
 */

public abstract class BaseWidghtlayout extends CoordinatorLayout {
    public BaseWidghtlayout(Context context) {
        super(context);
    }

    public BaseWidghtlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseWidghtlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        refreshSDKRelativeUI();
        IntentFilter filter = new IntentFilter();
        filter.addAction(this.getApplicationFlag());
        getContext().registerReceiver(mReceiver, filter);
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshSDKRelativeUI();
        }
    };

    private void init() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View content = layoutInflater.inflate(this.getLayoutId(), null, false);
        ButterKnife.bind(this,content);
        addView(content, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(mReceiver);
    }

    protected abstract int getLayoutId();
    protected abstract void refreshSDKRelativeUI();
    protected abstract String getApplicationFlag();
    protected void handler(Message msg){}
}
