package com.example.assignment.viewmodel

import android.app.Application
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.repository.MyRepository
import org.json.JSONArray

open class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val myRepository:MyRepository
//    private val livedata:LiveData<JSONArray>
    init {
        myRepository= MyRepository()
//        livedata=    myRepository.readJsonFile(application)
    }

    fun getJsondata(): LiveData<JSONArray> = myRepository.readJsonFile(getApplication())


//    fun checkFirstNameIsEmpty(firstNameEdittext: EditText) {
//        if(firstNameEdittext.text.isEmpty())
}