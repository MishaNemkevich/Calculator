package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {
    private lateinit var mathOperation: TextView
    private lateinit var resultText: TextView
    private var flag = true
    private var count = 0
    private val OPERATION_KEY = "mathOperation"
    private val RESULT_KEY = "resultText"
    private val FLAG_KEY = "flag"
    private val COUNT_KEY = "count"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mathOperation = findViewById(R.id.math_operation)
        resultText = findViewById(R.id.result_text)
        if (savedInstanceState != null) {
            mathOperation.text = savedInstanceState.getString(OPERATION_KEY)
            resultText.text = savedInstanceState.getString(RESULT_KEY)
            flag = savedInstanceState.getBoolean(FLAG_KEY)
            count = savedInstanceState.getInt(COUNT_KEY)
        }
        fun skobka(): String {
            val str = mathOperation.text.toString()
            if (str != "") {
                val char = str.last().toString()
                if (flag || char == "+" || char == "-" || char == "/" || char == "*" || char == "(") {
                    count++
                    flag = false
                    return "("
                } else if (count >= 1) {
                    if (count == 1) {
                        count = 0
                        flag = true
                        return ")"
                    }
                    count--
                    return ")"
                }
            }
            count++
            flag = false
            return "("
        }

        fun validText(operation: String): Boolean {
            val str = mathOperation.text.toString()
            if (str != "") {
                val char = str.last().toString()
                if (char != operation) {
                    return true
                }
            }
            return false
        }

        fun setTextFields(str: String) {
            if (resultText.text.any { it.isLetter() || it == '!' }){
                resultText.text = ""
            }
            else if (resultText.text != "" ) {
                mathOperation.text = resultText.text
                resultText.text = ""
            }
            mathOperation.append(str)
        }

        fun deleteText() {
            val str = mathOperation.text.toString()
            if (str != "") {
                if (str.last().toString() == "(") {
                    count--
                    if(count==0){
                        flag = true
                    }
                }
                else if (str.last().toString() == ")"){
                    count++
                    flag = false
                }
                if (str.isNotEmpty()) mathOperation.text = str.substring(0, str.length - 1)
                resultText.text = ""
            }
        }

        fun equalsBut() {
            try {
                val ex = ExpressionBuilder(mathOperation.text.toString()).build()
                val result = ex.evaluate()
                val longRes = result.toLong()
                if (result == longRes.toDouble()) {
                    resultText.text = longRes.toString()
                } else {
                    resultText.text = result.toString()
                }
            } catch (e: Exception) {
                Log.d("Exception", "сообщение: ${e.message}")
                resultText.text = e.message
            }
        }

        val zeroButton = findViewById<TextView>(R.id.b0_text)
        zeroButton.setOnClickListener {
            setTextFields("0")
        }
        val oneButton = findViewById<TextView>(R.id.b1_text)
        oneButton.setOnClickListener {
            setTextFields("1")
        }
        val twoButton = findViewById<TextView>(R.id.b2_text)
        twoButton.setOnClickListener {
            setTextFields("2")
        }
        val threeButton = findViewById<TextView>(R.id.b3_text)
        threeButton.setOnClickListener {
            setTextFields("3")
        }
        val fourButton = findViewById<TextView>(R.id.b4_text)
        fourButton.setOnClickListener {
            setTextFields("4")
        }
        val fiveButton = findViewById<TextView>(R.id.b5_text)
        fiveButton.setOnClickListener {
            setTextFields("5")
        }
        val sixButton = findViewById<TextView>(R.id.b6_text)
        sixButton.setOnClickListener {
            setTextFields("6")
        }
        val sevenButton = findViewById<TextView>(R.id.b7_text)
        sevenButton.setOnClickListener {
            setTextFields("7")
        }
        val eightButton = findViewById<TextView>(R.id.b8_text)
        eightButton.setOnClickListener {
            setTextFields("8")
        }
        val nineButton = findViewById<TextView>(R.id.b9_text)
        nineButton.setOnClickListener {
            setTextFields("9")
        }
        val minusButton = findViewById<TextView>(R.id.minus_text)
        minusButton.setOnClickListener {
            if (validText("-")) {
                setTextFields("-")
            }
        }
        val plusButton = findViewById<TextView>(R.id.plus_text)
        plusButton.setOnClickListener {
            if (validText("+")) {
                setTextFields("+")
            }
        }
        val delenieButton = findViewById<TextView>(R.id.delenie_text)
        delenieButton.setOnClickListener {
            if (validText("/")) {
                setTextFields("/")
            }
        }
        val unojenieButton = findViewById<TextView>(R.id.umnojenie_text)
        unojenieButton.setOnClickListener {
            if (validText("*")) {
                setTextFields("*")
            }
        }
        val equalsButton = findViewById<TextView>(R.id.equals_text)
        equalsButton.setOnClickListener {
            equalsBut()
        }
        val skobkaButton = findViewById<TextView>(R.id.skob_text)
        skobkaButton.setOnClickListener {
            setTextFields(skobka())
        }
        val twoZeroButton = findViewById<TextView>(R.id.twoZero_text)
        twoZeroButton.setOnClickListener {
            setTextFields("00")
        }
        val pointButton = findViewById<TextView>(R.id.point_text)
        pointButton.setOnClickListener {
            if (validText(".")) {
                setTextFields(".")
            }
        }
        val clearButton = findViewById<TextView>(R.id.ac_text)
        clearButton.setOnClickListener {
            flag = true
            count = 0
            mathOperation.text = ""
            resultText.text = ""
        }
        val backButton = findViewById<TextView>(R.id.back_text)
        backButton.setOnClickListener {
            deleteText()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putString(OPERATION_KEY, mathOperation.text.toString())
        if (resultText.text != "") savedInstanceState.putString(
            RESULT_KEY,
            resultText.text.toString()
        )
        savedInstanceState.putBoolean(FLAG_KEY, flag)
        savedInstanceState.putInt(COUNT_KEY, count)
        super.onSaveInstanceState(savedInstanceState)
    }

}