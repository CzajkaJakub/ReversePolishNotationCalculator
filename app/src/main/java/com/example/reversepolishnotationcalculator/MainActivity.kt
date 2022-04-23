package com.example.reversepolishnotationcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val stack: LinkedList<Double> = LinkedList()
        var result = 0.0
        var input = ""

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button0.setOnClickListener{ input += 0 }
        button1.setOnClickListener{ input += 1 }
        button2.setOnClickListener{ input += 2 }
        button3.setOnClickListener{ input += 3 }
        button4.setOnClickListener{ input += 4 }
        button5.setOnClickListener{ input += 5 }
        button6.setOnClickListener{ input += 6 }
        button7.setOnClickListener{ input += 7 }
        button8.setOnClickListener{ input += 8 }
        button9.setOnClickListener{ input += 9 }
        buttonDot.setOnClickListener{ input += "." }


        buttonPlus.setOnClickListener{
            val dod = stack.peekFirst()
            if( dod != null) {
                result += dod
                stack.remove()
                reloadLabels(stack, result)
            }
        }

        buttonMinus.setOnClickListener{
            val dod = stack.peekFirst()
            if( dod != null) {
                result -= dod
                stack.remove()
                reloadLabels(stack, result)
            }
        }

        buttonSub.setOnClickListener{
            val dod = stack.peekFirst()
            if( dod != null) {
                result *= dod
                stack.remove()
                reloadLabels(stack, result)
            }
        }

        buttonDiv.setOnClickListener{
            val dod = stack.peekFirst()
            if( dod != null) {
                result /= dod
                stack.remove()
                reloadLabels(stack, result)
            }
        }

        buttonDrop.setOnClickListener{
            val dod = stack.peekFirst()
            if( dod != null) {
                stack.remove()
                reloadLabels(stack, result)
            }
        }

        buttonPower.setOnClickListener{
            val dod = stack.peekFirst()
            if( dod != null) {
                result = result.pow(dod)
                stack.remove()
                reloadLabels(stack, result)
            }
        }

        buttonSqrt.setOnClickListener{
            result = sqrt(result)
            reloadLabels(stack, result)
        }

        buttonSwap.setOnClickListener{
            try{
                val val1 = stack[0]
                val val2 = stack[1]
                stack.remove()
                stack.remove()
                stack.add(0, val1)
                stack.add(0, val2)
                reloadLabels(stack, result)
            }catch (e: Exception){}
        }

        buttonAc.setOnClickListener{
            stack.clear()
            result = 0.0
            reloadLabels(stack, result)
        }





        buttonEnter.setOnClickListener{
            try{
                val inputNumber = input.toDouble()
                stack.add(inputNumber)
                reloadLabels(stack, result)
            }catch (e: Exception){}
            finally { input = "" }
        }
    }

    private fun reloadLabels(stack: LinkedList<Double>, result: Double) {

        val resultLabelText = "Stack : $stack, Stack amount ${stack.size}, Result $result"
        resultLabel.text = resultLabelText

        var firstLabelText = "1: "
        var secondLabelText = "2: "
        var thirdLabelText = "3: "
        var fourthLabelText = "4: "

        try {
            firstLabelText += stack[0].toString()
            secondLabelText += stack[1].toString()
            thirdLabelText += stack[2].toString()
            fourthLabelText += stack[3].toString()

        } catch (e: Exception){}
        finally {
            first.text = firstLabelText
            second.text = secondLabelText
            third.text = thirdLabelText
            fourth.text = fourthLabelText
        }
    }


}