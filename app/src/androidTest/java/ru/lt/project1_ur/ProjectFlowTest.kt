package ru.lt.project1_ur

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test

class ProjectFlowTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val name = "Anton"
    private val password = "123456"
    private val outText = "Выйти"
    private val inText = "Войти"

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val isUserLoggedIn = preferences.getBoolean("isUserLoggedIn", false)


    @Test
    fun loginTest(){
        Log.d("log----------------", "isUserLoggedIn: $isUserLoggedIn")
        onView(withId(R.id.textStartUr))
            .check(
                matches(
                    allOf(
                        withText(R.string.textStartUr),
                        isDisplayed()
                    )
                )
            )
        Thread.sleep(6000)
        if (isUserLoggedIn) {
            onView(withId(R.id.overflow_button)).perform(click())
            onView(withText(outText)).inRoot(RootMatchers.isPlatformPopup()).perform(click())
            Espresso.pressBack()
            Thread.sleep(6000)
        }
        onView(withId(R.id.buttonLose)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(0, click())
            )
        Thread.sleep(1000)
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(0, click())
            )
        onView(withId(R.id.goChat)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.editTextTextIn)).perform(typeText(name))
        onView(withId(R.id.editTextTextPasswordIn)).perform(typeText(password))
        onView(withId(R.id.button)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(0, click())
            )
        Thread.sleep(1000)
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(0, click())
            )
        onView(withId(R.id.goChat)).perform(click())
        onView(withId(R.id.messageEditText)).perform(typeText("Hi"))
        onView(withId(R.id.sendMessageButton)).perform(click())
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.recyclerView)).check(matches(atPositionRV(0, hasDescendant(withText("Hi")))))
        Thread.sleep(1000)
        onView(withId(R.id.recyclerView)).check(matches(size(2)))
        Espresso.pressBack()
        Thread.sleep(1000)
        onView(withId(R.id.overflow_button)).perform(click())
        onView(withText(name)).inRoot(RootMatchers.isPlatformPopup()).check(matches(withText(name)))
        onView(withText(outText)).inRoot(RootMatchers.isPlatformPopup()).perform(click())
        onView(withId(R.id.overflow_button)).perform(click())
        onView(withText(inText)).inRoot(RootMatchers.isPlatformPopup()).check(matches(withText(inText)))
        onView(withText(inText)).inRoot(RootMatchers.isPlatformPopup()).perform(click())
        onView(withId(R.id.editTextTextIn)).perform(typeText(name))
        onView(withId(R.id.editTextTextPasswordIn)).perform(typeText(password))
        onView(withId(R.id.button)).perform(click())
        Espresso.pressBack()
        onView(withId(R.id.textStartName)).check(matches(withText("Здравствуйте, $name")))
        Thread.sleep(2000)

    }
}