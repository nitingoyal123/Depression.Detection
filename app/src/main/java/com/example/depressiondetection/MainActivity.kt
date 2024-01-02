package com.example.depressiondetection

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.depressiondetection.databinding.ActivityMainBinding
import com.google.android.material.color.utilities.Score
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : ComponentActivity() {

    private lateinit var databaseReference : DatabaseReference
    var PATIENT_INFO = "PatientInfo"
    var OBJ_QUESTIONS = "ObjectiveQuestions"
    lateinit var patientInfo : PatientDetails
    var SCORE = 0
    lateinit var NAME : String
    lateinit var AGE : String
    lateinit var PHONE_NUMBER : String
    lateinit var EMAIL : String
    lateinit var ADDRESS : String
    lateinit var DEPRESSION_LEVEL_OBJECTIVE : String
    var DEPRESSION_LEVEL_SUBJECTIVE : String = ""
    private lateinit var DATE : String
    private var QUESTION_NUMBER = 1
    private var TOTAL_QUESTIONS = 9
    private var Questions = ArrayList<String>()


    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
        databaseReference = FirebaseDatabase.getInstance().getReference(OBJ_QUESTIONS)

    }

    private fun putQuestion(key : String,value: String) {
        databaseReference.child(key).setValue(value)
    }

    private fun showQuestion() {


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (question in snapshot.children) {
                    Questions.add(question.value.toString())
                }
                if (QUESTION_NUMBER in 1..TOTAL_QUESTIONS) {

                    binding.txtQuestion.text = Questions[QUESTION_NUMBER-1]

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TAGY","Failed")
            }


        })

    }

    fun btnNext(view : View) {

        binding.apply {
            if (radioGroupsOptions.checkedRadioButtonId == -1) {
                Toast.makeText(this@MainActivity,"Select one of the above options",Toast.LENGTH_SHORT).show()
            } else {
                var checkedId = radioGroupsOptions.checkedRadioButtonId
                when(checkedId) {
                    radioButtonOpt1.id -> {
                        SCORE += 0
                    }
                    radioButtonOpt2.id -> {
                        SCORE += 1
                    }
                    radioButtonOpt3.id -> {
                        SCORE += 2
                    }
                    radioButtonOpt4.id -> {
                        SCORE += 3
                    }
                }
                radioGroupsOptions.clearCheck()
                if (QUESTION_NUMBER < TOTAL_QUESTIONS) {
                    QUESTION_NUMBER++
                    onStart()
                    showQuestion()
                } else if (QUESTION_NUMBER == TOTAL_QUESTIONS) {

                    //CALCULATING DEPRESSION LEVEL OF OBJECTIVE QUESTIONS
                    if(SCORE in 1..4) {
                        DEPRESSION_LEVEL_OBJECTIVE = "Minimal depression"
                    } else if (SCORE in 5..9) {
                        DEPRESSION_LEVEL_OBJECTIVE = "Mild depression"
                    } else if (SCORE in 10..14) {
                        DEPRESSION_LEVEL_OBJECTIVE = "Moderate depression"
                    } else if (SCORE in 15..19) {
                        DEPRESSION_LEVEL_OBJECTIVE = "Moderately severe depression"
                    } else {
                        DEPRESSION_LEVEL_OBJECTIVE = "Severe depression"
                    }

                    startActivity(Intent(this@MainActivity,SubjectiveQuestions::class.java))
                    val intent = Intent(this@MainActivity,SubjectiveQuestions::class.java)

                    //PASSING SCORE TO NEXT ACTIVITY
                    intent.putExtra(PATIENT_INFO,PatientDetails(NAME,AGE,PHONE_NUMBER,EMAIL,ADDRESS,DATE,SCORE,DEPRESSION_LEVEL_OBJECTIVE,DEPRESSION_LEVEL_SUBJECTIVE))
                    startActivity(intent)
                    Toast.makeText(this@MainActivity,DEPRESSION_LEVEL_OBJECTIVE,Toast.LENGTH_SHORT).show()

                    finish()
                    SCORE = 0 // RESET SCORE
                }
            }
        }
    }

    fun btnPrevious(view: View) {
        if (QUESTION_NUMBER == 1) {
            TODO()
        } else {
            QUESTION_NUMBER--
            onStart()
            showQuestion()
        }
    }

    override fun onStart() {
        super.onStart()
        if (QUESTION_NUMBER > 1 ) {
            binding.btnPrevious.visibility = View.VISIBLE
        } else {
            binding.btnPrevious.visibility = View.INVISIBLE
        }
        if (QUESTION_NUMBER == TOTAL_QUESTIONS) {
            binding.btnNext.text = "Submit"
        } else {
            binding.btnNext.text = "Next"
        }
        showQuestion()

        patientInfo = intent.getSerializableExtra(PATIENT_INFO,PatientDetails::class.java)!!
        SCORE = patientInfo.score
        NAME = patientInfo.name
        AGE = patientInfo.age
        PHONE_NUMBER = patientInfo.phoneNumber
        EMAIL = patientInfo.email
        ADDRESS = patientInfo.address
        DATE = patientInfo.date
        DEPRESSION_LEVEL_OBJECTIVE = patientInfo.depressionLevelObjective
        DEPRESSION_LEVEL_SUBJECTIVE = patientInfo.depressionLevelSubjective

    }
}
