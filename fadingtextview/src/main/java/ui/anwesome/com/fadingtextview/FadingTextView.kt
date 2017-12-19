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
    data class PlusButton(var x:Float,var y:Float,var r:Float) {
        fun draw(canvas: Canvas,paint:Paint,scale:Float) {
            paint.color = Color.parseColor("#311B92")
            paint.strokeWidth = r/5
            paint.strokeCap = Paint.Cap.ROUND
            for(i in 0..1) {
                canvas.save()
                canvas.translate(x,y)
                canvas.rotate(i*90f*scale)

                canvas.drawLine(0f,-r,0f,r,paint)
                canvas.restore()
            }
        }
        fun handleTap(x:Float,y:Float):Boolean = x>=this.x-r && x<=this.x+r && y>=this.y-r && y<=this.y+r
    }
}