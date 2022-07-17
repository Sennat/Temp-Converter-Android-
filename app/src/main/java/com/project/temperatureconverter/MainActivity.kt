package com.project.temperatureconverter

import android.graphics.Color
import android.icu.text.DecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.project.temperatureconverter.databinding.ActivityMainBinding

private const val DEFAULT_FAHRENHEIT: Double = 70.0
private const val DEFAULT_CELCIUS: Double = 20.0

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var operation: String = "Celsius"
    private lateinit var operationTemp: String

    // Format decimal places
    private var df = DecimalFormat("#.#")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        getDefaultTemperatureCalculation().apply {
            binding.txtResult.text = "${df.format(getDefaultTemperatureCalculation())} °C"
        }

        // A function call for operation switch
        toggleOperationType()

        // A function call for conversion operation
        proceedOperation(df)

        setContentView(binding.root)
    }

    /**
     * A function call for conversion operation
     */
    private fun proceedOperation(df: DecimalFormat) {
        binding.txtInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                try {
                    when (operation) {
                        "Celsius" -> convertToCelsius().apply {
                            binding.txtResult.text = "${df.format(convertToCelsius())} °C"
                            binding.txtInfo.text = "Temperature Of ${operationTemp}  °C"
                        }
                        "Fahrenheit" -> convertToFahrenheit().apply {
                            binding.txtResult.text = "${df.format(convertToFahrenheit())} °F"
                            binding.txtInfo.text = "Temperature Of ${operationTemp}  °F"
                        }
                    }
                } catch (E: Exception) {
                    getDefaultTemperatureCalculation().apply {
                        binding.txtResult.text = "${df.format(getDefaultTemperatureCalculation())} °C"
                    }
                }
            }
        })
    }

    /**
     * A function to switch conversion operation
     */
    private fun toggleOperationType() {
        binding.btnSwitch.setOnCheckedChangeListener { _, isChecked ->
            //binding.txtInfo.visibility = View.VISIBLE
            if (isChecked) {
                binding.btnSwitch.getThumbDrawable().setTint(Color.argb(255, 253, 153, 0))
                binding.txtType.apply {
                    binding.txtType.text = "To Fahrenheit"
                    operation = "Celsius"
                    binding.txtResult.text = "${df.format(getToggleOperationValue(operation))} °F"
                    binding.txtInfo.text = "Temperature Of ${operationTemp}  °C"
                }
                //toggleOperation("Celsius")
            } else {
                binding.txtType.apply {
                    binding.txtType.text = "To Celsius"
                    operation = "Fahrenheit"
                    binding.txtResult.text = "${df.format(getToggleOperationValue(operation))} °C"
                    binding.txtInfo.text = "Temperature Of ${operationTemp}  °F"
                }
            }
        }
    }

    /**
     * A function to convert to Celsius
     */
    private fun convertToCelsius(): Double {
        operationTemp = binding.txtInput.text.toString()
        val value: Double = if (operationTemp != "") {
            (operationTemp.toDouble() - 32) * 5 / 9
        } else {
            (DEFAULT_FAHRENHEIT - 32) * 5 / 9
        }
        return value
    }

    /**
     * A function to convert to Fahrenheit
     */
    private fun convertToFahrenheit(): Double {
        operationTemp = binding.txtInput.text.toString()
        val value: Double = if (operationTemp != "") {
            (operationTemp.toDouble() * 9 / 5) + 32
        } else {
            (DEFAULT_CELCIUS * 9 / 5) + 32
        }
        return value
    }

    /**
     * A function get default temperature calculation
     */
    private fun getDefaultTemperatureCalculation(): Double {
        //binding.txtInfo.visibility = View.VISIBLE
        operation.apply {
            operation = "Celsius"
            binding.txtInfo.text = "Default Temp. Of ${DEFAULT_FAHRENHEIT} °F"
        }
        return (DEFAULT_FAHRENHEIT - 32) * 5 / 9
    }

    /**
     *  A function for a toggle operation value
     */
    private fun getToggleOperationValue(str: String): Double {
        operationTemp = binding.txtInput.text.toString()
        var value = 0.0

        if (operationTemp != "") {
            when(str) {
                "Fahrenheit" -> value = (operationTemp.toDouble() - 32) * 5 / 9
                "Celsius" -> value = (operationTemp.toDouble() * 9 / 5) + 32
            }
        } else {
            when(str) {
                "Fahrenheit" -> value = (DEFAULT_FAHRENHEIT - 32) * 5 / 9
                "Celsius" -> value = (DEFAULT_CELCIUS * 9 / 5) + 32
            }
        }

        return value
    }

}
