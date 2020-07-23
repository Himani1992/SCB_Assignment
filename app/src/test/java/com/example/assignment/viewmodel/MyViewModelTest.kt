package com.example.assignment.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment.repository.MyRepository
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.stubbing.Answer

class MyViewModelTest {
    private lateinit var myRepository: MyRepository

    val liveData = MutableLiveData<JSONArray>()
    @Mock
    private lateinit var application:Application

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setup(){

        MockitoAnnotations.initMocks(this)
        myRepository= MyRepository()

    }

    @Test
    fun getJsondata() {

                var jsonArray:JSONArray= JSONArray()
                jsonArray.put("[{\n" +
                        "\t\"id\": \"firstName\",\n" +
                        "\t\"type\": \"TEXT\",\n" +
                        "\t\"text\": \"First Name\"\n" +
                        "},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"middleName\",\n" +
                        "\t\t\"type\": \"TEXT\",\n" +
                        "\t\t\"text\": \"Middle Name\"\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"lastName\",\n" +
                        "\t\t\"type\": \"TEXT\",\n" +
                        "\t\t\"text\": \"Last Name\"\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"number\",\n" +
                        "\t\t\"type\": \"INTEGER\",\n" +
                        "\t\t\"text\": \"Contact Number\",\n" +
                        "\t\t\"validations\": [{\n" +
                        "\t\t\t\"type\": \"LENGTH\",\n" +
                        "\t\t\t\"max\": 13,\n" +
                        "\t\t\t\"min\": 8\n" +
                        "\t\t},\n" +
                        "\t\t\t{\n" +
                        "\t\t\t\t\"type\": \"REQUIRED\"\n" +
                        "\t\t\t}\n" +
                        "\t\t]\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"altnumber\",\n" +
                        "\t\t\"type\": \"INTEGER\",\n" +
                        "\t\t\"text\": \"Alternate Contact Number\",\n" +
                        "\t\t\"validations\": [{\n" +
                        "\t\t\t\"type\": \"LENGTH\",\n" +
                        "\t\t\t\"max\": 13,\n" +
                        "\t\t\t\"min\": 8\n" +
                        "\t\t},\n" +
                        "\t\t\t{\n" +
                        "\t\t\t\t\"type\": \"REQUIRED\"\n" +
                        "\t\t\t}\n" +
                        "\t\t]\n" +
                        "\t},\n" +
                        "\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"emailId\",\n" +
                        "\t\t\"type\": \"EMAILID\",\n" +
                        "\t\t\"text\": \"Email id\"\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"altemailId\",\n" +
                        "\t\t\"type\": \"EMAILID\",\n" +
                        "\t\t\"text\": \"Alternate Email Id\"\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"next1\",\n" +
                        "\t\t\"type\": \"BUTTON\",\n" +
                        "\t\t\"text\": \"Next\"\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"country\",\n" +
                        "\t\t\"type\": \"DROPDOWN\",\n" +
                        "\t\t\"text\": \"Country\",\n" +
                        "\t\t\"dataSource\": [\"India\", \"USA\", \"UK\"]\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"state\",\n" +
                        "\t\t\"type\": \"DROPDOWN\",\n" +
                        "\t\t\"text\": \"State\",\n" +
                        "\t\t\"dataSource\": [\"Maharashtra\", \"Kernataka\"]\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"city\",\n" +
                        "\t\t\"type\": \"DROPDOWN\",\n" +
                        "\t\t\"text\": \"City\",\n" +
                        "\t\t\"dataSource\": [\"Pune\", \"Mumbai\"]\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"address\",\n" +
                        "\t\t\"type\": \"TEXT\",\n" +
                        "\t\t\"text\": \"Address\"\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"pincode\",\n" +
                        "\t\t\"type\": \"INTEGER\",\n" +
                        "\t\t\"text\": \"Pincode\"\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"next2\",\n" +
                        "\t\t\"type\": \"BUTTON\",\n" +
                        "\t\t\"text\": \"Next\"\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"gender\",\n" +
                        "\t\t\"type\": \"DROPDOWN\",\n" +
                        "\t\t\"text\": \"Gender\",\n" +
                        "\t\t\"dataSource\": [\"Male\", \"Female\"]\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"companyname\",\n" +
                        "\t\t\"type\": \"TEXT\",\n" +
                        "\t\t\"text\": \"Company Name\"\n" +
                        "\t},\n" +
                        "\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"salary\",\n" +
                        "\t\t\"type\": \"INTEGER\",\n" +
                        "\t\t\"text\": \"Salary\"\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"Position\",\n" +
                        "\t\t\"type\": \"CHECKBOX\",\n" +
                        "\t\t\"text\": \"Developer\"\n" +
                        "\t},\n" +
                        "\t{\n" +
                        "\t\t\"id\": \"submit\",\n" +
                        "\t\t\"type\": \"BUTTON\",\n" +
                        "\t\t\"text\": \"Submit\"\n" +
                        "\t}\n" +
                        "]")

                liveData.value=jsonArray
//
                doReturn(liveData)
                    .`when`(myRepository.readJsonFile(application))


//             Mockito.`when`(myRepository.readJsonFile(application)).thenReturn(liveData)
    }
}