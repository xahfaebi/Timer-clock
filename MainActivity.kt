package com.example.timerapp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var timerView: TimerView
    private lateinit var timeInput: EditText
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private lateinit var timeDisplay: TextView
    private var timer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 0
    private var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerView = findViewById(R.id.timerView)
        timeInput = findViewById(R.id.timeInput)
        startButton = findViewById(R.id.startButton)
        resetButton = findViewById(R.id.resetButton)
        timeDisplay = findViewById(R.id.timeDisplay)

        startButton.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        if (!isTimerRunning) {
            if (timeLeftInMillis == 0L) {
                val minutes = timeInput.text.toString().toIntOrNull() ?: 0
                timeLeftInMillis = minutes * 60 * 1000L
            }

            timer = object : CountDownTimer(timeLeftInMillis, 10) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeftInMillis = millisUntilFinished
                    updateTimerUI()
                }

                override fun onFinish() {
                    isTimerRunning = false
                    updateButtons()
                    timeLeftInMillis = 0
                    updateTimerUI()
                }
            }.start()

            isTimerRunning = true
            updateButtons()
            timeInput.visibility = View.GONE
            timeDisplay.visibility = View.VISIBLE
        }
    }

    private fun pauseTimer() {
        timer?.cancel()
        isTimerRunning = false
        updateButtons()
    }

    private fun resetTimer() {
        timer?.cancel()
        timeLeftInMillis = 0
        isTimerRunning = false
        updateButtons()
        updateTimerUI()
        timeInput.visibility = View.VISIBLE
        timeDisplay.visibility = View.GONE
        timeInput.setText("")
    }

    private fun updateTimerUI() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        val millis = (timeLeftInMillis % 1000) / 10

        val timeFormatted = String.format("%02d:%02d.%02d", minutes, seconds, millis)
        timeDisplay.text = timeFormatted

        // Update progress in TimerView
        val progress = if (timeLeftInMillis > 0) {
            timeLeftInMillis.toFloat() / (timeInput.text.toString().toIntOrNull() ?: 1) / 60 / 1000
        } else 0f

        timerView.setProgress(progress)
    }

    private fun updateButtons() {
        if (isTimerRunning) {
            startButton.text = "Pause"
            resetButton.visibility = View.GONE
        } else {
            startButton.text = "Start"
            resetButton.visibility = View.VISIBLE
        }
    }
}
