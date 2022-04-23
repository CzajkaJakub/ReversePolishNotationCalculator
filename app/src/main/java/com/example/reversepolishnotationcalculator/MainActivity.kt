package com.example.reversepolishnotationcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val stack: Queue<Int> = LinkedList()
        val result = 0


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button0.setOnClickListener{ stack.add(0) }
        button1.setOnClickListener{ stack.add(1) }
        button2.setOnClickListener{ stack.add(2) }
        button3.setOnClickListener{ stack.add(3) }
        button4.setOnClickListener{ stack.add(4) }
        button5.setOnClickListener{ stack.add(5) }
        button6.setOnClickListener{ stack.add(6) }
        button7.setOnClickListener{ stack.add(7) }
        button8.setOnClickListener{ stack.add(8) }
        button9.setOnClickListener{ stack.add(9) }




    }


}