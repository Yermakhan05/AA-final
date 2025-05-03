package com.example.myfaith

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.runner.AndroidJUnit4
import com.example.myfaith.view.activity.RegistrationActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RegistrationActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(RegistrationActivity::class.java)

    @Test
    fun allViewsAreDisplayed() {
        onView(withId(R.id.registration_email)).check(matches(isDisplayed()))
        onView(withId(R.id.registration_number)).check(matches(isDisplayed()))
        onView(withId(R.id.registration_name)).check(matches(isDisplayed()))
        onView(withId(R.id.registration_username)).check(matches(isDisplayed()))
        onView(withId(R.id.registration_password)).check(matches(isDisplayed()))
        onView(withId(R.id.registration_button)).check(matches(isDisplayed()))
        onView(withId(R.id.sign_in)).check(matches(isDisplayed()))
    }
}
