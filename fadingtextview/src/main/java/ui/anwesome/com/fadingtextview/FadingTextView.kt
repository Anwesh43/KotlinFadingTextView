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
    data class FadingText(var x:Float,var y:Float,var h:Float,var text:String) {
        fun draw(canvas:Canvas,paint:Paint,scale:Float) {
            paint.textSize = h/2
            paint.color = Color.BLACK
            val path = Path()
            val rw = paint.measureText(text)*12/10
            path.addRect(RectF(x+rw*scale,y,x+rw,y+h),Path.Direction.CW)
            canvas.save()
            canvas.clipPath(path)
            canvas.drawText(text,x+paint.measureText(text)/10,y,paint)
            canvas.restore()
        }
    }
    
}