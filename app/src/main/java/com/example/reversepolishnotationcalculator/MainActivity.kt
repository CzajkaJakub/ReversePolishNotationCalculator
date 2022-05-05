package com.example.reversepolishnotationcalculator

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

class MainActivity : AppCompatActivity() {

    val tag = "StateChanged"

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.i(tag, "onCreate")

        val stack: LinkedList<Double> = LinkedList()
        var result = 0.0
        var input = ""

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            stackField.text = stackFieldTextValue.plus(stack.toString().substring(1, stack.toString().length - 1))
            resultLabel.text = resultFieldTextValue.plus(result.toString())
            inputField.text = inputFieldTextValue.plus(input)
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(tag, "onRestart")

    }

    override fun onStart() {
        super.onStart()
        Log.i(tag, "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.i(tag, "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.i(tag, "onPause")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "onDestroy")

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i(tag, "onConfigurationChanged")

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(tag, "onRestoreInstanceState")

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.i(tag, "onSaveInstanceState")

    }





}