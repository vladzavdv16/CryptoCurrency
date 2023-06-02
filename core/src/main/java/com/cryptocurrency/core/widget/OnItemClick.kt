package com.cryptocurrency.core.widget

import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

class OnItemClick(
    private val listener: View.OnClickListener
) : RecyclerView.OnItemTouchListener {

    private var downTime: Long = 0L

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (!isSingleTap(e)) {
            val view = rv.findChildViewUnder(e.x, e.y)
            if (view != null) {
                listener.onClick(view)
            }
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        TODO("Not yet implemented")
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        TODO("Not yet implemented")
    }

    private fun isSingleTap(e: MotionEvent): Boolean {
        if (MotionEvent.ACTION_DOWN == e.actionMasked) {
            downTime = SystemClock.uptimeMillis()
        } else if (MotionEvent.ACTION_UP == e.actionMasked) {
            e.eventTime - downTime <= ViewConfiguration.getTapTimeout()
        }
        return false
    }
}