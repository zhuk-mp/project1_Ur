package ru.lt.project1_ur

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.lt.project1_ur.data.OnItemClickListener
import ru.lt.project1_ur.data.ProjectViewHolder
import ru.lt.project1_ur.state.ProjectViewState

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner::class)
class ProjectViewHolderTest {

    private lateinit var mockListener: OnItemClickListener
    private lateinit var context: Context

    @Before
    fun setUp() {
        mockListener = mock(OnItemClickListener::class.java)
        context = ApplicationProvider.getApplicationContext()
    }
    @Test
    fun `bindCatalog should not throw exception`() {
        val itemView = LinearLayout(context).apply {
            addView(TextView(context).apply {
                id = R.id.textView_sfera
            })
            addView(ImageView(context).apply {
                id = R.id.imageView_avatar
            })
        }

        val holder = ProjectViewHolder(itemView, mockListener)
        val model = ProjectViewState.Catalog(1, "", "")

        try {
            holder.bindCatalog(model)
        } catch (e: Exception) {
            fail("Method threw an exception: $e")
        }
    }

    @Test
    fun `bindPerson should not throw exception`() {
        val itemView = LinearLayout(context).apply {
            addView(TextView(context).apply {
                id = R.id.textView_name
            })
            addView(TextView(context).apply {
                id = R.id.textView_spec
            })
            addView(TextView(context).apply {
                id = R.id.textView_address
            })
            addView(TextView(context).apply {
                id = R.id.textView_count
            })
            addView(TextView(context).apply {
                id = R.id.textView_rew
            })
            addView(ImageView(context).apply {
                id = R.id.imageView_avatar
            })
        }

        val holder = ProjectViewHolder(itemView, mockListener)
        val model = ProjectViewState.Person(1,"","","","","","","",1,1)

        try {
            holder.bindPerson(model)
        } catch (e: Exception) {
            fail("Method threw an exception: $e")
        }
    }


}