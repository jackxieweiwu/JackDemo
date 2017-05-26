package zkrtdrone.zkrt.com.widght

import android.content.Context
import android.graphics.*
import android.view.WindowManager
import android.view.Gravity
import android.view.ViewGroup
import android.graphics.drawable.shapes.PathShape
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.*
import android.widget.LinearLayout


/**
 * Created by jack_xie on 17-5-22.
 */
class ExpandableGridView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GridView(context, attrs, defStyleAttr) {

    private var mLayoutParams: WindowManager.LayoutParams? = null
    private var mCoverView: LinearLayout? = null
    private var mParentViewGroup: ViewGroup? = null
    private var hasScrolled = false
    private var sllY:Int = 0
    private var mListener: OnExpandItemClickListener? = null

    /**
     * Set listener for sub grid view item. When sub grid view item is clicked, it will invoke
     * the listener's onItemClick function.
     * @param listener
     */
    fun setOnExpandItemClickListener(listener: OnExpandItemClickListener) {
        mListener = listener
    }

    /**
     * Expand the grid view under the clicked item.
     * @param clickedView The clicked item.

     */
    fun expandGridViewAtView(view: View, clickedView: View,numberw:Int,moudleNumber:Int) {

        // 0. Init the cover layer
        mCoverView = LinearLayout(context)
        (mCoverView as LinearLayout).orientation = LinearLayout.VERTICAL
        clickedView.setOnTouchListener { v, event -> true }
        val imageViewUp = ImageView(context)
        val imageViewDown = ImageView(context)
        //if(moudleNumber == 3) {
            val middleView = HorizontalScrollView(context)
        /*}else{
            val middleView = FrameLayout(context) //HorizontalScrollView(context)
         }*/

        val touchBottom = view.getBottom()
        if (touchBottom > measuredHeight - paddingBottom - verticalSpacing) {
            hasScrolled = true
            sllY = touchBottom - measuredHeight + paddingBottom + verticalSpacing / 2
            scrollBy(0, sllY)
        }
        this.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(this.drawingCache)
        this.destroyDrawingCache()

        var heightUp:Int = 1 // height of up cover image
        var heightDown:Int = 1// height of down cover image
        val middleViewHeight = view.getHeight() + 20
        val bottomPos = bitmap.height - touchBottom - verticalSpacing / 2 - middleViewHeight
        var upY = 0
        var downY = touchBottom
        if (bottomPos <= 0) {
            heightUp = touchBottom + verticalSpacing / 2 - middleViewHeight
            upY = middleViewHeight
            heightDown = bitmap.height - heightUp - middleViewHeight
            if (heightDown < 0) {
                heightUp += heightDown
                heightDown = paddingBottom
                heightUp -= heightDown
            }
            downY = upY + heightUp
        } else {
            heightUp = touchBottom + verticalSpacing / 2
            heightDown = bottomPos
        }
        val bitmapUp = Bitmap.createBitmap(bitmap, 0, upY, bitmap.width, heightUp)
        val bitmapDown = Bitmap.createBitmap(bitmap, 0, downY, bitmap.width, heightDown)
        imageViewUp.setImageBitmap(bitmapUp)
        imageViewUp.setOnClickListener { collapseGridView() }
        imageViewDown.setImageBitmap(bitmapDown)
        imageViewDown.setOnClickListener { collapseGridView() }
        val linearLayout = LinearLayout(context)
        val vSpace = verticalSpacing
        val hSpace = horizontalSpacing
        val gridParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        clickedView.setLayoutParams(gridParams)
        clickedView.setPadding(hSpace, vSpace, hSpace, vSpace)
        linearLayout.addView(clickedView)
        middleView.addView(linearLayout)
        val touchX:Float = (view.getLeft() + columnWidth / 2).toFloat()
        val touchY:Float = heightUp.toFloat()
        val canvas = Canvas(bitmapUp)
        val path = Path()
        path.moveTo(touchX - 15, touchY)
        path.lineTo(touchX + 15, touchY)
        path.lineTo(touchX, touchY - 15)
        path.lineTo(touchX - 15, touchY)
        val circle = ShapeDrawable(PathShape(path, width.toFloat(), height.toFloat()))
        circle.paint.color = Color.DKGRAY
        circle.setBounds(0, 0, width, height)
        circle.draw(canvas)
        var aNumber:Int = ViewGroup.LayoutParams.WRAP_CONTENT
        if(numberw == 1)
            aNumber = ViewGroup.LayoutParams.WRAP_CONTENT
        if(numberw == 2) aNumber = middleViewHeight
        val params = ViewGroup.LayoutParams(width,aNumber)
        middleView.layoutParams = params
        middleView.setBackgroundColor(Color.DKGRAY)

        (mCoverView as LinearLayout).addView(imageViewUp)
        (mCoverView as LinearLayout).addView(middleView)
        (mCoverView as LinearLayout).addView(imageViewDown)

        mParentViewGroup = parent as ViewGroup
        mLayoutParams = WindowManager.LayoutParams()
        (mLayoutParams as WindowManager.LayoutParams).format = PixelFormat.TRANSLUCENT
        (mLayoutParams as WindowManager.LayoutParams).gravity = Gravity.TOP or Gravity.LEFT
        (mLayoutParams as WindowManager.LayoutParams).x = left//start x
        (mLayoutParams as WindowManager.LayoutParams).y = top //start y
        (mLayoutParams as WindowManager.LayoutParams).width = WindowManager.LayoutParams.WRAP_CONTENT
        (mLayoutParams as WindowManager.LayoutParams).height = WindowManager.LayoutParams.WRAP_CONTENT
        (mLayoutParams as WindowManager.LayoutParams).flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        (mParentViewGroup as ViewGroup).addView(mCoverView, 0, mLayoutParams)
        (mCoverView as LinearLayout).bringToFront()
    }

    /**
     * Collapse the grid view and remove the cover layer
     */
    fun collapseGridView() {
        if (mParentViewGroup != null && mCoverView != null) {
            (mCoverView as LinearLayout).removeAllViews()
            (mParentViewGroup as ViewGroup).removeView(mCoverView)
            mLayoutParams = null
            mCoverView = null
            mParentViewGroup = null
        }
        if (hasScrolled) {
            scrollBy(0, -sllY)
            hasScrolled = false
            sllY = 0
        }
    }

    /**
     * Sub item click listener interface
     */
    interface OnExpandItemClickListener {
        fun onItemClick(position: Int, clickPositionData: Any)
    }
}