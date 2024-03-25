package com.emirpetek.taskagitmakas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.emirpetek.taskagitmakas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mvvmObj:MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainActivityObj = this

        hideStatusBar()

    }

    fun hideStatusBar(){
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        actionBar?.hide()
    }


    fun btnPlay(){
        Toast.makeText(this,"play tıklandı", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this,GameScreenSingle::class.java))
    }

    fun btnPlayOnline(){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.alert_play_online_options, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val alertDialog = builder.create()


        val buttonOnlineCreateRoom = dialogView.findViewById<Button>(R.id.buttonOnlineCreateRoom)
        val buttonOnlineJoinRoom = dialogView.findViewById<Button>(R.id.buttonOnlineJoinRoom)
        buttonOnlineCreateRoom.setOnClickListener { Toast.makeText(this,"ksdjfsdlş",Toast.LENGTH_SHORT).show() }
        buttonOnlineJoinRoom.setOnClickListener { Toast.makeText(this,"qweqeeqwwqe",Toast.LENGTH_SHORT).show() }
        alertDialog.show()

    }


}