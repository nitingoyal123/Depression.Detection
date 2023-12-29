package com.example.depressiondetection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.depressiondetection.databinding.ActivitySubjectiveQuestionsBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SubjectiveQuestions : AppCompatActivity() {

    var PATIENT_INFO = "PatientInfo"
    var OBJ_QUESTIONS = "ObjectiveQuestions"
    var patientInfo = intent.getSerializableExtra(PATIENT_INFO,ScoreDetails::class.java)!!
    var SCORE = patientInfo.score
    var NAME = patientInfo.name
    var PHONE_NUMBER = patientInfo.phoneNumber
    var DEPRESSION_LEVEL_OBJECTIVE = patientInfo.depressionLevelObjective
    var DEPRESSION_LEVEL_SUBJECTIVE = patientInfo.depressionLevelSubjective

    private lateinit var databaseReference : DatabaseReference
    var SUB_QUESTIONS = "SubjectiveQuestions"
    private var QUESTION_NUMBER = 1
    private var TOTAL_QUESTIONS = 3
    private var Questions = ArrayList<String>()


    private lateinit var binding : ActivitySubjectiveQuestionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@SubjectiveQuestions,R.layout.activity_subjective_questions)
        databaseReference = FirebaseDatabase.getInstance().getReference(SUB_QUESTIONS)


        showQuestions()

    }

    private fun showQuestions() {
        //GET QUESTIONS
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (question in snapshot.children) {
                    Questions.add(question.value.toString())
                    if (QUESTION_NUMBER in 1..TOTAL_QUESTIONS) {
                        binding.txtQuestion.text = Questions[QUESTION_NUMBER-1]
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("TAGY","Failed")
            }
        })
    }

    override fun onStart() {
        super.onStart()
        if (QUESTION_NUMBER == TOTAL_QUESTIONS) {
            binding.btnSubmit.text = "Submit"
        }
    }
}