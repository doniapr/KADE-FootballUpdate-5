package com.doniapr.footballupdate.view.ui

import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.doniapr.footballupdate.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchTest {
    private val threadSleep: Long = 2000

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testSearch() {

        // press search menu
        onView(withId(R.id.search)).perform(click())
        // typing query
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("arsenal"))
            .perform(pressImeActionButton())
        // waiting for result
        Thread.sleep(threadSleep)
        // press first item on recycler view
        onView(withId(R.id.rv_search_result)).check(matches(isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        // waiting for detail result
        Thread.sleep(threadSleep)
        // press back button
        onView(withText("Stats")).check(matches(isDisplayed())).perform(pressBack())
        // press back button
        onView(withId(R.id.search)).check(matches(isDisplayed())).perform(pressBack())
    }

    @Test
    fun testSearchLeague() {
        // press first item on recycler view
        onView(withId(R.id.rv_list_league)).check(matches(isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        // waiting for detail result
        Thread.sleep(threadSleep)
        // press search menu
        onView(withId(R.id.search)).perform(click())
        // typing query
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("arsenal"))
            .perform(pressImeActionButton())

        // waiting for result
        Thread.sleep(threadSleep)
        // press first item on recycler view
        onView(withId(R.id.rv_search_result)).check(matches(isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        Thread.sleep(threadSleep)
        // press back button
        onView(withText("Stats")).check(matches(isDisplayed())).perform(pressBack())
        // press back button
        onView(withId(R.id.search)).check(matches(isDisplayed())).perform(pressBack())
    }

    @Test
    fun testSearchNoResult() {
        // press search menu
        onView(withId(R.id.search)).perform(click())
        // typing query
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("persija jakarta"))
            .perform(pressImeActionButton())
        // waiting for result
        Thread.sleep(3500)
        // press first item on recycler view
        onView(withId(R.id.txt_failed)).check(matches(isDisplayed()))
        // press back button
        onView(withId(R.id.txt_failed)).perform(pressBack())
    }

    @Test
    fun testSearchLeagueNoResult() {
        // press first item on recycler view
        onView(withId(R.id.rv_list_league)).check(matches(isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        // waiting for detail result
        Thread.sleep(threadSleep)
        // press search menu
        onView(withId(R.id.search)).perform(click())
        // typing query
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("barcelona"))
            .perform(pressImeActionButton())
        // waiting for result
        Thread.sleep(3500)
        // press first item on recycler view
        onView(withId(R.id.txt_failed)).check(matches(isDisplayed()))
        // press back button
        onView(withId(R.id.txt_failed)).perform(pressBack())
    }

    @Test
    fun testSearchTeam() {
        // press search menu
        onView(withId(R.id.search)).perform(click())
        // typing query
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("arsenal"))
            .perform(pressImeActionButton())
        // waiting for result
        Thread.sleep(threadSleep)
        // press first item on recycler view

        onView(withText(R.string.team_name)).check(matches(isDisplayed())).perform(click())

        onView(withId(R.id.rv_search_result_team)).check(matches(isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        // waiting for detail result
        Thread.sleep(threadSleep)
        // press back button
        onView(withText(R.string.tab_team_player)).check(matches(isDisplayed())).perform(click())

        onView(withId(R.id.rv_list_player)).check(matches(isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        Thread.sleep(threadSleep)
        onView(withId(R.id.txt_player_name)).check(matches(isDisplayed())).perform(pressBack())
        onView(withText(R.string.tab_team_player)).check(matches(isDisplayed())).perform(pressBack())
        onView(withText(R.string.team_name)).check(matches(isDisplayed())).perform(pressBack())

    }
}