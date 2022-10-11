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

        val s1 = intent.getStringExtra("stringQuestionKey")
        val s2 = intent.getStringExtra("stringAnswerKey")
        val s3 = intent.getStringExtra("stringWrongKey1")
        val s4 = intent.getStringExtra("stringWrongKey2")

        val promptQuestion = findViewById<EditText>(R.id.Question)
        val promptAnswer = findViewById<EditText>(R.id.answer)
        val promptAnswer1 = findViewById<EditText>(R.id.wrongAnswer1)
        val promptAnswer2 = findViewById<EditText>(R.id.wrongAnswer2)

        promptQuestion.setText(s1)
        promptAnswer.setText(s2)
        promptAnswer1.setText(s3)
        promptAnswer2.setText(s4)

        val cancelButton = findViewById<ImageView>(R.id.cancel_question)
        cancelButton.setOnClickListener{
            finish()
        }

        val saveButton = findViewById<ImageView>(R.id.save_question)
        saveButton.setOnClickListener{
            val getQuestion = findViewById<EditText>(R.id.Question).text.toString()
            val getAnswer = findViewById<EditText>(R.id.answer).text.toString()
            val wrongOption1 = findViewById<EditText>(R.id.wrongAnswer1).text.toString()
            val wrongOption2 = findViewById<EditText>(R.id.wrongAnswer2).text.toString()

            if((getQuestion == "") or (getAnswer == "") or (wrongOption1 == "") or (wrongOption2 == "")){
                Log.i("MainActivity", "Returned null data from AddCardActivity")
                val toast = Toast.makeText(applicationContext, "Incomplete Card", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast.show()
            }
            else {
                val data = Intent()
                data.putExtra(
                    "Question", getQuestion
                )
                data.putExtra(
                    "Answer", getAnswer
                )
                data.putExtra(
                    "wrongAnswer1", wrongOption1
                )
                data.putExtra(
                    "wrongAnswer2", wrongOption2
                )
                setResult(RESULT_OK, data)
                finish()
            }
        }
    }
}