package com.example.reversepolishnotationcalculator

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        arithmeticButtonsColorPicker.setOnClickListener{ openColorPicker(ColorType.ButtonsOperations, settings) }
        acButtonColorPicker.setOnClickListener{ openColorPicker(ColorType.ButtonAc, settings) }
        numbersButtonsColorPicker.setOnClickListener{ openColorPicker(ColorType.ButtonsNumbers, settings) }
        textColorPicker.setOnClickListener{ openColorPicker(ColorType.Text, settings) }
        defaultSettingsButton.setOnClickListener{ setDefaultColors(settings); loadColors(settings)}

        accuracySlider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(bar: SeekBar?, value: Int, p2: Boolean) {
                settings.accuracy = value + 1
                val newText = "Accuracy : ${settings.accuracy}"
                accuracyView.text = newText
            }
            override fun onStartTrackingTouch(p0: SeekBar?) { accuracyView.setTextColor(-788529153)}
            override fun onStopTrackingTouch(p0: SeekBar?) { accuracyView.setTextColor(805306367) }
        })

        backToMainButton.setOnClickListener{
            val intent = Intent(this, CalculatorView::class.java)
            saveSettingsInFile(settings)
            startActivity(intent)}
    }

    private fun setDefaultColors(settings: Settings) {
        settings.textColor = Color.WHITE
        settings.backgroundColor = Color.BLACK
        settings.buttonsNumbers = Color.GRAY
        settings.buttonAc = -1124139008
        settings.buttonsOperations = -101596

    }

    private fun readSettingsFromFile(): Settings {
        val path = this.filesDir.toString().plus("/colors.json")
        if(!File(path).exists()){
            File(path).createNewFile()
            return Settings(Color.BLACK, Color.GRAY, -101596, -1124139008, Color.WHITE, 2)
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
                Log.i("color", color.toString())
                when(type){
                    ColorType.Background -> settings.backgroundColor = color
                    ColorType.Text -> settings.textColor = color
                    ColorType.ButtonsNumbers -> settings.buttonsNumbers = color
                    ColorType.ButtonsOperations -> settings.buttonsOperations = color
                    ColorType.ButtonAc -> settings.buttonAc = color
                }
                loadColors(settings)
            }
        }

        val colorPicker = AmbilWarnaDialog(this, -1, true,  dialog)
        colorPicker.show()
    }

    private fun loadColors(settings: Settings) {
        val buttons: List<TextView> = listOf<TextView>(backToMainButton, backgroundColorPicker, textColorPicker, arithmeticButtonsColorPicker,
            defaultSettingsButton, numbersButtonsColorPicker, acButtonColorPicker)
        fun paintButtons(element: TextView) = element.setBackgroundColor(settings.buttonsNumbers)
        fun paintText(element: TextView) = element.setTextColor(settings.textColor)
        buttons.forEach{paintButtons(it); paintText(it)}
        backgroundSettingsLayout.setBackgroundColor(settings.backgroundColor)
        accuracyView.setTextColor(805306367)
    }
}