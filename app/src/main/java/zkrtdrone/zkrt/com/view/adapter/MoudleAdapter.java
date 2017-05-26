package zkrtdrone.zkrt.com.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.arialyy.absadapter.common.AbsHolder;
import com.arialyy.absadapter.listview.AbsLvAdapter;
import java.util.List;
import butterknife.Bind;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.bean.Moudle;

/**
 * Created by jack_xie on 17-5-18.
 */

public class MoudleAdapter extends AbsLvAdapter<Moudle,MoudleAdapter.MoudleHolder> {
    public MoudleAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int setLayoutId(int type) {
        return R.layout.item_moudle;
    }

    @Override
    public void bindData(final int position, MoudleHolder holder, Moudle item) {
        holder.itemView.setVisibility(View.INVISIBLE);
        holder.moudle_img_sx.setBackground(item.getBitmap());
        holder.txt_moudle_name.setText(item.getName()+"");
        int count = 3 - position % 3;
        final TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                count,
                Animation.RELATIVE_TO_SELF,
                0,
                Animation.RELATIVE_TO_SELF,
                0,
                Animation.RELATIVE_TO_SELF,
                0);
        translateAnimation.setDuration(count* 50);

        final View finalConvertView = holder.itemView;
        holder.itemView.postDelayed(new Runnable() {
            @Override
            public void run() {
                finalConvertView.startAnimation(translateAnimation);
            }
        }, position * 50);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                finalConvertView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (position == getCount() - 1) {
                    if (mListener != null) {
                        mListener.onAnimationEnd();
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public interface OnLastItemAnimationEndListener {
        void onAnimationEnd();
    }

    private OnLastItemAnimationEndListener mListener;

    public void setOnLastItemAnimationEndListener(OnLastItemAnimationEndListener listener) {
        mListener = listener;
    }

    @Override
    public MoudleHolder getViewHolder(View convertView) {
        return new MoudleHolder(convertView);
    }

    /**
     * 创建相应的Holder
     */
    public class MoudleHolder extends AbsHolder {
        @Bind(R.id.moudle_img_sx)
        ImageView moudle_img_sx;
        @Bind(R.id.txt_moudle_name)
        TextView txt_moudle_name;

        public MoudleHolder(View itemView) {
            super(itemView);
        }
    }
}