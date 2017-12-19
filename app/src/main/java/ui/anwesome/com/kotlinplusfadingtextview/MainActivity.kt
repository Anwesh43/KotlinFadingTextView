package ui.anwesome.com.kotlinplusfadingtextview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.fadingtextview.FadingTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var view = FadingTextView.create(this)
        view.setText1("hello world")
        view.addToParent(this)
    }
}
