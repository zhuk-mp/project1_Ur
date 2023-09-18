package ru.lt.project1_ur

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.lt.project1_ur.data.OnItemClickListener
import ru.lt.project1_ur.data.ProjectAdapterCatalog
import ru.lt.project1_ur.state.ProjectViewState

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner::class)
class ProjectAdapterCatalogTest {

    private lateinit var adapter: ProjectAdapterCatalog
    private lateinit var mockListener: OnItemClickListener

    @Before
    fun setUp() {
        mockListener = mock(OnItemClickListener::class.java)
        adapter = ProjectAdapterCatalog(mockListener)
    }

    @Test
    fun `getItemCount should return correct count`() {
        val items = mutableListOf(
            ProjectViewState.Catalog(1,"",""),
            ProjectViewState.Catalog(1,"","")
        )
        adapter.setItems(items)
        assertThat(adapter.itemCount).isEqualTo(2)
    }

    @Test
    fun `addItemCount should return correct count`() {
        val items = mutableListOf(
            ProjectViewState.Catalog(1,"",""),
            ProjectViewState.Catalog(1,"","")
        )
        val itemsAdd = mutableListOf(
            ProjectViewState.Catalog(1,"",""),
            ProjectViewState.Catalog(1,"","")
        )
        adapter.setItems(items)
        adapter.addItems(itemsAdd)
        assertThat(adapter.itemCount).isEqualTo(4)
    }
}
