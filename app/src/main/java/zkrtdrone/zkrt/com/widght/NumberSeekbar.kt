package zkrtdrone.zkrt.com.widght

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.SeekBar
import zkrtdrone.zkrt.com.R

/**
 * Created by root on 17-6-8.
 */
class NumberSeekbar : SeekBar {
    private var oldPaddingTop: Int = 0

    private var oldPaddingLeft: Int = 0

    private var oldPaddingRight: Int = 0

    private var oldPaddingBottom: Int = 0

    private var isMysetPadding = true

    private var mText: String? = null

    private var mTextWidth: Float = 0.toFloat()

    private var mImgWidth: Float = 0.toFloat()

    private var mImgHei: Float = 0.toFloat()

    private var mPaint: Paint? = null

    private var res: Resources? = null

    private var bm: Bitmap? = null

    var textsize = 40
        private set

    var textpaddingleft: Int = 0
        private set

    var textpaddingtop: Int = 0
        private set

    var imagepaddingleft: Int = 0
        private set

    var imagepaddingtop: Int = 0
        private set

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    // 屏蔽滑动
    // @Override
    // public boolean onTouchEvent(MotionEvent event) {
    // return false;
    // }
    /**
     * (非 Javadoc)

     * @方法名: onTouchEvent
     * *
     * @描述: 不屏蔽屏蔽滑动
     * *
     * @日期: 2014-8-11 下午2:03:15
     * *
     * @param event
     * *
     * @return
     * *
     * @see android.widget.AbsSeekBar.onTouchEvent
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return super.onTouchEvent(event)
    }

    // 修改setpadding 使其在外部调用的时候无效
    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        if (isMysetPadding) {
            super.setPadding(left, top, right, bottom)
        }
    }

    // 初始化
    private fun init() {
        res = resources
        initBitmap()
        initDraw()
        setPadding()
    }

    private fun initDraw() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.typeface = Typeface.DEFAULT
        mPaint!!.textSize = textsize.toFloat()
        mPaint!!.color = 0xff23fc4f.toInt()
    }

    private fun initBitmap() {
        bm = BitmapFactory.decodeResource(res, R.drawable.pop_icon_down)
        if (bm != null) {
            mImgWidth = bm!!.width.toFloat()
            mImgHei = bm!!.height.toFloat()
        } else {
            mImgWidth = 0f
            mImgHei = 0f
        }
    }

    @Synchronized override fun onDraw(canvas: Canvas) {
        try {
            super.onDraw(canvas)
            mText = progress.toString() + ""// (getProgress() * 100 / getMax()) + "%";
            mTextWidth = mPaint!!.measureText(mText)
            val bounds = this.progressDrawable.bounds
            val xImg = (bounds.width() * progress / max + imagepaddingleft
                    + oldPaddingLeft).toFloat()
            val yImg = (imagepaddingtop + oldPaddingTop).toFloat()
            val xText = bounds.width() * progress / max + mImgWidth / 2 - mTextWidth / 2 + textpaddingleft.toFloat() + oldPaddingLeft.toFloat()
            val yText = yImg + textpaddingtop.toFloat() + (50 / 2).toFloat() + textHei / 4
            //canvas.drawBitmap(bm, xImg, yImg, mPaint);
            canvas.drawText(mText!!, xText, yText, mPaint!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // 初始化padding 使其左右上 留下位置用于展示进度图片
    private fun setPadding() {
        val top = bitmapHeigh + oldPaddingTop
        val left = bitmapWidth / 2 + oldPaddingLeft
        val right = bitmapWidth / 2 + oldPaddingRight
        val bottom = oldPaddingBottom
        isMysetPadding = true
        setPadding(left, top, right, bottom)
        isMysetPadding = false
    }

    /**
     * 设置展示进度背景图片

     * @param resid
     */
    fun setBitmap(resid: Int) {
        bm = BitmapFactory.decodeResource(res, resid)
        if (bm != null) {
            mImgWidth = bm!!.width.toFloat()
            mImgHei = bm!!.height.toFloat()
        } else {
            mImgWidth = 0f
            mImgHei = 0f
        }
        setPadding()
    }

    /**
     * 替代setpadding

     * @param left
     * *
     * @param top
     * *
     * @param right
     * *
     * @param bottom
     */
    fun setMyPadding(left: Int, top: Int, right: Int, bottom: Int) {
        oldPaddingTop = top
        oldPaddingLeft = left
        oldPaddingRight = right
        oldPaddingBottom = bottom
        isMysetPadding = true
        setPadding(left + bitmapWidth / 2, top + bitmapHeigh, right + bitmapWidth / 2, bottom)
        isMysetPadding = false
    }

    /**
     * 设置进度字体大小

     * @param textsize
     */
    fun setTextSize(textsize: Int) {
        this.textsize = textsize
        mPaint!!.textSize = textsize.toFloat()
    }

    /**
     * 设置进度字体颜色

     * @param color
     */
    fun setTextColor(color: Int) {
        mPaint!!.color = color
    }

    /**
     * 调整进度字体的位置 初始位置为图片的正中央

     * @param top
     * *
     * @param left
     */
    fun setTextPadding(top: Int, left: Int) {
        this.textpaddingleft = left
        this.textpaddingtop = top
    }

    /**
     * 调整进图背景图的位置 初始位置为进度条正上方、偏左一半

     * @param top
     * *
     * @param left
     */
    fun setImagePadding(top: Int, left: Int) {
        this.imagepaddingleft = left
        this.imagepaddingtop = top
    }

    private val bitmapWidth: Int
        get() = Math.ceil(mImgWidth.toDouble()).toInt()

    private val bitmapHeigh: Int
        get() = Math.ceil(mImgHei.toDouble()).toInt()

    private val textHei: Float
        get() {
            val fm = mPaint!!.fontMetrics
            return Math.ceil((fm.descent - fm.top).toDouble()).toFloat() + 2
        }
}
