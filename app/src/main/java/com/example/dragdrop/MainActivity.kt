package com.example.dragdrop

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DragView.DragAndDrop {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image.init(this,this)
        image.percentDragOut = 50
        image.startDrag = 10
    }

    override fun actionStartDrag() {
        Log.d("Drag","actionStartDrag")
    }

    override fun actionDrop(output: Boolean) {
        Log.d("Drag","actionDrop")
    }

    override fun actionEndDrop(output: Boolean) {
        if (output){
            Log.d("Drag","Finish ")
        }
        Log.d("Drag","actionEndDrop")
    }

}
