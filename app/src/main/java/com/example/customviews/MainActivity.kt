package com.example.customviews

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.custom_view_lib.labels.LabelView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView(){
        val labelView: LabelView = findViewById(R.id.label_view)
        labelView.applyLastAttributes()
    }
}
