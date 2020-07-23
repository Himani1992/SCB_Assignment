package com.example.assignment.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.json.JSONArray

 class MyRepository {

    val liveData = MutableLiveData<JSONArray>()

    //can i see the what is inside of the jsondata.json?? yes

    fun readJsonFile(context: Application): LiveData<JSONArray> {
        try {
            val jsonfile: String =
                context.applicationContext.assets.open("jsondata.json").bufferedReader()
                    .use {
                        it.readText()
                    } // read asset file jsondata.json
            var jsonArray: JSONArray = JSONArray(jsonfile)
            liveData.value = jsonArray
        } catch (e: Exception) {
        }
        return liveData
    }
}