package com.example.reversepolishnotationcalculator

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.android.synthetic.main.settings_activity.*
import yuku.ambilwarna.AmbilWarnaDialog
import java.io.File

class SettingsActivity : AppCompatActivity() {

    private val mapper = jacksonObjectMapper()

    override fun onCreate(savedInstanceState: Bundle?) {
        val settings: Settings = readSettingsFromFile()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        setButtonsListeners(settings)
        loadColors(settings)
    }


    private fun setButtonsListeners(settings: Settings) {
        backgroundColorPicker.setOnClickListener{ openColorPicker(ColorType.Background, settings) }
        buttonsColorPicker.setOnClickListener{ openColorPicker(ColorType.Buttons, settings) }
        textColorPicker.setOnClickListener{ openColorPicker(ColorType.Text, settings) }
        defaultSettingsButton.setOnClickListener{ setDefaultColors(settings); loadColors(settings)}

        accuracySlider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(bar: SeekBar?, value: Int, p2: Boolean) {
                settings.accuracy = value + 1
                val newText = "Accuracy : ${settings.accuracy}"
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

    private fun setDefaultColors(settings: Settings) {
        settings.textColor = Color.WHITE
        settings.backgroundColor = Color.BLACK
        settings.buttonColor = Color.BLUE
    }

    private fun readSettingsFromFile(): Settings {
        val path = this.filesDir.toString().plus("/colors.json")
        if(!File(path).exists()){
            File(path).createNewFile()
            return Settings(Color.BLACK, Color.BLUE, Color.WHITE, 2)
        }
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
        val buttons: List<TextView> = listOf<TextView>(backToMainButton, backgroundColorPicker, textColorPicker, buttonsColorPicker, defaultSettingsButton)
        fun paintButtons(element: TextView) = element.setBackgroundColor(settings.buttonColor)
        fun paintText(element: TextView) = element.setTextColor(settings.textColor)
        buttons.forEach{paintButtons(it); paintText(it)}
        backgroundSettingsLayout.setBackgroundColor(settings.backgroundColor)
        accuracyView.setTextColor(settings.textColor)
    }
}