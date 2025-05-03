package com.example.myfaith

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import com.example.myfaith.view.fragment.ProfileFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileFragmentTest {

    @Test
    fun testProfileFragmentUIElementsVisible() {
        launchFragmentInContainer<ProfileFragment>()

        onView(withId(R.id.profile_name)).check(matches(isDisplayed()))
        onView(withId(R.id.profile_number)).check(matches(isDisplayed()))
        onView(withId(R.id.profile_photo)).check(matches(isDisplayed()))
        onView(withId(R.id.edit_profile_button)).check(matches(isDisplayed()))
    }
}
