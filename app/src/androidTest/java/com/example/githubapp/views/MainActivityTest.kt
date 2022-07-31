package com.example.githubapp.views

import android.content.Context
import android.view.View
import androidx.core.util.ObjectsCompat.requireNonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.core.internal.deps.guava.base.Objects
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.githubapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Objects.requireNonNull

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

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

//    @Test
//    @Throws(InterruptedException::class)
//    fun testCaseForRecyclerScroll() {
//        Thread.sleep(1000)
//        val recyclerView =
//            activityRule.activity.findViewById<RecyclerView>(R.id.prRecyclerView)
//        val itemCount =
//            Objects.requireNonNull(recyclerView.adapter!!).itemCount
//        onView(ViewMatchers.withId(R.id.prRecyclerView))
//            .inRoot(
//                RootMatchers.withDecorView(
//                    Matchers.`is`(
//                        activityRule.activity.window.decorView
//                    )
//                )
//            )
//            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
//    }

}