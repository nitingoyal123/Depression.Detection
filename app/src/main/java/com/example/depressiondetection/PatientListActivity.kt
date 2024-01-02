package com.example.depressiondetection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.depressiondetection.databinding.ActivityPatientListBinding

class PatientListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPatientListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@PatientListActivity,R.layout.activity_patient_list)





    }
}