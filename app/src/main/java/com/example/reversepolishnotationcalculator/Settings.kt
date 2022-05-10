package com.example.reversepolishnotationcalculator

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Settings(

    @JsonProperty("backgroundColor")
    var backgroundColor: Int,

    @JsonProperty("buttonsNumbers")
    var buttonsNumbers: Int,

    @JsonProperty("buttonsOperations")
    var buttonsOperations: Int,

    @JsonProperty("buttonAc")
    var buttonAc: Int,

    @JsonProperty("textColor")
    var textColor: Int,

    @JsonProperty("accuracy")
    var accuracy:Int)





