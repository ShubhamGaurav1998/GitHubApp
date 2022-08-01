package com.example.githubapp.views

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.githubapp.R
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_is_recyclerView_visible_onAppLaunch() {
        onView(withId(R.id.swipeRefreshLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.prRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_back_feature(){
        onView(withId(R.id.prRecyclerView)).check(matches(isDisplayed()))
        pressBack()
        onView(withText(R.string.quit_confirmation_message)).check(matches(isDisplayed()))
        onView(withText(R.string.no)).perform(click())
        onView(withId(R.id.swipeRefreshLayout)).check(matches(isDisplayed()))
    }


}