package com.example.depressiondetection

import java.io.Serializable

data class PatientDetails(
    var name : String,
    var age : String,
    var phoneNumber : String,
    var email : String,
    var address : String,
    var date : String,
    var score : Int,
    var depressionLevelObjective : String,
    var depressionLevelSubjective: String
) : Serializable
