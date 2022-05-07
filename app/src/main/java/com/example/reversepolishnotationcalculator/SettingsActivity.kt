package com.example.reversepolishnotationcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.android.synthetic.main.activity_settings.*
import yuku.ambilwarna.AmbilWarnaDialog
import java.io.File

class SettingsActivity : AppCompatActivity() {

    private val mapper = jacksonObjectMapper()

    override fun onCreate(savedInstanceState: Bundle?) {
        val settings: Settings = readSettingsFromFile()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setButtonsListeners(settings)
        loadColors(settings)
    }


    private fun setButtonsListeners(settings: Settings) {
        backgroundColorPicker.setOnClickListener{ openColorPicker(ColorType.Background, settings) }
        buttonsColorPicker.setOnClickListener{ openColorPicker(ColorType.Buttons, settings) }
        textColorPicker.setOnClickListener{ openColorPicker(ColorType.Text, settings) }

        accuracySlider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(bar: SeekBar?, value: Int, p2: Boolean) {
                settings.accuracy = value
                val newText = "Accuracy : $value"
                accuracyView.text = newText
            }
            override fun onStartTrackingTouch(p0: SeekBar?) { accuracyView.visibility = TextView.VISIBLE}
            override fun onStopTrackingTouch(p0: SeekBar?) { accuracyView.visibility = TextView.INVISIBLE }
        })

        backToMainButton.setOnClickListener{
            val intent = Intent(this, CalculatorView::class.java)
            saveSettingsInFile(settings)
            startActivity(intent)}
    }

    private fun readSettingsFromFile(): Settings {
        val path = this.filesDir.toString().plus("/colors.json")
        return mapper.readValue(File(path))
    }

    private fun saveSettingsInFile(settings: Settings) {
        val path = filesDir.toString().plus("/colors.json")
        mapper.writeValue(File(path), settings)
    }

    private fun openColorPicker(type: ColorType, settings: Settings) {
        val dialog = object: AmbilWarnaDialog.OnAmbilWarnaListener{

            override fun onCancel(dialog: AmbilWarnaDialog?) { return }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                when(type){
                    ColorType.Buttons -> settings.buttonColor = color
                    ColorType.Background -> settings.backgroundColor = color
                    ColorType.Text -> settings.textColor = color
                }
                loadColors(settings)
            }
        }

        val colorPicker = AmbilWarnaDialog(this, -1, true,  dialog)
        colorPicker.show()
    }

    private fun loadColors(settings: Settings) {
        backgroundSettingsLayout.setBackgroundColor(settings.backgroundColor)
        backToMainButton.setBackgroundColor(settings.buttonColor)
        backgroundColorPicker.setBackgroundColor(settings.buttonColor)
        textColorPicker.setBackgroundColor(settings.buttonColor)
        buttonsColorPicker.setBackgroundColor(settings.buttonColor)
        backToMainButton.setTextColor(settings.textColor)
        backgroundColorPicker.setTextColor(settings.textColor)
        textColorPicker.setTextColor(settings.textColor)
        buttonsColorPicker.setTextColor(settings.textColor)
    }
}