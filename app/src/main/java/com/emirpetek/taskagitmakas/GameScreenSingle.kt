package com.emirpetek.taskagitmakas

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.emirpetek.taskagitmakas.databinding.ActivityGameScreenSingleBinding
import java.util.ArrayList
import java.util.Random

class GameScreenSingle : AppCompatActivity() {
    private lateinit var binding: ActivityGameScreenSingleBinding
    private var gameSituation : ArrayList<String> = ArrayList()
    private var player1Choice = ""
    private var player2Choice = ""
    var player1Score = 0
    var player2Score = 0
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor:Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameScreenSingleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.gameScreenSingleObj = this

        sharedPreferences = getSharedPreferences("single_game_scores", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        gameSituation.add("Rock")
        gameSituation.add("Paper")
        gameSituation.add("Scissors")

        player1Score = sharedPreferences.getString("single_game_user_score", "0")!!.toInt()
        player2Score =sharedPreferences.getString("single_game_computer_score", "0")!!.toInt()


        binding.player1Score = resources.getText(R.string.your_score).toString() + player1Score.toString()
        binding.player2Score = resources.getText(R.string.computer_score).toString() + player2Score.toString()


        hideStatusBar()
    }

    fun setWinResultText(state1:String,state2: String){ // kazandınız kaybettiniz gibi durumları ekrana yazdırır
        val winState = winChecker(state1,state2)
        when(winState){
            "win" -> {
                binding.textViewWinResult.text = resources.getText(R.string.win)
                player1Score++

                editor.putString("single_game_user_score",player1Score.toString())
                editor.apply()
                val score = sharedPreferences.getString("single_game_user_score", "0")

                binding.player1Score = resources.getText(R.string.your_score).toString() + score
            }
            "lose" -> {
                binding.textViewWinResult.text = resources.getText(R.string.win_computer)
                player2Score++

                editor.putString("single_game_computer_score",player2Score.toString())
                editor.apply()
                val score = sharedPreferences.getString("single_game_computer_score", "0")

                binding.player2Score = resources.getText(R.string.computer_score).toString() + score

            }
            "draw" -> {
                binding.textViewWinResult.text = resources.getText(R.string.draw)
            }
        }
    }

    // taş kağıt veya makas seçimlerini data binding ile alır
    fun btnPaper(){
        player1Choice = "Paper"
        binding.imageViewPlayer1.setImageResource(R.drawable.paper)
        randomGameComputer()
        setWinResultText(player1Choice,randomGameComputer())
    }

    fun btnRock(){
        player1Choice = "Rock"
        binding.imageViewPlayer1.setImageResource(R.drawable.rock)
        randomGameComputer()
        setWinResultText(player1Choice,randomGameComputer())
    }

    fun btnScissor(){
        player1Choice = "Scissors"
        binding.imageViewPlayer1.setImageResource(R.drawable.scissor)
        randomGameComputer()
        setWinResultText(player1Choice,randomGameComputer())
    }


    fun randomGameComputer():String{
        val randomNumber = Random().nextInt(3)
        player2Choice = gameSituation.get(randomNumber) // random olarak taş kağıt makastan birini seçer
        when(player2Choice){ // bilgisayarın yaptığı seçim ekranda gösterilir
            "Paper" -> binding.imageViewPlayer2.setImageResource(R.drawable.paper)
            "Rock" -> binding.imageViewPlayer2.setImageResource(R.drawable.rock)
            "Scissors" -> binding.imageViewPlayer2.setImageResource(R.drawable.scissor)
        }
        return player2Choice
    }

    fun winChecker(state1:String,state2:String):String{ // kazanma ihtimallerini kontrol eder ve bir değer döndürür
        var winState = ""
        if (state1 == "Paper" && state2 == "Rock") winState = "win"
        if (state1 == "Paper" && state2 == "Scissors") winState = "lose"
        if (state1 == "Rock" && state2 == "Paper") winState = "lose"
        if (state1 == "Rock" && state2 == "Scissors") winState = "win"
        if (state1 == "Scissors" && state2 == "Paper") winState = "win"
        if (state1 == "Scissors" && state2 == "Rock") winState = "lose"
        if (state1 == state2) winState = "draw"
        return winState
    }

    fun resetScore(){ // kaydedilen skorları sıfırlar
        val ad = AlertDialog.Builder(this)
        ad.setTitle(resources.getText(R.string.reset_score))
        ad.setMessage(resources.getText(R.string.sure_for_reset_score))
        ad.setPositiveButton(resources.getText(R.string.yes)){ dialog, which ->
            editor.putString("single_game_user_score","0") // user skor sıfırlama
            editor.putString("single_game_computer_score","0") // bilgisayarın skorunu sıfırlama
            editor.apply()

            // skorlar sıfırlandıktan sonra textviewde de skorları sıfırlama yapar
            val score1 = sharedPreferences.getString("single_game_user_score", "0")
            val score2 = sharedPreferences.getString("single_game_computer_score", "0")
            binding.player1Score = resources.getText(R.string.your_score).toString() + score1
            binding.player2Score = resources.getText(R.string.computer_score).toString() + score2
            player1Score = 0
            player2Score = 0
        }

        ad.create()
        ad.show()

    }


    fun hideStatusBar(){ // status barı gizler
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }
}