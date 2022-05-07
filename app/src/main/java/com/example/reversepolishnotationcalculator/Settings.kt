package com.example.reversepolishnotationcalculator

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Settings(

    @JsonProperty("backgroundColor")
    var backgroundColor: Int,

    @JsonProperty("buttonColor")
    var buttonColor: Int,

    @JsonProperty("textColor")
    var textColor: Int,

    @JsonProperty("accuracy")
    var accuracy:Int)



