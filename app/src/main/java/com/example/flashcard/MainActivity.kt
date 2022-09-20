package com.example.flashcard

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<ImageView>(R.id.button)
        val question = findViewById<TextView>(R.id.flashcard_question)
        val ans = findViewById<TextView>(R.id.flashcard)
        val ans1 =  findViewById<TextView>(R.id.flashcard_answer)
        val ans2 = findViewById<TextView>(R.id.flashcard_answer2)
        val ans3 = findViewById<TextView>(R.id.flashcard_answer3)

        question.setOnClickListener {
            ans.isVisible = !ans.isVisible
        }

        ans1.setOnClickListener {
            ans1.setBackgroundColor(Color.parseColor("#90ee90"))
        }
        ans2.setOnClickListener {
            ans1.setBackgroundColor(Color.parseColor("#90ee90"))
            ans2.setBackgroundColor(Color.parseColor("#FF0000"))
        }
        ans3.setOnClickListener {
            ans3.setBackgroundColor(Color.parseColor("#FF0000"))
            ans1.setBackgroundColor(Color.parseColor("#90ee90"))
        }
        button.setOnClickListener{
            ans1.isVisible = !ans1.isVisible
            ans2.isVisible = !ans2.isVisible
            ans3.isVisible = !ans3.isVisible
        }
    }
}