package com.example.reversepolishnotationcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_settings.*
import yuku.ambilwarna.AmbilWarnaDialog

class SettingsActivity : AppCompatActivity() {

    var newBackgroundColor: Int? = null
    var newButtonColor: Int? = null
    var newTextColor: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        backgroundColorPicker.setOnClickListener{
            openColorPicker(ColorType.Background)
        }

        buttonsColorPicker.setOnClickListener{
            openColorPicker(ColorType.Buttons)
        }

        textColorPicker.setOnClickListener{
            openColorPicker(ColorType.Text)
        }

        backToMainButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("BackgroundColor", newBackgroundColor)
            intent.putExtra("ButtonColor", newButtonColor)
            intent.putExtra("TextColor", newTextColor)
            startActivity(intent)}
    }


    private fun openColorPicker(type: ColorType) {
        val dialog = object: AmbilWarnaDialog.OnAmbilWarnaListener{

            override fun onCancel(dialog: AmbilWarnaDialog?) { return }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                when(type){
                    ColorType.Buttons -> newButtonColor = color
                    ColorType.Background -> newBackgroundColor = color
                    ColorType.Text -> newTextColor = color
                }
                refreshColors()
            }

            private fun refreshColors() {

                if(newBackgroundColor != null) {
                    backgroundSettingsLayout.setBackgroundColor(newBackgroundColor!!)
                }
                if(newButtonColor != null) {
                    backToMainButton.setBackgroundColor(newButtonColor!!)
                    backgroundColorPicker.setBackgroundColor(newButtonColor!!)
                    textColorPicker.setBackgroundColor(newButtonColor!!)
                    buttonsColorPicker.setBackgroundColor(newButtonColor!!)
                }
                if(newTextColor != null){
                    backToMainButton.setTextColor(newTextColor!!)
                    backgroundColorPicker.setTextColor(newTextColor!!)
                    textColorPicker.setTextColor(newTextColor!!)
                    buttonsColorPicker.setTextColor(newTextColor!!)
                }
            }
        }

        val colorPicker = AmbilWarnaDialog(this, -1, true,  dialog)
        colorPicker.show()
    }
}