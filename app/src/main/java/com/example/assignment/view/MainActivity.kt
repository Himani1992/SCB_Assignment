package com.example.assignment.view

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.R
import com.example.assignment.Stringhelper
import com.example.assignment.Stringhelper.Companion.BUTTON
import com.example.assignment.Stringhelper.Companion.EMAILID
import com.example.assignment.Stringhelper.Companion.INTEGER
import com.example.assignment.Stringhelper.Companion.INVALID_TYPE
import com.example.assignment.Stringhelper.Companion.TEXT
import com.example.assignment.Stringhelper.Companion.TYPE
import com.example.assignment.Stringhelper.Companion.altemailId
import com.example.assignment.Stringhelper.Companion.altnumber
import com.example.assignment.Stringhelper.Companion.emailId
import com.example.assignment.Stringhelper.Companion.firstName
import com.example.assignment.Stringhelper.Companion.id
import com.example.assignment.Stringhelper.Companion.lastName
import com.example.assignment.Stringhelper.Companion.max
import com.example.assignment.Stringhelper.Companion.middleName
import com.example.assignment.Stringhelper.Companion.min
import com.example.assignment.Stringhelper.Companion.number
import com.example.assignment.Stringhelper.Companion.text
import com.example.assignment.Stringhelper.Companion.validations
import com.example.assignment.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    Stringhelper {
    var regexString: String = "\\d+"

    var textMin: Int = 0
    lateinit var myViewModel: MyViewModel
    var textMax: Int = 0
    var spinnerSelectedItem: String? = null
    var jsonArray: JSONArray? = null

    var layoutParamMargin: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    var myLayoutParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,
        1F
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutParamMargin.setMargins(10, 15, 10, 15)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        myViewModel.getJsondata().observe(this,             // get json data from viewmodel
            Observer<JSONArray> { jsonarray ->
                Log.i("JsonArray", jsonarray.toString())
                jsonArray = jsonarray
                generateLayout(jsonarray)
            })
    }


    private fun generateLayout(jsonArray: JSONArray) {
        linearLayout.removeAllViews()
        for (i in 0..7) {
            var jsonObject: JSONObject = jsonArray.getJSONObject(i)
            when (jsonObject.getString(TYPE)) {             // check input type
                TEXT, INTEGER, EMAILID ->
                    createTextview(jsonObject)             // Create TEXT
                BUTTON ->
                    createButton(jsonObject)              // Create BUTTON

                else -> INVALID_TYPE

            }
        }

    }

    private fun createButton(jsonObject: JSONObject) {
        var button = Button(this)
        button.text = jsonObject.getString(text)
        button.setTag(jsonObject.getString(id))
        button.layoutParams = layoutParamMargin           // set margin to button
        linearLayout.addView(button)

        button.setOnClickListener {

            // Next button click
            var firstNameEdittext: EditText = linearLayout.findViewWithTag<EditText>(firstName)
//            myViewModel.checkFirstNameIsEmpty(firstNameEdittext)

            var middleNameEdittext: EditText = linearLayout.findViewWithTag<EditText>(middleName)
            var lastNameEdittext: EditText = linearLayout.findViewWithTag<EditText>(lastName)
            var numberEdittext: TextView = linearLayout.findViewWithTag<EditText>(number)
            var altnumberEdittext: TextView = linearLayout.findViewWithTag<EditText>(altnumber)
            var emailIdrEdittext: TextView = linearLayout.findViewWithTag<EditText>(emailId)
            var altemailIdEditText: TextView = linearLayout.findViewWithTag<EditText>(altemailId)

            if (!firstNameEdittext.text.toString().matches(Regex("\\d+(?:\\.\\d+)?"))) {
                firstNameEdittext.setError("First name can not contain number")
            }

            if (!middleNameEdittext.text.toString().matches(Regex("\\d+(?:\\.\\d+)?"))) {
                middleNameEdittext.setError("Middle name can not contain number")
            }

            if (!lastNameEdittext.text.toString().matches(Regex("\\d+(?:\\.\\d+)?"))) {
                lastNameEdittext.setError("Last name can not contain number")
            }

            if (firstNameEdittext.text.isEmpty()) {
                firstNameEdittext.setError("Cannot be Blank")

            }
            if (middleNameEdittext.text.isEmpty()) {
                middleNameEdittext.setError("Cannot be Blank")
            }

            if (lastNameEdittext.text.isEmpty()) {
                lastNameEdittext.setError("Cannot be Blank")
            }
            if (numberEdittext.text.isEmpty()) {
                numberEdittext.setError("Cannot be Blank")
            } else if (numberEdittext.text.toString().length < 8 || numberEdittext.text.toString().length > 13) {
                numberEdittext.setError("Please enter valid number")

            }
            if (altnumberEdittext.text.isEmpty()) {
                altnumberEdittext.setError("Cannot be Blank")
            } else if (altnumberEdittext.text.toString().length < 8 || altnumberEdittext.text.toString().length > 13) {
                altnumberEdittext.setError("Please enter valid number")

            }
            if (emailIdrEdittext.text.isEmpty()) {
                emailIdrEdittext.setError("Cannot be Blank")
            } else if (!(Patterns.EMAIL_ADDRESS.matcher(emailIdrEdittext.text).matches())) {
                emailIdrEdittext.setError("Please enter valid email id")
            }
            if (altemailIdEditText.text.isEmpty()) {
                altemailIdEditText.setError("Cannot be Blank")
            } else if (!(Patterns.EMAIL_ADDRESS.matcher(altemailIdEditText.text).matches())) {
                altemailIdEditText.setError("Please enter valid email id")
            }

            if (firstNameEdittext.text.toString().length != 0 && middleNameEdittext.text.toString().length != 0
                && lastNameEdittext.text.toString().length != 0 && numberEdittext.text.toString().length != 0
                && altnumberEdittext.text.toString().length != 0 && emailIdrEdittext.text.toString().length != 0
                && altemailIdEditText.text.toString().length != 0 && Patterns.EMAIL_ADDRESS.matcher(
                    emailIdrEdittext.text
                ).matches()
                && Patterns.EMAIL_ADDRESS.matcher(altemailIdEditText.text).matches()

            ) {
                var intent = Intent(this, SecondActivity::class.java)
                var bundle = Bundle();
                bundle.putString(firstName, firstNameEdittext.text.toString())
                bundle.putString(middleName, middleNameEdittext.text.toString())
                bundle.putString(lastName, lastNameEdittext.text.toString())
                bundle.putString(number, numberEdittext.text.toString())
                bundle.putString(altnumber, altnumberEdittext.text.toString())
                bundle.putString(emailId, emailIdrEdittext.text.toString())
                bundle.putString(altemailId, altemailIdEditText.text.toString())
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid data", Toast.LENGTH_LONG).show()
            }

        }

    }


    fun isNumeric(str: String): Boolean {
        return str.matches(Regex("-?\\d+(\\.\\d+)?"))
    }

    private fun createTextview(jsonObject: JSONObject): Unit {


        var linearLayoutText =
            LinearLayout(this);                  // create horizontal linear layout
        linearLayoutText.orientation = LinearLayout.HORIZONTAL
        val textView = TextView(this)

        var edittext = EditText(this)

        if (jsonObject.getString(TYPE) == INTEGER) {                // check data
            var id: String = jsonObject.getString(id);
            if (id.contains("number")) {
                var validations: JSONArray = jsonObject.getJSONArray(validations)
                var jsonObject: JSONObject = validations.getJSONObject(0)
                textMin = jsonObject.getInt(min)
                textMax = jsonObject.getInt(max)
            }
            edittext.inputType = InputType.TYPE_CLASS_NUMBER
        } else if (jsonObject.getString(TYPE) == EMAILID) {
            edittext.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        }


        textView.text = jsonObject.getString(text)

        textView.layoutParams = myLayoutParam
        edittext.tag = jsonObject.getString(id)              //set tag
        edittext.layoutParams = myLayoutParam

        linearLayoutText.addView(textView)              // Add textview to layout
        linearLayoutText.addView(edittext)               // add Edittext to linearlayout
        linearLayoutText.layoutParams = layoutParamMargin
        linearLayout?.addView(linearLayoutText)              // Add textview to layout

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position != 0) {
            spinnerSelectedItem =
                parent?.getItemAtPosition(position).toString()            // get selected item
        }
    }


}


