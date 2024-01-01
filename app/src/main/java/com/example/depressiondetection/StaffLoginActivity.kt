package com.example.depressiondetection

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.compose.material3.TopAppBar
import androidx.databinding.DataBindingUtil
import com.example.depressiondetection.databinding.ActivityStaffLoginBinding

class StaffLoginActivity : AppCompatActivity() {
    private var EMAIL_KEY = "kkkk@gmail.com"
    private var STAFF_LOGIN_DATA = "Staff Login Data"
    private var PASSWORD = "123456"
    private lateinit var binding : ActivityStaffLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@StaffLoginActivity,R.layout.activity_staff_login)

        binding.btnLogin.setOnClickListener {
            var email = binding.edtEmailLogin.text.toString().trim()
            var password = binding.edtPasswordLogin.text.toString().trim()
            login(email,password)
        }
    }

    private fun login(email: String, password: String) {


        var sharedPreferences = getSharedPreferences(STAFF_LOGIN_DATA, MODE_PRIVATE)
        var correctPassword = sharedPreferences.getString(email,null)
        if (correctPassword.equals(password)) {
            Toast.makeText(this@StaffLoginActivity,"Logged In Successfully !!",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@StaffLoginActivity,NavigateToInfoActivity::class.java))
        }


    }

    override fun onStart() {
        super.onStart()
        var sharedPreferences = getSharedPreferences(STAFF_LOGIN_DATA, MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putString(EMAIL_KEY,PASSWORD)
        editor.apply()
    }
}