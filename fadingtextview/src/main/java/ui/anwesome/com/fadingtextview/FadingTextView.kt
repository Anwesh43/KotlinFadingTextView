package ui.anwesome.com.fadingtextview

/**
 * Created by anweshmishra on 19/12/17.
 */
import android.view.*
import android.graphics.*
import android.content.*
class FadingTextView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}