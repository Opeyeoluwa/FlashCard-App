package com.example.flashcard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule
import kotlin.concurrent.timerTask
class MainActivity : AppCompatActivity() {
    lateinit var flashcardDatabase : FlashcardDatabase
    var allFlashcards = mutableListOf<Flashcard>()
    var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countDownTimer = object : CountDownTimer(16000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                findViewById<TextView>(R.id.timer).text = "Time Left: " + millisUntilFinished / 1000
            }
            override fun onFinish() {}
        }
        fun startTimer() {
            countDownTimer?.cancel()
            countDownTimer?.start()
        }

        flashcardDatabase = FlashcardDatabase(this)
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

        val viewAnswerButton = findViewById<ImageView>(R.id.button)
        val viewAnswerButton2 = findViewById<ImageView>(R.id.button2)
        val question = findViewById<TextView>(R.id.flashcard_question)
        val ans = findViewById<TextView>(R.id.flashcard)
        val ans1 =  findViewById<TextView>(R.id.flashcard_answer)
        val ans2 = findViewById<TextView>(R.id.flashcard_answer2)
        val ans3 = findViewById<TextView>(R.id.flashcard_answer3)

        if(allFlashcards.isNotEmpty()) {
            question.text = allFlashcards[0].question
            ans.text = allFlashcards[0].answer
            ans1.text = allFlashcards[0].wrongAnswer1
            ans2.text = allFlashcards[0].wrongAnswer2
            ans3.text = allFlashcards[0].answer
        }

        startTimer()

        question.setOnClickListener{
            question.setCameraDistance(25000F)
            ans.setCameraDistance(25000F)
            question.animate().rotationY(90f).setDuration(200).withEndAction((Runnable {
                ans.visibility = View.VISIBLE
                ans.rotationY = -90f
                ans.animate().rotationY(0f).setDuration(200).start()
            })).start()
        }
        ans.setOnClickListener{
            question.setCameraDistance(25000F)
            ans.setCameraDistance(25000F)
            ans.animate().rotationY(90f).setDuration(200).withEndAction((Runnable {
                question.visibility = View.VISIBLE
                question.rotationY = -90f
                question.animate().rotationY(0f).setDuration(200).start()
            })).start()
        }

        ans3.setOnClickListener {
            ans3.setBackgroundColor(Color.parseColor("#90ee90"))

            val viewKonfetti = findViewById<nl.dionsegijn.konfetti.xml.KonfettiView>(R.id.konfettiView)
            viewKonfetti.start(Party(speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
                position = Position.Relative(0.5, 0.3)))
        }
        ans2.setOnClickListener {
            ans3.setBackgroundColor(Color.parseColor("#90ee90"))
            ans2.setBackgroundColor(Color.parseColor("#FF0000"))
        }
        ans1.setOnClickListener {
            ans1.setBackgroundColor(Color.parseColor("#FF0000"))
            ans3.setBackgroundColor(Color.parseColor("#90ee90"))
        }

        viewAnswerButton.setOnClickListener{
            ans1.isVisible = !ans1.isVisible
            ans2.isVisible = !ans2.isVisible
            ans3.isVisible = !ans3.isVisible
            viewAnswerButton.isVisible = !viewAnswerButton.isVisible
            viewAnswerButton2.isVisible = !viewAnswerButton2.isVisible
        }
        viewAnswerButton2.setOnClickListener{
            ans1.isVisible = !ans1.isVisible
            ans2.isVisible = !ans2.isVisible
            ans3.isVisible = !ans3.isVisible
            viewAnswerButton2.isVisible = !viewAnswerButton2.isVisible
            viewAnswerButton.isVisible = !viewAnswerButton.isVisible
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
            val data: Intent? = result.data
            if (data != null) {
                val string1 = data.getStringExtra("Question")
                val string2 = data.getStringExtra("Answer")
                val string3 = data.getStringExtra("wrongAnswer1")
                val string4 = data.getStringExtra("wrongAnswer2")

                question.text = string1
                ans.text = string2
                ans1.text = string3
                ans2.text = string4
                ans3.text = string2

                Log.i("MainActivity", "string1: $string1")
                Log.i("MainActivity", "string2: $string2")
                Snackbar.make(findViewById(R.id.flashcard_question), "Successfully created card", Snackbar.LENGTH_SHORT).show()

                if (!string1.isNullOrEmpty() && !string2.isNullOrEmpty() && !string3.isNullOrEmpty() && !string4.isNullOrEmpty()) {
                    flashcardDatabase.insertCard(Flashcard(string1, string2, string3, string4))
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()

                }
            }
            else{
                Log.i("MainActivity", "Returned null data from AddCardActivity")
            }
        }

        val addButton = findViewById<ImageView>(R.id.add_question)
        addButton.setOnClickListener{
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        val editResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            val data: Intent? = result.data
            if (data != null){
                val string1 = data.getStringExtra("Question")
                val string2 = data.getStringExtra("Answer")
                val string3 = data.getStringExtra("wrongAnswer1")
                val string4 = data.getStringExtra("wrongAnswer2")

                question.text = string1
                ans.text = string2
                ans1.text = string3
                ans2.text = string4
                ans3.text = string2

                if (!string1.isNullOrEmpty() && !string2.isNullOrEmpty() && !string3.isNullOrEmpty() && !string4.isNullOrEmpty()) {
                    flashcardDatabase.updateCard(Flashcard(string1, string2, string3, string4))
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                }
            }
            else {
                Log.i("MainActivity", "Returned Null dta from AddCardActivity")
            }
        }

        val editButton = findViewById<ImageView>(R.id.edit_question)
        editButton.setOnClickListener{
            val intent = Intent(this, AddCardActivity::class.java)
            val editQuestion = question.text.toString()
            val editAnswer = ans.text.toString()
            val editWrongAnswer1 = ans1.text.toString()
            val editWrongAnswer2 = ans2.text.toString()
            intent.putExtra("stringQuestionKey", editQuestion)
            intent.putExtra("stringAnswerKey", editAnswer )
            intent.putExtra("stringWrongKey1", editWrongAnswer1 )
            intent.putExtra("stringWrongKey2", editWrongAnswer2 )
            editResultLauncher.launch(intent)
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        fun getRandomNumber(min: Int, max: Int): Int{
            return(min..max).random()
        }

        fun norm(){
            ans1.setBackgroundColor(Color.parseColor("#cfd9df"))
            ans2.setBackgroundColor(Color.parseColor("#cfd9df"))
            ans3.setBackgroundColor(Color.parseColor("#cfd9df"))

            var cardIndex: Int
            allFlashcards = flashcardDatabase.getAllCards().toMutableList()

            do {
                cardIndex = getRandomNumber(0, allFlashcards.size - 1)
                var dbQuestion = allFlashcards[cardIndex].question
            } while (question.text == dbQuestion);

            var(dbQuestion, answer, wronganswer1, wronganswer2) = allFlashcards[cardIndex]
            question.text = dbQuestion
            ans.text = answer
            ans1.text = wronganswer1
            ans2.text = wronganswer2
            ans3.text = answer
        }

        val nextButton = findViewById<ImageView>(R.id.next_question)
        nextButton.setOnClickListener(){
           if(allFlashcards.isEmpty() or (allFlashcards.size==1)){
               Snackbar.make(findViewById(R.id.flashcard_question), "No card left, create Card", Snackbar.LENGTH_SHORT).show()
           }
           else{
               val leftOutAnim = AnimationUtils.loadAnimation(this, R.anim.left_out)
               val rightInAnim = AnimationUtils.loadAnimation(this, R.anim.right_in)

               startTimer()

               leftOutAnim.setAnimationListener(object: Animation.AnimationListener {
                   override fun onAnimationStart(animation: Animation?){
                       if (ans.isVisible){
                           ans.rotationY = 90f
                           question.rotationY = 0f
                       }
                   }
                   override fun onAnimationEnd(animation: Animation?){

                       findViewById<View>(R.id.flashcard_question).startAnimation(rightInAnim)
                       findViewById<View>(R.id.flashcard_answer).startAnimation(rightInAnim)
                       findViewById<View>(R.id.flashcard_answer2).startAnimation(rightInAnim)
                       findViewById<View>(R.id.flashcard_answer3).startAnimation(rightInAnim)
                   }
                   override fun onAnimationRepeat(animation: Animation?){

                   }
               })
               findViewById<View>(R.id.flashcard_question).startAnimation(leftOutAnim)
               findViewById<View>(R.id.flashcard_answer).startAnimation(leftOutAnim)
               findViewById<View>(R.id.flashcard_answer2).startAnimation(leftOutAnim)
               findViewById<View>(R.id.flashcard_answer3).startAnimation(leftOutAnim)

               Timer().schedule(500){
                   norm()
               }
           }
        }

        val deleteButton = findViewById<ImageView>(R.id.delete_question)
        deleteButton.setOnClickListener(){

            if(allFlashcards.isEmpty() or (allFlashcards.size==1)){
                Snackbar.make(findViewById(R.id.flashcard_question), "No card left, create Card", Snackbar.LENGTH_SHORT).show()
            }
            else{
                val questionToDelete = findViewById<TextView>(R.id.flashcard_question).text.toString()
                flashcardDatabase.deleteCard(questionToDelete)
                allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                norm()
            }

        }
    }
}