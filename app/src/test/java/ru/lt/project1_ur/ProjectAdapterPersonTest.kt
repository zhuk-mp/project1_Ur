package ru.lt.project1_ur

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.lt.project1_ur.data.OnItemClickListener
import ru.lt.project1_ur.data.ProjectAdapterPerson
import ru.lt.project1_ur.state.ProjectViewState

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner::class)
class ProjectAdapterPersonTest {

    private lateinit var adapter: ProjectAdapterPerson
    private lateinit var mockListener: OnItemClickListener

    @Before
    fun setUp() {
        mockListener = Mockito.mock(OnItemClickListener::class.java)
        adapter = ProjectAdapterPerson(mockListener)
    }

    @Test
    fun `getItemCount should return correct count`() {
        val items = mutableListOf(
            ProjectViewState.Person(1,"","","","","","","",1,1),
            ProjectViewState.Person(1,"","","","","","","",1,1),
        )
        adapter.setItems(items)
        Truth.assertThat(adapter.itemCount).isEqualTo(2)
    }

    @Test
    fun `addItemCount should return correct count`() {
        val items = mutableListOf(
            ProjectViewState.Person(1,"","","","","","","",1,1),
        )
        val itemsAdd = mutableListOf(
            ProjectViewState.Person(1,"","","","","","","",1,1),
        )
        adapter.setItems(items)
        adapter.addItems(itemsAdd)
        Truth.assertThat(adapter.itemCount).isEqualTo(2)
    }
}