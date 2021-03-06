package com.jeongari.facedetection_mlkit

import android.content.Context
import android.graphics.*
import android.graphics.Paint.Style.FILL
import android.graphics.Paint.Style.STROKE
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.jeongari.facedetection_mlkit.utils.dip
import java.util.ArrayList

class DrawView : View {

    private var mRatioWidth = 0
    private var mRatioHeight = 0

    private var points : ArrayList<PointF> ?= null
    private var rectF : RectF ?= null
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mRatioX: Float = 0.toFloat()
    private var mRatioY: Float = 0.toFloat()
    private var mImgWidth: Int = 0
    private var mImgHeight: Int = 0

    private val mPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            style = STROKE
            strokeWidth = dip(2).toFloat()
        }
    }

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    fun setImgSize(
        width: Int,
        height: Int
    ) {
        mImgWidth = width
        mImgHeight = height
        requestLayout()
    }

    fun setDrawPoint(
        rectF: RectF,
        points: ArrayList<PointF>,
        ratio: Float
    ) {
        this.rectF = null
        this.points = null

        val left = rectF.left / ratio / mRatioX
        val right = rectF.right / ratio / mRatioX
        val bottom = rectF.bottom / ratio / mRatioY
        val top = rectF.top / ratio / mRatioY

        this.rectF = RectF(left,top,right,bottom)

        val pIter : Iterator<PointF> = points.iterator()
        while (pIter.hasNext()){
            val point = pIter.next()
            this.points?.add(PointF(point.x / ratio / mRatioX, point.y / ratio /mRatioY))
        }
    }

    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that is,
     * calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    fun setAspectRatio(
        width: Int,
        height: Int
    ) {
        if (width < 0 || height < 0) {
            throw IllegalArgumentException("Size cannot be negative.")
        }
        mRatioWidth = width
        mRatioHeight = height
        requestLayout()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(rectF != null){
            mPaint.color = Color.parseColor("#86AF49")
            canvas.drawRect(rectF!!,mPaint)
        }
        if (!points.isNullOrEmpty()){
            mPaint.color = Color.parseColor("#86AF49")
            canvas.drawPoints(points as FloatArray,mPaint)
        }
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height)
        } else {
            if (width < height * mRatioWidth / mRatioHeight) {
                mWidth = width
                mHeight = width * mRatioHeight / mRatioWidth
            } else {
                mWidth = height * mRatioWidth / mRatioHeight
                mHeight = height
            }
        }

        setMeasuredDimension(mWidth, mHeight)

        mRatioX = mImgWidth.toFloat() / mWidth
        mRatioY = mImgHeight.toFloat() / mHeight

    }
}