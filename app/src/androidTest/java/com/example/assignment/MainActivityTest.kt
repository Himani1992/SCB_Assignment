package com.example.assignment

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.TypeTextAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.assignment.view.MainActivity
import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{




    @Test
    fun _isActivityInView(){
    var activity=ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.linearLayout)).check(matches(isDisplayed()))         // check layoutparam is visible means layout is visible

    }

    @Test
    fun _validInput(){                      //for valid input
        var activity=ActivityScenario.launch(MainActivity::class.java)

        onView(                                                 //check first name is blank then show error
            allOf(
                withTagValue(`is`("firstName" as Any)),
                isDisplayed()
            )
        ).perform(typeText("abc"), ViewActions.closeSoftKeyboard())

        onView(                                                 //check first name is blank then show error
            allOf(
                withTagValue(`is`("middleName" as Any)),
                isDisplayed()
            )
        ).perform(typeText("abc"), ViewActions.closeSoftKeyboard())

        onView(                                                 //check first name is blank then show error
            allOf(
                withTagValue(`is`("lastName" as Any)),
                isDisplayed()
            )
        ).perform(typeText("abc"), ViewActions.closeSoftKeyboard())

        onView(                                                 //check first name is blank then show error
            allOf(
                withTagValue(`is`("number" as Any)),
                isDisplayed()
            )
        ).perform(typeText("8974775789"), ViewActions.closeSoftKeyboard())

        onView(                                                 //check first name is blank then show error
            allOf(
                withTagValue(`is`("altnumber" as Any)),
                isDisplayed()
            )
        ).perform(typeText("8974775789"), ViewActions.closeSoftKeyboard())

        onView(                                                 //check first name is blank then show error
            allOf(
                withTagValue(`is`("emailId" as Any)),
                isDisplayed()
            )
        ).perform(typeText("abc@gmail.com"), ViewActions.closeSoftKeyboard())

        onView(                                                 //check first name is blank then show error
            allOf(
                withTagValue(`is`("altemailId" as Any)),
                isDisplayed()
            )
        ).perform(typeText("abc1@gmail.com"), ViewActions.closeSoftKeyboard())


        onView(
            allOf(
                withTagValue(`is`("next1" as Any)),
                isDisplayed()
            )
        ).perform(click())

        onView(withId(R.id.linearLayout2)).check(matches(isDisplayed()))
    }



    @Test
    fun _isFirstNameInView(){
        var activity=ActivityScenario.launch(MainActivity::class.java)


        onView(
            allOf(
                withTagValue(`is`("next1" as Any)),
                isDisplayed()
            )
        ).perform(click())

        onView(                                                 //check first name is blank then show error
            allOf(
                withTagValue(`is`("firstName" as Any)),
                isDisplayed()
            )
        ).check(matches(hasErrorText("Cannot be Blank")))

    }


    // for Invalid Input

    @Test
    fun _InvalidFirstName(){
        var activity=ActivityScenario.launch(MainActivity::class.java)

        onView(                                                 //check first name is blank then show error
            allOf(
                withTagValue(`is`("firstName" as Any)),
                isDisplayed()
            )
        ).perform(typeText("abc35"))

        closeSoftKeyboard()

        onView(
            allOf(
                withTagValue(`is`("next1" as Any)),
                isDisplayed()
            )
        ).perform(click())


        onView(                                                 //check first name is blank then show error
            allOf(
                withTagValue(`is`("firstName" as Any)),
                isDisplayed()
            )
        ).check(matches(withContentDescription(containsString("35"))))
            .check(matches(hasErrorText("Cannot not contain numbers")))
    }


    @Test
    fun _TestPressBackButton(){
        Espresso.pressBack()
    }

}