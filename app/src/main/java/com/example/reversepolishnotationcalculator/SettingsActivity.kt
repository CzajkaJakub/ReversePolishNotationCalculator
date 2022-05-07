package com.example.reversepolishnotationcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.android.synthetic.main.activity_settings.*
import yuku.ambilwarna.AmbilWarnaDialog
import java.io.File

class SettingsActivity : AppCompatActivity() {

    private val mapper = jacksonObjectMapper()
    private var colors: Colors? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        colors = readColorsFromFile()
        setContentView(R.layout.activity_settings)
        setButtonsListeners()
        readColorsFromFile()
    }

    private fun setButtonsListeners() {
        backgroundColorPicker.setOnClickListener{ openColorPicker(ColorType.Background) }
        buttonsColorPicker.setOnClickListener{ openColorPicker(ColorType.Buttons) }
        textColorPicker.setOnClickListener{ openColorPicker(ColorType.Text) }
        backToMainButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            saveColorsInFile()
            startActivity(intent)}
    }

    private fun readColorsFromFile(): Colors {
        val path = this.filesDir.toString().plus("/colors.json")
        return mapper.readValue(File(path))
    }

    private fun saveColorsInFile(){
        val path = filesDir.toString().plus("/colors.json")
        mapper.writeValue(File(path), colors)
    }

    private fun openColorPicker(type: ColorType) {
        val dialog = object: AmbilWarnaDialog.OnAmbilWarnaListener{

            override fun onCancel(dialog: AmbilWarnaDialog?) { return }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                when(type){
                    ColorType.Buttons -> colors!!.buttonColor = color
                    ColorType.Background -> colors!!.backgroundColor = color
                    ColorType.Text -> colors!!.textColor = color
                }
                loadColors()
            }
        }

        val colorPicker = AmbilWarnaDialog(this, -1, true,  dialog)
        colorPicker.show()
    }

    private fun loadColors() {
        backgroundSettingsLayout.setBackgroundColor(colors!!.backgroundColor)
        backToMainButton.setBackgroundColor(colors!!.buttonColor)
        backgroundColorPicker.setBackgroundColor(colors!!.buttonColor)
        textColorPicker.setBackgroundColor(colors!!.buttonColor)
        buttonsColorPicker.setBackgroundColor(colors!!.buttonColor)
        backToMainButton.setTextColor(colors!!.textColor)
        backgroundColorPicker.setTextColor(colors!!.textColor)
        textColorPicker.setTextColor(colors!!.textColor)
        buttonsColorPicker.setTextColor(colors!!.textColor)
    }
}