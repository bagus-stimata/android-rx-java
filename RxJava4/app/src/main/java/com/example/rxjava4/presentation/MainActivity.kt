package com.example.rxjava4.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rxjava4.databinding.ActivityMainBinding
import com.example.rxjava4.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val myMainBinding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fragmentManager = supportFragmentManager

        fragmentManager
            .beginTransaction()
            .replace(R.id.container_create_note, CreateNoteFragment())
            .commit()

//        fragmentManager
//            .beginTransaction()
//            .replace(R.id.container_create_note, BlankFragment() )
//            .commit()

        fragmentManager
            .beginTransaction()
            .replace(R.id.container_list_note, ListNoteFragment() )
            .commit()
        fragmentManager
                .beginTransaction()
                .replace(R.id.container_mutable_note, MutableNoteFragment() )
                .commit()


    }
}