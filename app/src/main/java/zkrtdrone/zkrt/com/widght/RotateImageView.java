package zkrtdrone.zkrt.com.widght;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jack_xie on 17-4-27.
 */

public class RotateImageView extends View {
    private static final float YAW_ARROW_SIZE = 1.2f;
    private static final float YAW_ARROW_ANGLE = 4f; //4.5
    private static final float PLANE_WING_WIDTH = 5f;

    private float halfWidth;
    private float halfHeight;
    private float radiusExternal;

    private Paint yawPaint;
    private Paint planePaint;
    private Paint planeFinPaint;

    private Path yawPath = new Path();
    private float yaw;

    public RotateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
        setAttitude(0);
    }

    private void initialize() {
        Paint fillPaint = new Paint();
        fillPaint.setAntiAlias(true);
        fillPaint.setStyle(Paint.Style.FILL);

        yawPaint = new Paint(fillPaint);
        yawPaint.setColor(Color.RED);

        planePaint = new Paint(fillPaint);
        planePaint.setColor(Color.WHITE);
        planePaint.setStrokeWidth(PLANE_WING_WIDTH);
        planePaint.setStrokeCap(Paint.Cap.ROUND);
        planeFinPaint = new Paint(planePaint);
        planeFinPaint.setStrokeWidth(PLANE_WING_WIDTH / 2f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        halfHeight = h / 2f;
        halfWidth = w / 2f;
        radiusExternal = Math.min(halfHeight, halfWidth) / YAW_ARROW_SIZE;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(halfWidth, halfHeight);
        drawYaw(canvas);
    }

    private void drawYaw(Canvas canvas) {
        // Fill the background
        //canvas.drawCircle(0, 0, radiusExternal, yawPaint);
        // Yaw Arrow
        float mathYaw = (float) Math.toRadians(180 - yaw);
        yawPath.reset();
        yawPath.moveTo(0, 0);
        radialLineTo(yawPath, mathYaw + YAW_ARROW_ANGLE, radiusExternal);
        radialLineTo(yawPath, mathYaw, radiusExternal * YAW_ARROW_SIZE);
        radialLineTo(yawPath, mathYaw - YAW_ARROW_ANGLE, radiusExternal);
        canvas.drawPath(yawPath, yawPaint);
    }

    private void radialLineTo(Path path, float angle, float radius) {
        path.lineTo((float) Math.sin(angle) * radius, (float) Math.cos(angle) * radius);
    }

    public void setAttitude(double yaw) {
        this.yaw = (float)yaw;
        invalidate();
    }
}
