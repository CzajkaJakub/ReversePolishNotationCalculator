package com.example.reversepolishnotationcalculator

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class CalculatorView : AppCompatActivity() {

    val tag = "StateChanged"

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.i(tag, "onCreate")

        val stack: LinkedList<Double> = LinkedList()
        var result = 0.0
        var input = ""

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingsButton.setOnClickListener{ startActivity(Intent(this, SettingsActivity::class.java))}
        refreshColors()

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
        buttonDot.setOnClickListener{ input += "."; reloadLabels(stack, result, input) }


        buttonPlus.setOnClickListener{
            val dod = stack.peekFirst()
            if( dod != null) {
                result += dod
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

        buttonSub.setOnClickListener{
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
            result = sqrt(result)
            reloadLabels(stack, result, input)
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
            val newValue = -stack[0]
            stack.remove()
            stack.add(0, newValue)
            reloadLabels(stack, result, input)
        }





        buttonEnter.setOnClickListener{
            try{
                val inputNumber = input.toDouble()
                stack.add(inputNumber)
            }catch (e: Exception){}
            finally { input = ""; reloadLabels(stack, result, input) }
        }
    }

    private fun refreshColors() {
        val extras = intent.extras ?: return
        val backgroundColor = extras.getInt("BackgroundColor")
        val textColor = extras.getInt("TextColor")
        val buttonColor = extras.getInt("ButtonColor")

        changeBackgroundColor(backgroundColor)
        changeButtonColors(buttonColor)
        changeTextColors(textColor)
    }

    private fun changeBackgroundColor(backgroundColor: Int) {
        mainLayout.setBackgroundColor(backgroundColor)
    }

    private fun changeTextColors(textColor: Int) {
        buttonAc.setTextColor(textColor)
        buttonSwap.setTextColor(textColor)
        buttonDrop.setTextColor(textColor)
        button0.setTextColor(textColor)
        button1.setTextColor(textColor)
        button2.setTextColor(textColor)
        button3.setTextColor(textColor)
        button4.setTextColor(textColor)
        button5.setTextColor(textColor)
        button6.setTextColor(textColor)
        button7.setTextColor(textColor)
        button8.setTextColor(textColor)
        button9.setTextColor(textColor)
        buttonDiv.setTextColor(textColor)
        buttonDot.setTextColor(textColor)
        buttonChangeCharacter.setTextColor(textColor)
        buttonMinus.setTextColor(textColor)
        buttonPlus.setTextColor(textColor)
        buttonEnter.setTextColor(textColor)
        buttonSqrt.setTextColor(textColor)
        buttonPower.setTextColor(textColor)
        buttonSub.setTextColor(textColor)
    }

    private fun changeButtonColors(buttonColor: Int) {
        buttonAc.setBackgroundColor(buttonColor)
        buttonSwap.setBackgroundColor(buttonColor)
        buttonDrop.setBackgroundColor(buttonColor)
        button0.setBackgroundColor(buttonColor)
        button1.setBackgroundColor(buttonColor)
        button2.setBackgroundColor(buttonColor)
        button3.setBackgroundColor(buttonColor)
        button4.setBackgroundColor(buttonColor)
        button5.setBackgroundColor(buttonColor)
        button6.setBackgroundColor(buttonColor)
        button7.setBackgroundColor(buttonColor)
        button8.setBackgroundColor(buttonColor)
        button9.setBackgroundColor(buttonColor)
        buttonDiv.setBackgroundColor(buttonColor)
        buttonDot.setBackgroundColor(buttonColor)
        buttonChangeCharacter.setBackgroundColor(buttonColor)
        buttonMinus.setBackgroundColor(buttonColor)
        buttonPlus.setBackgroundColor(buttonColor)
        buttonEnter.setBackgroundColor(buttonColor)
        buttonSqrt.setBackgroundColor(buttonColor)
        buttonPower.setBackgroundColor(buttonColor)
        buttonSub.setBackgroundColor(buttonColor)

    }


    private fun reloadLabels(stack: LinkedList<Double>, result: Double, input: String) {
        var (firstLabelTextValue, secondLabelTextValue, thirdLabelTextValue, fourthLabelTextValue) = listOf("1: ", "2: ", "3: ", "4: ")
        val (resultFieldTextValue, inputFieldTextValue, stackFieldTextValue, stackSizeFieldTextValue) = listOf("Result : ", "Input : ", "Stack : ", "Stack size : ")
        try {
            firstLabelTextValue += stack[0].toString()
            secondLabelTextValue += stack[1].toString()
            thirdLabelTextValue += stack[2].toString()
            fourthLabelTextValue += stack[3].toString()

        } catch (e: Exception){}
        finally {
            first.text = firstLabelTextValue
            second.text = secondLabelTextValue
            third.text = thirdLabelTextValue
            fourth.text = fourthLabelTextValue
            stackSizeField.text = stackSizeFieldTextValue.plus(stack.size.toString())
    //        stackField.text = stackFieldTextValue.plus(stack.toString().substring(1, stack.toString().length - 1))
            resultLabel.text = resultFieldTextValue.plus(result.toString())
            inputField.text = inputFieldTextValue.plus(input)
        }
    }







}