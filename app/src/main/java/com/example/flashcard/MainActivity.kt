package com.example.flashcard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar

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
        val addButton = findViewById<ImageView>(R.id.add_question)
        val editButton = findViewById<ImageView>(R.id.edit_question)

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

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
            val data: Intent? = result.data
            if (data != null) {
                val string1 = data.getStringExtra("Question")
                val string2 = data.getStringExtra("Answer")
                val string3 = data.getStringExtra("Answer1")
                val string4 = data.getStringExtra("Answer2")
                val string5 = data.getStringExtra("Answer3")

                question.text = string1
                ans.text = string2
                ans1.text = string3
                ans2.text = string4
                ans3.text = string5

                Log.i("MainActivity", "string1: $string1")
                Log.i("MainActivity", "string2: $string2")
                Snackbar.make(findViewById(R.id.flashcard_question), "Successfully created card", Snackbar.LENGTH_SHORT).show()
            }
            else{
                Log.i("MainActivity", "Returned null data from AddCardActivity")
            }


        }

        addButton.setOnClickListener{
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
        }

        editButton.setOnClickListener{
            val intent = Intent(this, AddCardActivity::class.java)
            val edit_question = question.text.toString()
            val edit_answer = ans.text.toString()
            val edit_answer1 = ans1.text.toString()
            val edit_answer2 = ans2.text.toString()
            val edit_answer3 = ans3.text.toString()
            intent.putExtra("stringKey1", edit_question)
            intent.putExtra("stringKey2", edit_answer )
            intent.putExtra("stringKey3", edit_answer1 )
            intent.putExtra("stringKey4", edit_answer2 )
            intent.putExtra("stringKey5", edit_answer3 )
            resultLauncher.launch(intent)
        }
    }
}