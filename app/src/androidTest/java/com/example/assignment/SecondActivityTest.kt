package com.example.assignment

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.assignment.view.SecondActivity
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class SecondActivityTest{

    @Test
    fun _isActivityInView(){
        var activity= ActivityScenario.launch(SecondActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.linearLayout2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))         // check layoutparam is visible means layout is visible

    }

    @Test
    fun _validInput(){                      //for valid input
        var activity=ActivityScenario.launch(SecondActivity::class.java)

        // select country
        onView(Matchers.allOf(ViewMatchers.withTagValue(Matchers.`is`("country" as Any)), ViewMatchers.isDisplayed())
        ).perform(click())

        onData(allOf(`is`(instanceOf(String::class.java)))).atPosition(1).perform(click())

        onView(allOf(ViewMatchers.withTagValue(Matchers.`is`("country" as Any)),
                ViewMatchers.isDisplayed()
            )).check(matches(withSpinnerText("India")));

        // select state
        onView(Matchers.allOf(ViewMatchers.withTagValue(Matchers.`is`("state" as Any)), ViewMatchers.isDisplayed())
        ).perform(click())

        onData(allOf(`is`(instanceOf(String::class.java)))).atPosition(1).perform(click())

        onView(allOf(ViewMatchers.withTagValue(Matchers.`is`("state" as Any)),
            ViewMatchers.isDisplayed()
        )).check(matches(withSpinnerText("Maharashtra")));

        // select city
        onView(Matchers.allOf(ViewMatchers.withTagValue(Matchers.`is`("city" as Any)), ViewMatchers.isDisplayed())
        ).perform(click())

        onData(allOf(`is`(instanceOf(String::class.java)))).atPosition(1).perform(click())

        onView(allOf(ViewMatchers.withTagValue(Matchers.`is`("city" as Any)),
            ViewMatchers.isDisplayed()
        )).check(matches(withSpinnerText("Pune")));



        Espresso.onView(                                                 //check first name is blank then show error
            Matchers.allOf(
                ViewMatchers.withTagValue(Matchers.`is`("address" as Any)),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.typeText("Shivajinagar, Pune"), ViewActions.closeSoftKeyboard())

        Espresso.onView(                                                 //check first name is blank then show error
            Matchers.allOf(
                ViewMatchers.withTagValue(Matchers.`is`("pincode" as Any)),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.typeText("411005"), ViewActions.closeSoftKeyboard())


        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withTagValue(Matchers.`is`("next2" as Any)),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.linearLayout3))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun _TestPressBackButton(){
        Espresso.pressBack()
    }

}