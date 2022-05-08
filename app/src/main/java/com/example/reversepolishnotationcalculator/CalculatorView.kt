package com.example.reversepolishnotationcalculator

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.android.synthetic.main.calculator_activity.*
import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class CalculatorView : AppCompatActivity() {

    private val mapper = jacksonObjectMapper()
    private var settings: Settings? = null
    private val stack: LinkedList<Double> = LinkedList()
    private var result = 0.0
    private var input = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        settings = readSettingsFromFile()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator_activity)
        setButtonsListeners()
        setBackgroundColor(settings!!.backgroundColor)
        setButtonColor(settings!!.buttonColor)
        changeTextColor(settings!!.textColor)
    }

    private fun readSettingsFromFile(): Settings {
        val path = this.filesDir.toString().plus("/colors.json")
        if(!File(path).exists()){
            return Settings(Color.BLACK, Color.RED, Color.WHITE, 2)
        }
        return mapper.readValue(File(path))
    }

    private fun setButtonsListeners() {
        settingsButton.setOnClickListener{ startActivity(Intent(this, SettingsActivity::class.java))}
        button0.setOnClickListener{ input += 0; reloadLabels(stack, result, input) }
        button1.setOnClickListener{ input += 1; reloadLabels(stack, result, input) }
        button2.setOnClickListener{ input += 2; reloadLabels(stack, result, input) }
        button3.setOnClickListener{ input += 3; reloadLabels(stack, result, input) }
        button4.setOnClickListener{ input += 4; reloadLabels(stack, result, input) }
        button5.setOnClickListener{ input += 5; reloadLabels(stack, result, input) }
        button6.setOnClickListener{ input += 6; reloadLabels(stack, result, input) }
        button7.setOnClickListener{ input += 7; reloadLabels(stack, result, input) }
        button8.setOnClickListener{ input += 8; reloadLabels(stack, result, input) }
        button9.setOnClickListener{ input += 9; reloadLabels(stack, result, input) }
        buttonDot.setOnClickListener{
            when(input){
                "" -> input += "0."
                else -> if (!input.contains(".")){input += ".";}
            }
            reloadLabels(stack, result, input) }
        buttonPlus.setOnClickListener{
            val value = stack.peekFirst()
            if( value != null) {
                result += value
                stack.remove()
                reloadLabels(stack, result, input)
            }
        }
        buttonMinus.setOnClickListener{
            val dod = stack.peekFirst()
            if( dod != null) {
                result -= dod
                stack.remove()
                reloadLabels(stack, result, input)
            }
        }
        buttonMultiplication.setOnClickListener{
            val dod = stack.peekFirst()
            if( dod != null) {
                result *= dod
                stack.remove()
                reloadLabels(stack, result, input)
            }
        }
        buttonDiv.setOnClickListener{
            val dod = stack.peekFirst()
            if( dod != null) {
                result /= dod
                stack.remove()
                reloadLabels(stack, result, input)
            }
        }
        buttonDrop.setOnClickListener{
            val dod = stack.peekFirst()
            if( dod != null) {
                stack.remove()
                reloadLabels(stack, result, input)
            }
        }
        buttonPower.setOnClickListener{
            val dod = stack.peekFirst()
            if( dod != null) {
                result = result.pow(dod)
                stack.remove()
                reloadLabels(stack, result, input)
            }
        }
        buttonSqrt.setOnClickListener{
            if(result < 0){
                val rootError = "There is no root from a negative number"
                inputField.text = rootError
            } else {
                result = sqrt(result)
                reloadLabels(stack, result, input)
            }
        }
        buttonSwap.setOnClickListener{
            try{
                val val1 = stack[0]
                val val2 = stack[1]
                stack.remove()
                stack.remove()
                stack.add(0, val1)
                stack.add(0, val2)
                reloadLabels(stack, result, input)
            }catch (e: Exception){}
        }
        buttonAc.setOnClickListener{
            stack.clear()
            result = 0.0
            input = ""
            reloadLabels(stack, result, input)
        }
        buttonChangeCharacter.setOnClickListener{
            try{
                val newValue = -stack[0]
                stack.remove()
                stack.add(0, newValue)
                reloadLabels(stack, result, input)
            } catch (e: Exception){
                val errorText = "Add some number to stack!"
                inputField.text = errorText
            }

        }
        buttonEnter.setOnClickListener{
            try{
                val inputNumber = input.toDouble()
                stack.add(inputNumber)
            }catch (e: Exception){}
            finally { input = ""; reloadLabels(stack, result, input) }
        }
    }

    private fun setBackgroundColor(backgroundColor: Int) {
        calculatorBackground.setBackgroundColor(backgroundColor)
    }

    private fun changeTextColor(textColor: Int) {
        val elements: List<TextView> = listOf<TextView>(buttonAc, buttonSwap, buttonDrop, button0, button1, button2, button3, inputField,
                                              button4, button5, button6, button7, button8, button9, buttonDiv, resultLabel,
                                              buttonDot, buttonChangeCharacter, buttonMinus, buttonPlus, buttonEnter,
                                              buttonSqrt, buttonPower, buttonMultiplication, firstStackLabel, secondStackLabel, thirdStackLabel, fourthStackLabel, stackSizeField)
        fun colorElement(element: TextView) = element.setTextColor(textColor)
        elements.forEach{colorElement(it)}
    }

    private fun setButtonColor(buttonColor: Int) {
        val elements: List<Button> = listOf<Button>(buttonAc, buttonSwap, buttonDrop, button0, button1, button2, button3,
            button4, button5, button6, button7, button8, button9, buttonDiv,
            buttonDot, buttonChangeCharacter, buttonMinus, buttonPlus, buttonEnter,
            buttonSqrt, buttonPower, buttonMultiplication)
        fun colorElement(element: Button) = element.setBackgroundColor(buttonColor)
        elements.forEach{colorElement(it)}
    }

    private fun reloadLabels(stack: LinkedList<Double>, result: Double, input: String) {
        var (firstLabelTextValue, secondLabelTextValue, thirdLabelTextValue, fourthLabelTextValue) = listOf("1: ", "2: ", "3: ", "4: ")
        val (resultFieldTextValue, inputFieldTextValue, stackSizeFieldTextValue) = listOf("Result : ", "Input : ", "Stack size : ")
        fun round(x : Double) = ((x * (10.0).pow(settings!!.accuracy.toDouble())).roundToInt()) / ((10.0).pow(settings!!.accuracy.toDouble()))
        try {
            firstLabelTextValue += round(stack[0]).toString()
            secondLabelTextValue += round(stack[1]).toString()
            thirdLabelTextValue += round(stack[2]).toString()
            fourthLabelTextValue += round(stack[3]).toString()

        } catch (e: Exception){}
        finally {
            firstStackLabel.text = firstLabelTextValue
            secondStackLabel.text = secondLabelTextValue
            thirdStackLabel.text = thirdLabelTextValue
            fourthStackLabel.text = fourthLabelTextValue
            stackSizeField.text = stackSizeFieldTextValue.plus(stack.size.toString())
            resultLabel.text = resultFieldTextValue.plus(round(result))
            inputField.text = inputFieldTextValue.plus(input)
        }
    }







}