package com.example.assignment.view

import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_second.*
import org.json.JSONArray
import org.json.JSONObject


class SecondActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    Stringhelper {
    var textMin: Int = 0
    var textMax: Int = 0
    var jsonarray: JSONArray? = null
    var spinnerSelectedItem: String? = null
    lateinit var myViewModel: MyViewModel
    var countrytext: String = ""
    var stateText = ""
    var cityText = ""

     var bundlePrev: Bundle= Bundle()

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
        setContentView(R.layout.activity_second)
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
        linearLayout2.removeAllViews()

        jsonarray = jsonArray

        for (i in 8..13) {
            var jsonObject: JSONObject =
                jsonArray.getJSONObject(i)     // craete different view from input
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
        button.setTag(jsonObject.getString(id))
        button.text = jsonObject.getString(text)
        button.layoutParams = layoutParamMargin               // add margin to button
        linearLayout2.addView(button)
        button.setOnClickListener {
            // Next button click
            var addressEdittext: EditText = linearLayout2.findViewWithTag<EditText>("address")
            var pincodeEdittext: EditText = linearLayout2.findViewWithTag<EditText>("pincode")
            var countrySpinner: Spinner = linearLayout2.findViewWithTag<Spinner>("country")
            var stateSpinner: Spinner = linearLayout2.findViewWithTag<Spinner>("state")
            var citySpinner: Spinner = linearLayout2.findViewWithTag<Spinner>("city")

            if (addressEdittext.text.isEmpty()) {
                addressEdittext.setError("Cannot be blank")
            }
            if (pincodeEdittext.text.isEmpty()) {
                pincodeEdittext.setError("Cannot be blank")
            }

            if (pincodeEdittext.text.toString().length > 8) {
                pincodeEdittext.setError("Please enter currect pincode")
            }

            if (countrytext.equals("")) {
                Toast.makeText(this, "Please select Country", Toast.LENGTH_LONG).show()
            }
            if (stateText.equals("")) {
                Toast.makeText(this, "Please select State", Toast.LENGTH_LONG).show()
            }
            if (cityText.equals("")) {
                Toast.makeText(this, "Please select City", Toast.LENGTH_LONG).show()
            }

            if (countrytext.length != 0 && stateText.length != 0 && cityText.length != 0
                && addressEdittext.text.toString().length != 0 && pincodeEdittext.text.toString().length != 0
            ) {


                var intent =
                    Intent(this, ThirdActivity::class.java)      // pass data to third activity
                var bundle=Bundle()
                bundle=bundlePrev

                bundle.putString("address",addressEdittext.text.toString())
                bundle.putString("pincode",pincodeEdittext.text.toString())
                bundle.putString("country",countrytext)
                bundle.putString("state",stateText)
                bundle.putString("city",cityText)
                intent.putExtras(bundle)
                startActivity(intent)

            }
        }

    }


    private fun createCheckBox(jsonObject: JSONObject) {
        var checkBox = CheckBox(this)
        checkBox.tag = jsonObject.getString(id)                 // set tag

        checkBox.text = jsonObject.getString(text)             //settext to checkbox
        linearLayout2.addView(checkBox)                                 //Add checkbox in Layout

    }

    private fun createDropdown(jsonObject: JSONObject) {
        val spinner = Spinner(this)

        var jsonArray: JSONArray = jsonObject.getJSONArray(dataSource);
        val spinnerArray = arrayOfNulls<String>(jsonArray.length() + 1)           // create array
        var s = jsonObject.getString(text)
        spinnerArray.set(0, s)
        for (i in 0..jsonArray.length() - 1) {
            spinnerArray[i + 1] = jsonArray.getString(i);
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

//        button.layoutParams=layoutParamMargin

        var textview = TextView(this)
        textview.text = jsonObject.getString(text)
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        textview.layoutParams = myLayoutParam
        spinner.layoutParams = myLayoutParam

        linearLayout.addView(textview)                      //add textview to layout
        linearLayout.addView(spinner)
        linearLayout.layoutParams = layoutParamMargin
        linearLayout2.addView(linearLayout)               //add spinner to layout

    }

    private fun createTextview(jsonObject: JSONObject): Unit {
        var linearLayoutText = LinearLayout(this);
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

        linearLayout2.addView(linearLayoutText)              // Add textview to layout

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position != 0) {
            spinnerSelectedItem =
                parent?.getItemAtPosition(position).toString()
            when (parent?.getItemAtPosition(0)) {
                "Country" ->
                    countrytext = parent?.getItemAtPosition(position).toString()
                "State" ->
                    stateText = parent?.getItemAtPosition(position).toString()
                "City" ->
                    cityText = parent?.getItemAtPosition(position).toString()

            }

            // get selected item
            linearLayout2.findViewWithTag<Spinner>("Country")
        }
    }


}
