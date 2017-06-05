package dji.midware.ui.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.SystemClock;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by jack_xie on 17-6-1.
 */

public class MarqueeTextView extends TextView {
    private static final String a = MarqueeTextView.class.getSimpleName();
    private MarqueeTextView.a b;
    private boolean c;
    private int d;

    public MarqueeTextView(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public MarqueeTextView(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public MarqueeTextView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.c = false;
        this.d = -1;
        TextUtils.TruncateAt var4 = this.getEllipsize();
        if(TextUtils.TruncateAt.MARQUEE == var4) {
            this.setEllipsize((TextUtils.TruncateAt)null);
            this.setMarqueeRepeatLimit(-1);
            this.setSingleLine();
            this.b = new MarqueeTextView.a(this);
        } else {
            this.setEllipsize(var4);
        }

    }

    public void setTextColor(int var1) {
        this.d = var1;
        super.setTextColor(var1);
    }

    public void setGravity(int var1) {
        super.setGravity(var1);
    }

    public void setText(CharSequence var1, BufferType var2) {
        CharSequence var3 = this.getText();
        if(!TextUtils.equals(var3, var1) && null != this.b) {
            this.d();
            this.c = true;
        }

        super.setText(var1, var2);
    }

    protected void onFocusChanged(boolean var1, int var2, Rect var3) {
        super.onFocusChanged(var1, var2, var3);
        this.a(null != this.b);
    }

    public void onWindowFocusChanged(boolean var1) {
        this.a(var1);
    }

    protected void onDraw(Canvas var1) {
        this.getPaint().setColor(this.d);
        this.b();
        Layout var2 = this.getLayout();
        float var3 = (float)(this.getHeight() - (var2.getLineBottom(0) - var2.getLineTop(0))) * 1.0F / 2.0F;
        float var4;
        if(this.b != null && this.b.d()) {
            var4 = -this.b.c();
            var1.translate((float)var2.getParagraphDirection(0) * var4, var3);
        } else {
            var1.translate(0.0F, var3);
        }

        var2.draw(var1, (Path)null, (Paint)null, 0);
        if(this.b != null && this.b.g()) {
            var4 = this.b.f();
            var1.translate((float)var2.getParagraphDirection(0) * var4, 0.0F);
            var2.draw(var1, (Path)null, (Paint)null, 0);
        }

    }

    private boolean a() {
        int var1 = this.getRight() - this.getLeft() - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight();
        return var1 > 0 && this.getLayout().getLineWidth(0) > (float)var1;
    }

    private void b() {
        if(this.c && null != this.b) {
            this.c();
            this.c = false;
        }

    }

    private void c() {
        if(this.b != null && this.b.e() && this.getLineCount() == 1 && this.a()) {
            this.b.b(this.getMarqueeRepeatLimit());
        }

    }

    private void d() {
        if(this.b != null && !this.b.e()) {
            this.b.b();
        }

    }

    private void a(boolean var1) {
        if(null != this.b) {
            if(var1) {
                this.c();
            } else {
                this.d();
            }
        }

    }

    public void setDelay(int var1) {
        if(this.b != null) {
            this.b.a(var1);
        }

    }

    private static final class a {
        private final WeakReference<TextView> a;
        private final Choreographer b;
        private byte c;
        private final float d;
        private float e;
        private int f;
        private float g;
        private float h;
        private float i;
        private long j;
        private static int k = 1200;
        private Choreographer.FrameCallback l;
        private Choreographer.FrameCallback m;
        private Choreographer.FrameCallback n;

        public void a(int var1) {
            k = var1;
        }

        private a(TextView var1) {
            this.c = 0;
            this.l = new Choreographer.FrameCallback() {
                public void doFrame(long var1) {
                    a.this.a();
                }
            };
            this.m = new Choreographer.FrameCallback() {
                public void doFrame(long var1) {
                    a.this.c = 2;
                    a.this.j = SystemClock.uptimeMillis();
                    a.this.a();
                }
            };
            this.n = new Choreographer.FrameCallback() {
                public void doFrame(long var1) {
                    if(a.this.c == 2) {
                        if(a.this.f >= 0) {
                            a.this.f--;
                        }

                        a.this.b(a.this.f);
                    }

                }
            };
            float var2 = var1.getContext().getResources().getDisplayMetrics().density;
            this.d = 30.0F * var2;
            this.a = new WeakReference(var1);
            this.b = Choreographer.getInstance();
        }

        void a() {
            if(this.c == 2) {
                this.b.removeFrameCallback(this.l);
                TextView var1 = (TextView)this.a.get();
                if(var1 != null) {
                    long var2 = SystemClock.uptimeMillis();
                    long var4 = var2 - this.j;
                    this.j = var2;
                    float var6 = (float)var4 / 1000.0F * this.d;
                    this.i += var6;
                    if(this.i > this.e) {
                        this.i = this.e;
                        this.b.postFrameCallbackDelayed(this.n, (long)k);
                    } else {
                        this.b.postFrameCallback(this.l);
                    }

                    var1.invalidate();
                }

            }
        }

        void b() {
            this.c = 0;
            this.b.removeFrameCallback(this.m);
            this.b.removeFrameCallback(this.n);
            this.b.removeFrameCallback(this.l);
            this.h();
        }

        private void h() {
            this.i = 0.0F;
            TextView var1 = (TextView)this.a.get();
            if(var1 != null) {
                var1.postInvalidate();
            }

        }

        void b(int var1) {
            if(var1 == 0) {
                this.b();
            } else {
                this.f = var1;
                TextView var2 = (TextView)this.a.get();
                if(var2 != null && var2.getLayout() != null) {
                    this.c = 1;
                    this.i = 0.0F;
                    int var3 = var2.getWidth() - var2.getCompoundPaddingLeft() - var2.getCompoundPaddingRight();
                    float var4 = var2.getLayout().getLineWidth(0);
                    float var5 = (float)var3 / 3.0F;
                    this.g = var4 - (float)var3 + var5;
                    this.e = this.g + (float)var3;
                    this.h = var4 + var5;
                    var2.postInvalidate();
                    this.b.postFrameCallback(this.m);
                }

            }
        }

        float c() {
            return this.i;
        }

        boolean d() {
            return this.c == 2;
        }

        boolean e() {
            return this.c == 0;
        }

        float f() {
            return this.h;
        }

        boolean g() {
            return this.c == 2 && this.i > this.g;
        }
    }
}
