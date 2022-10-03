package com.example.flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val s1 = intent.getStringExtra("stringKey1")
        val s2 = intent.getStringExtra("stringKey2")
        val s3 = intent.getStringExtra("stringKey5")
        val s4 = intent.getStringExtra("stringKey4")
        val s5 = intent.getStringExtra("stringKey3")

        val promptQuestion = findViewById<EditText>(R.id.Question)
        val promptAnswer = findViewById<EditText>(R.id.answer)
        val promptAnswer1 = findViewById<EditText>(R.id.answer1)
        val promptAnswer2 = findViewById<EditText>(R.id.answer2)
        val promptAnswer3 = findViewById<EditText>(R.id.answer3)
        promptQuestion.setText(s1)
        promptAnswer.setText(s2)
        promptAnswer1.setText(s3)
        promptAnswer2.setText(s4)
        promptAnswer3.setText(s5)

        val cancelButton = findViewById<ImageView>(R.id.cancel_question)
        cancelButton.setOnClickListener{
            finish()
        }

        val saveButton = findViewById<ImageView>(R.id.save_question)
        saveButton.setOnClickListener{
            val get_text = findViewById<EditText>(R.id.Question).text.toString()
            val get_answer = findViewById<EditText>(R.id.answer).text.toString()
            val option1 = findViewById<EditText>(R.id.answer1).text.toString()
            val option2 = findViewById<EditText>(R.id.answer2).text.toString()
            val option3 = findViewById<EditText>(R.id.answer3).text.toString()

            if((get_text == "") or (get_answer == "") or (option1 == "") or (option2 == "") or (option3 == "")){
                Log.i("MainActivity", "Returned null data from AddCardActivity")
                val toast = Toast.makeText(applicationContext, "Incomplete Card", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast.show()
            }
            else {
                val data = Intent()
                data.putExtra(
                    "Question", get_text
                )
                data.putExtra(
                    "Answer", get_answer
                )
                data.putExtra(
                    "Answer1", option1
                )
                data.putExtra(
                    "Answer2", option2
                )
                data.putExtra(
                    "Answer3", option3
                )
                setResult(RESULT_OK, data)
                finish()
            }
        }
    }
}