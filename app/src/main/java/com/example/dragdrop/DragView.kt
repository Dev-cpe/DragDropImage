package com.example.dragdrop

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class DragView(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs) {

    var prevY: Int = 0
    var calY:Int = 0
    var downX:Int = 0
    var downY:Int = 0

    var percentDragOut = 30
    var startDrag = 5
    private var endDrag = false
    private lateinit var dragAndDrop: DragAndDrop
    private lateinit var activity: AppCompatActivity

    fun init(activity: AppCompatActivity, dragAndDrop: DragAndDrop){
        this.activity = activity
        this.dragAndDrop = dragAndDrop
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val point = event!!.pointerCount
        var currentY = 0f
        val par = this.layoutParams as LinearLayout.LayoutParams
        if (point == 1) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    prevY = event.rawY.toInt()
                    calY = event.rawY.toInt()

                    downX = event.rawX.toInt()
                    downY = event.rawY.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    currentY = event.rawY.toInt().toFloat()
                    par.topMargin += event.rawY.toInt() - prevY
                    prevY = event.rawY.toInt()
                    par.leftMargin = 0
                    this.layoutParams = par

                    var cal = 0f
                    if (currentY > calY) {
                        cal = currentY - calY
                    } else if (currentY < calY) {
                        cal = calY - currentY
                    }

                    if (calScrollPercent(cal) > startDrag) {
                        dragAndDrop.actionStartDrag()
                    }

                    if (calScrollPercent(cal) > percentDragOut) {
                        endDrag = true
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (!endDrag) {
                        par.topMargin = 0
                        par.leftMargin = 0
                        this.layoutParams = par
                    } else {
                        this.visibility = GONE
                    }

                    if (endDrag) {
                        dragAndDrop.actionEndDrop(endDrag)
                    } else {
                        dragAndDrop.actionDrop(endDrag)
                    }
                }
            }
        } else {
            super.onTouchEvent(event)
        }
        return true
    }

    private fun calScrollPercent(cal: Float): Float {
        return cal / getScreenHeightSize() * 100
    }

    private fun getScreenHeightSize(): Int {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }

    interface DragAndDrop {
        fun actionStartDrag()
        fun actionDrop(output: Boolean)
        fun actionEndDrop(output: Boolean)
    }
}