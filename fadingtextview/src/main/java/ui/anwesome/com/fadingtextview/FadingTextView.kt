package ui.anwesome.com.fadingtextview

/**
 * Created by anweshmishra on 19/12/17.
 */
import android.app.Activity
import android.view.*
import android.graphics.*
import android.content.*
class FadingTextView(ctx:Context,var text:String="hello"):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = FadingTextRenderer(this)
    override fun onDraw(canvas:Canvas) {
        renderer.render(canvas,paint)
    }
    fun addToParent(activity: Activity) {
        activity.addContentView(this, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200))
    }
    fun setText1(text1:String) {
        this.text = text1
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap(event.x,event.y)
            }
        }
        return true
    }
    data class FadingText(var x:Float,var y:Float,var h:Float,var text:String) {
        fun draw(canvas:Canvas,paint:Paint,scale:Float) {
            paint.textSize = h/2
            paint.color = Color.WHITE
            canvas.save()
            val path = Path()
            val rw = paint.measureText(text)*12/10
            path.addRect(RectF(x+rw*scale,y-h/2,x+rw,y+h/2),Path.Direction.CW)
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
                canvas.drawLine(r,0f,-r,0f,paint)
                canvas.restore()
            }
        }
        fun handleTap(x:Float,y:Float):Boolean = x>=this.x-r && x<=this.x+r && y>=this.y-r && y<=this.y+r
    }
    data class FadingTextContainer(var text:String,var w:Float,var h:Float) {
        var plusButton = PlusButton(w/10,h/2,h/3)
        var fadingText = FadingText(w/10+h/3,h/2,h,text)
        val state = State()
        fun draw(canvas: Canvas,paint:Paint) {
            state.executeFn { scale ->
                plusButton.draw(canvas,paint,scale)
                fadingText.draw(canvas,paint,scale)
            }
        }
        fun update(stopcb:(Float)->Unit) {
            state.update(stopcb)
        }
        fun startUpdating(x:Float,y:Float,startcb:()->Unit) {
            if(plusButton.handleTap(x,y)) {
                state.startUpdating(startcb)
            }
        }
    }
    data class State(var scale:Float = 0f,var dir:Float = 0f,var prevScale:Float = 0f) {
        fun update(stopcb:(Float)->Unit) {
            scale += 0.1f*dir
            if(Math.abs(scale-prevScale)>1) {
                scale = prevScale+dir
                dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }
        fun startUpdating(startcb:()->Unit) {
            dir = 1f-2*scale
            startcb()
        }
        fun executeFn(cb:(Float)->Unit) {
            cb(scale)
        }
    }
    data class FadingTextAnimator(var container:FadingTextContainer,var view:FadingTextView) {
        var animated = false
        fun update() {
            if(animated) {
                container.update{
                    animated = false
                }
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex:Exception) {

                }
            }
        }
        fun draw(canvas: Canvas,paint:Paint) {
            container.draw(canvas,paint)
        }
        fun startUpdating(x:Float,y:Float) {
            if(!animated) {
                container.startUpdating (x,y,{
                    animated = true
                    view.postInvalidate()
                })
            }
        }
    }
    data class FadingTextRenderer(var view:FadingTextView,var time:Int = 0) {
        var animator:FadingTextAnimator?=null
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                animator = FadingTextAnimator(FadingTextContainer(view.text,w,h),view)
            }
            canvas.drawColor(Color.parseColor("#212121"))
            animator?.draw(canvas,paint)
            animator?.update()
            time++
        }
        fun handleTap(x:Float,y:Float) {
            animator?.startUpdating(x,y)
        }
    }
    companion object {
        fun create(activity:Activity):FadingTextView {
            val view = FadingTextView(activity)
            return view
        }
    }
}