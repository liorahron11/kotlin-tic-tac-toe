package com.example.tic_tac_toc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var board = Array(3) { Array(3) { "" } }
    private var currentPlayer = "X"
    private lateinit var statusTextView: TextView
    private val buttons = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)

        // Initialize buttons and set click listeners
        val ids = arrayOf(
            intArrayOf(R.id.button00, R.id.button01, R.id.button02),
            intArrayOf(R.id.button10, R.id.button11, R.id.button12),
            intArrayOf(R.id.button20, R.id.button21, R.id.button22)
        )
        for (i in 0..2) {
            for (j in 0..2) {
                val button = findViewById<Button>(ids[i][j])
                buttons.add(button)
                button.setOnClickListener { onCellClicked(i, j, button) }
            }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener { resetBoard() }
    }

    private fun onCellClicked(row: Int, col: Int, button: Button) {
        if (board[row][col].isNotEmpty()) return // Cell already filled
        board[row][col] = currentPlayer
        button.text = currentPlayer

        if (checkWinner()) {
            statusTextView.text = "Player $currentPlayer Wins!"
            disableButtons()
        } else if (isBoardFull()) {
            statusTextView.text = "It's a Draw!"
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            statusTextView.text = "Player $currentPlayer's Turn"
        }
    }

    private fun checkWinner(): Boolean {
        for (i in 0..2) {
            // Check rows and columns
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer ||
                board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer
            ) return true
        }
        // Check diagonals
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer ||
            board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer
        ) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { it.isNotEmpty() } }
    }

    private fun disableButtons() {
        buttons.forEach { it.isEnabled = false }
    }

    private fun resetBoard() {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        statusTextView.text = "Player X's Turn"
        buttons.forEach {
            it.text = ""
            it.isEnabled = true
        }
    }
}