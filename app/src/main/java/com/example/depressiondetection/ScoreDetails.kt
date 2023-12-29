package com.example.depressiondetection

import java.io.Serializable

data class ScoreDetails(

    var name : String,
    var phoneNumber : String,
    var score : Int,
    var depressionLevelObjective : String,
    var depressionLevelSubjective: String

) : Serializable
