package com.example.depressiondetection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.depressiondetection.databinding.ActivityNavigateToInfoBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NavigateToInfoActivity : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    private lateinit var binding : ActivityNavigateToInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@NavigateToInfoActivity,R.layout.activity_navigate_to_info)


    }

    override fun onStart() {
        super.onStart()
        db = Firebase.firestore


    }
}