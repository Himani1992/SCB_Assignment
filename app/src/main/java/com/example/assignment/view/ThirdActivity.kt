package com.example.assignment.view

import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.R
import com.example.assignment.Stringhelper
import com.example.assignment.Stringhelper.Companion.BUTTON
import com.example.assignment.Stringhelper.Companion.CHECKBOX
import com.example.assignment.Stringhelper.Companion.DROPDOWN
import com.example.assignment.Stringhelper.Companion.EMAILID
import com.example.assignment.Stringhelper.Companion.INTEGER
import com.example.assignment.Stringhelper.Companion.INVALID_TYPE
import com.example.assignment.Stringhelper.Companion.TEXT
import com.example.assignment.Stringhelper.Companion.TYPE
import com.example.assignment.Stringhelper.Companion.dataSource
import com.example.assignment.Stringhelper.Companion.id
import com.example.assignment.Stringhelper.Companion.text
import com.example.assignment.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.activity_third.*
import org.json.JSONArray
import org.json.JSONObject


class ThirdActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    Stringhelper {
    var textMin: Int = 0
    var textMax: Int = 0
    var spinnerSelectedItem: String? = null
    var myLayoutParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT,
        1F
    )
    lateinit var myViewModel: MyViewModel
    var bundlePrev: Bundle = Bundle()

    var layoutParamMargin: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        layoutParamMargin.setMargins(10, 15, 10, 15)


        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        myViewModel.getJsondata().observe(this,             // get json data from viewmodel
            Observer<JSONArray> { jsonarray ->
                var jsonarray: JSONArray = jsonarray
                generateLayout(jsonarray)
            })


        bundlePrev = intent.extras!!
    }


    private fun generateLayout(jsonArray: JSONArray) {
        linearLayout3.removeAllViews()

        for (i in 14..18) {
            var jsonObject: JSONObject = jsonArray.getJSONObject(i)
            when (jsonObject.getString(TYPE)) {
                TEXT, INTEGER, EMAILID ->
                    createTextview(jsonObject)             // Create TEXT
                DROPDOWN ->
                    createDropdown(jsonObject)              // Create DROPDOWN
                CHECKBOX ->
                    createCheckBox(jsonObject)                // Create CHECKBOX
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
        button.layoutParams = layoutParamMargin
        linearLayout3.addView(button)
        button.setOnClickListener {
            //             Next button click
            var companynameEdittext: EditText =
                linearLayout3.findViewWithTag("companyname")
            var salary: EditText = linearLayout3.findViewWithTag("salary")
            var Position: CheckBox = linearLayout3.findViewWithTag("Position")
            var genderSpinner: Spinner = linearLayout3.findViewWithTag("gender")

            if (companynameEdittext.text.isEmpty()) {
                companynameEdittext.setError("Cannot be Blank")
            } else {

            }
            if (salary.text.isEmpty()) {
                salary.setError("Cannot be Blank")
            } else {

            }


            if (spinnerSelectedItem.equals("")) {
                Toast.makeText(this, "Please select Gender", Toast.LENGTH_LONG).show()
            }
            if (!Position.isChecked) {
                Position.setError("Cannot be Blank")
            } else {
                var checked = true
                if (spinnerSelectedItem!!.isNotEmpty() && companynameEdittext.text.isNotEmpty()
                    && salary.text.isNotEmpty()
                ) {

                    // Send all data to service
                    Toast.makeText(this, "Sending data to service", Toast.LENGTH_LONG).show()

                }

            }

        }

    }


    private fun createCheckBox(jsonObject: JSONObject) {
        var linearLayoutText = LinearLayout(this)
        linearLayoutText.orientation = LinearLayout.HORIZONTAL
        val textView = TextView(this)
        textView.text = jsonObject.getString(id)
        textView.layoutParams = myLayoutParam


        var checkBox = CheckBox(this)



        checkBox.tag = jsonObject.getString(id)                 // set tag
        checkBox.layoutParams = myLayoutParam


        checkBox.text = jsonObject.getString(text)             //settext to checkbox
        linearLayoutText.addView(textView)
        linearLayoutText.addView(checkBox)
        linearLayoutText.layoutParams = layoutParamMargin
        linearLayout3.addView(linearLayoutText)                                 //Add checkbox in Layout

    }

    private fun createDropdown(jsonObject: JSONObject) {
        val spinner = Spinner(this)

        var jsonArray: JSONArray = jsonObject.getJSONArray(dataSource)
        val spinnerArray = arrayOfNulls<String>(jsonArray.length() + 1)           // create array
        var s = jsonObject.getString(text)
        spinnerArray.set(0, s)
        for (i in 0..jsonArray.length() - 1) {
            spinnerArray[i + 1] = jsonArray.getString(i)
        }

        val spinnerAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerArray
            )      // Create adapter
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)            // Add spacing

        with(spinner)
        {
            setSelection(0, false)                  // set selection to 0 i.e. city
            gravity = Gravity.CENTER
            adapter = spinnerAdapter


        }
        spinner.tag = jsonObject.getString(id)          //set tag
        spinner.onItemSelectedListener = this
        var linearLayoutText = LinearLayout(this)
        linearLayoutText.orientation = LinearLayout.HORIZONTAL
        val textView = TextView(this)
        textView.text = jsonObject.getString(text)
        textView.layoutParams = myLayoutParam
        spinner.layoutParams = myLayoutParam
        spinner.onItemSelectedListener = this

        linearLayoutText.addView(textView)
        linearLayoutText.addView(spinner)
        linearLayout3.addView(linearLayoutText)               //add spinner to layout

    }

    private fun createTextview(jsonObject: JSONObject) {
        var linearLayoutText = LinearLayout(this)
        linearLayoutText.orientation = LinearLayout.HORIZONTAL
        val textView = TextView(this)

        var edittext = EditText(this)

        if (jsonObject.getString(TYPE) == INTEGER) {

            edittext.inputType = InputType.TYPE_CLASS_NUMBER
        }


        textView.text = jsonObject.getString(text)

        textView.layoutParams = myLayoutParam
        edittext.tag = jsonObject.getString(id)              //set tag
        edittext.layoutParams = myLayoutParam
        linearLayoutText.addView(textView)              // Add textview to layout
        linearLayoutText.addView(edittext)

        linearLayoutText.layoutParams = layoutParamMargin
        linearLayout3.addView(linearLayoutText)              // Add textview to layout

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
