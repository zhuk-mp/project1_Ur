package ru.lt.project1_ur

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class AtPositionRVMatcher (
    private val position: Int,
    private val matcher: Matcher<View>
) : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java){
    override fun describeTo(description: Description) {
        description.appendText("not matchers")
    }

    override fun matchesSafely(item: RecyclerView): Boolean {
        return matcher.matches(item.findViewHolderForAdapterPosition(position)?.itemView)
    }
}

fun atPositionRV(position: Int, matcher: Matcher<View>) = AtPositionRVMatcher(position, matcher)

class RecyclerViewSize (
    private val size: Int,
) : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java){
    override fun describeTo(description: Description) {
        description.appendText("not matchers")
    }

    override fun matchesSafely(item: RecyclerView): Boolean {
        return item.adapter?.itemCount == size
    }
}

fun size(size: Int) = RecyclerViewSize(size)