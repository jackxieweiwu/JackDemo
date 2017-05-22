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
    //private var scrollY: Int = 0
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
    fun expandGridViewAtView(clickedView: View) {

        // 0. Init the cover layer
        mCoverView = LinearLayout(context)
        mCoverView!!.orientation = LinearLayout.VERTICAL

        // 1. Init the up, middle and down part views for the cover layer
        val imageViewUp = ImageView(context)
        val imageViewDown = ImageView(context)
        val middleView = HorizontalScrollView(context)

        // bottom position of clicked item view
        val touchBottom = clickedView.getBottom()
        // when the clicked item view is not fully showed, scroll up to make it fully show
        if (touchBottom > measuredHeight - paddingBottom - verticalSpacing) {
            hasScrolled = true
            scrollY = touchBottom - measuredHeight + paddingBottom + verticalSpacing / 2
            scrollBy(0, scrollY)
        }
        // 2. Take snapshot of current grid view
        this.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(this.drawingCache)
        this.destroyDrawingCache()// clear the draw cache, so that next time will not get the same drawing

        var heightUp = 1// height of up cover image
        var heightDown = 1// height of down cover image
        val middleViewHeight = clickedView.getHeight() + 20// height of middle sub grid view
        val bottomPos = bitmap.height - touchBottom - verticalSpacing / 2 - middleViewHeight
        var upY = 0 // y position where up image start to split the image
        var downY = touchBottom// y position where down image start to split the image
        // if the middle sub grid view cannot fully display, decrease up cover image's height of middleViewHeight
        // so that the cover layer can scroll up to make middle sub grid view display
        if (bottomPos <= 0) {
            heightUp = touchBottom + verticalSpacing / 2 - middleViewHeight
            upY = middleViewHeight
            // down image height, decrease middle view height so that cover layer height matches the grid view height
            heightDown = bitmap.height - heightUp - middleViewHeight
            // when down image view cannot fully display, set it's height to the bottom padding height
            if (heightDown < 0) {
                heightUp += heightDown
                heightDown = paddingBottom
                heightUp -= heightDown
            }
            downY = upY + heightUp
        } else {
            // when the middle view can fully display, decrease down image view's height of middle view height
            heightUp = touchBottom + verticalSpacing / 2
            heightDown = bottomPos
        }
        // 3. Split the snapshot image to up/down image
        val bitmapUp = Bitmap.createBitmap(bitmap, 0, upY, bitmap.width, heightUp)
        val bitmapDown = Bitmap.createBitmap(bitmap, 0, downY, bitmap.width, heightDown)
        // add click handler to the up/down image view: collapse the expand grid view
        imageViewUp.setImageBitmap(bitmapUp)
        imageViewUp.setOnClickListener { collapseGridView() }
        imageViewDown.setImageBitmap(bitmapDown)
        imageViewDown.setOnClickListener { collapseGridView() }

        // 4. Middle sub grid view, set it's to one row, horizontal scrollable grid view
        val linearLayout = LinearLayout(context)
        val vSpace = verticalSpacing
        val hSpace = horizontalSpacing
        val gridParams = AbsListView.LayoutParams(count * (columnWidth + hSpace),
                ViewGroup.LayoutParams.WRAP_CONTENT)
        clickedView.setLayoutParams(gridParams)
        clickedView.setPadding(hSpace, vSpace, hSpace, vSpace)

        linearLayout.addView(clickedView)
        middleView.addView(linearLayout)
        // Triangle arrow up
        val touchX:Float = (clickedView.getLeft() + columnWidth / 2).toFloat()
        val touchY:Float = heightUp.toFloat()
        val canvas = Canvas(bitmapUp)//use Canvas to draw triangle in the up cover image
        val path = Path()
        path.moveTo(touchX - 15f, touchY)
        path.lineTo(touchX + 15f, touchY)
        path.lineTo(touchX, touchY - 15f)
        path.lineTo(touchX - 15f, touchY)
        val circle = ShapeDrawable(PathShape(path, width.toFloat(), height.toFloat()))
        circle.paint.color = Color.DKGRAY
        circle.setBounds(0, 0, width, height)
        circle.draw(canvas)

        val params = ViewGroup.LayoutParams(width, middleViewHeight)
        middleView.layoutParams = params
        middleView.setBackgroundColor(Color.DKGRAY)

        // 6. Add the up/middle/down sub views to the cover layer
        mCoverView!!.addView(imageViewUp)
        mCoverView!!.addView(middleView)
        mCoverView!!.addView(imageViewDown)

        // 7. Add the cover layer to the grid view and bring it to the front
        mParentViewGroup = parent as ViewGroup
        mLayoutParams = WindowManager.LayoutParams()
        mLayoutParams!!.format = PixelFormat.TRANSLUCENT
        mLayoutParams!!.gravity = Gravity.TOP or Gravity.LEFT
        mLayoutParams!!.x = left//start x
        mLayoutParams!!.y = top //start y
        mLayoutParams!!.width = WindowManager.LayoutParams.WRAP_CONTENT
        mLayoutParams!!.height = WindowManager.LayoutParams.WRAP_CONTENT
        mLayoutParams!!.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        mParentViewGroup!!.addView(mCoverView, 0, mLayoutParams)
        mCoverView!!.bringToFront()

    }

    /**
     * Collapse the grid view and remove the cover layer
     */
    fun collapseGridView() {
        // remove the cover layer
        if (mParentViewGroup != null && mCoverView != null) {
            mCoverView!!.removeAllViews()
            mParentViewGroup!!.removeView(mCoverView)
            mLayoutParams = null
            mCoverView = null
            mParentViewGroup = null
        }
        // if the grid view has scrolled before expand, scroll it back
        if (hasScrolled) {
            scrollBy(0, -scrollY)
            hasScrolled = false
            scrollY = 0
        }
    }

    /**
     * Sub item click listener interface
     */
    interface OnExpandItemClickListener {
        fun onItemClick(position: Int, clickPositionData: Any)
    }

}