package com.example.depressiondetection

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.depressiondetection.databinding.ActivityPatientDetailsBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreKtxRegistrar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PatientDetailsActivity : AppCompatActivity() {

    var PATIENT_INFO = "PatientInfo"
    private lateinit var db : FirebaseFirestore

    private lateinit var binding : ActivityPatientDetailsBinding
    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@PatientDetailsActivity,R.layout.activity_patient_details)
        db = Firebase.firestore

        binding.apply {
            btnSave.setOnClickListener {
                var name = edtName.text.toString().trim()
                var age = edtAge.text.toString().trim()
                var phoneNumber = edtPhoneNumber.text.toString().trim()
                var email = edtEmail.text.toString().trim()
                var address = edtAddress.text.toString().trim()
                var dateAndTime = FieldValue.serverTimestamp().toString()
                if (TextUtils.isEmpty(name)) {
                    edtName.setBackgroundColor(resources.getColor(R.color.purple_200))
                } else {
                    edtName.background = null
                }
                if (TextUtils.isEmpty(age)) {
                    edtAge.setBackgroundColor(resources.getColor(R.color.purple_200))
                } else {
                    edtAge.background = null
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    edtPhoneNumber.setBackgroundColor(resources.getColor(R.color.purple_200))
                } else {
                    edtPhoneNumber.background = null
                }
                if (TextUtils.isEmpty(email)) {
                    edtEmail.setBackgroundColor(resources.getColor(R.color.purple_200))
                } else {
                    edtEmail.background = null
                }
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(email)) {

                    var details = hashMapOf(
                        "name" to name,
                        "age" to age,
                        "phoneNumber" to phoneNumber,
                        "email" to email,
                        "address" to address,
                        "timeAndDate" to dateAndTime
                    )

                    var doc = db.collection("PatientDetails").document(name)
                    doc.set(details)
                        .addOnSuccessListener {
                            Toast.makeText(this@PatientDetailsActivity,"Record successfully added !!!",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@PatientDetailsActivity,MainActivity::class.java)
                            intent.putExtra(PATIENT_INFO,PatientDetails(name,age,phoneNumber,email,address,dateAndTime,0,"",""))
                            startActivity(intent)
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(this@PatientDetailsActivity,"Oops! Something went wrong",Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }
}