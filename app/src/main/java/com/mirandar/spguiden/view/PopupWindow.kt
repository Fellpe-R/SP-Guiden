package com.mirandar.spguiden.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mirandar.spguiden.R
import com.mirandar.spguiden.control.Utils

class PopupWindow : AppCompatActivity() {

    private val close = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) { finish() }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.popup_window)

        registerReceiver(close, IntentFilter(action), RECEIVER_NOT_EXPORTED)

        val utils = Utils(this)
        var imgView : FullImgFragment? = null

        val view = intent.getStringExtra("Fragment")
        val imgsPosition = intent.getStringExtra(("position"))

        val img = GalleryFragment(this)
        val locations = LocationsFragment(this)
        val about = AboutFragment(this)

        if (imgsPosition != null) {
            imgView = FullImgFragment(imgsPosition, utils.getImg(), this)
        } else {
            utils.log("Null! | imgsPosition: "+imgsPosition)
        }

        when (view) {
            "img" -> openFrag(img)
            "locations" -> openFrag(locations)
            "About" -> openFrag(about)
            "imgView" -> openFrag(imgView!!)
            else -> utils.message("Página indisponível!")
        }


    }
    private fun openFrag(f: Fragment){
        supportFragmentManager.beginTransaction().
        add(R.id.fragment_container, f).commit()
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(close)
    }
    private val action: String = "close"
}