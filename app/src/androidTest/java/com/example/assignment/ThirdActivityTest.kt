package com.example.assignment

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.assignment.view.MainActivity
import com.example.assignment.view.SecondActivity
import com.example.assignment.view.ThirdActivity
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ThirdActivityTest{

    @Test
    fun _isActivityInView(){
        var activity= ActivityScenario.launch(ThirdActivity::class.java)

        Espresso.onView(ViewMatchers.withId(R.id.linearLayout3))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))         // check layoutparam is visible means layout is visible

    }

    @Test
    fun _validInput(){                      //for valid input
        var activity=ActivityScenario.launch(ThirdActivity::class.java)

        // select country
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withTagValue(Matchers.`is`("gender" as Any)),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.click())

        Espresso.onData(Matchers.allOf(Matchers.`is`(Matchers.instanceOf(String::class.java)))).atPosition(2).perform(
            ViewActions.click()
        )

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withTagValue(Matchers.`is`("gender" as Any)),
                ViewMatchers.isDisplayed()
            )
        ).check(ViewAssertions.matches(ViewMatchers.withSpinnerText("Female")));



        Espresso.onView(                                                 //check first name is blank then show error
            Matchers.allOf(
                ViewMatchers.withTagValue(Matchers.`is`("companyname" as Any)),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.typeText("abcd pvt ltd"), ViewActions.closeSoftKeyboard())

        Espresso.onView(                                                 //check first name is blank then show error
            Matchers.allOf(
                ViewMatchers.withTagValue(Matchers.`is`("salary" as Any)),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.typeText("30000"), ViewActions.closeSoftKeyboard())


        Espresso.onView(                                                 //check first name is blank then show error
            Matchers.allOf(
                ViewMatchers.withTagValue(Matchers.`is`("Position" as Any)),
                ViewMatchers.isDisplayed()
            )
        ).check(matches(isNotChecked())).perform(click())


        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withTagValue(Matchers.`is`("submit" as Any)),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.click())


    }


    @Test
    fun _TestPressBackButton(){
        pressBack()
    }

    // blank input
    @Test
    fun _invalidInput(){
        var activity=ActivityScenario.launch(ThirdActivity::class.java)

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withTagValue(Matchers.`is`("submit" as Any)),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.click())

        Espresso.onView(                                                 //check first name is blank then show error
            Matchers.allOf(
                ViewMatchers.withTagValue(Matchers.`is`("companyname" as Any)),
                ViewMatchers.isDisplayed()
            )
        ).check(matches(hasErrorText("Cannot be Blank")))


        Espresso.onView(                                                 //check first name is blank then show error
            Matchers.allOf(
                ViewMatchers.withTagValue(Matchers.`is`("salary" as Any)),
                ViewMatchers.isDisplayed()
            )
        ).check(matches(hasErrorText("Cannot be Blank")))





    }



}