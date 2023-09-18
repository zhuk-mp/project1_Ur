package ru.lt.project1_ur

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.lt.project1_ur.data.CatalogItems
import ru.lt.project1_ur.data.File
import ru.lt.project1_ur.data.Icon
import ru.lt.project1_ur.data.ProjectDataService
import ru.lt.project1_ur.data.ProjectPresenter
import ru.lt.project1_ur.data.TypeItem

class ProjectPresenterTest {

    @Mock
    private lateinit var projectDataService: ProjectDataService

    private lateinit var projectPresenter: ProjectPresenter

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        projectPresenter = ProjectPresenter(projectDataService)
    }
    @Test
    fun `updateCatalogItems should correctly transform TypeItem to Catalog`() {
        val typeItems = listOf(
            TypeItem(1, "Title1", Icon(0, "", ""), File(0, "", "path1")),
            TypeItem(2, "Title2", Icon(0, "", ""), File(0, "", "path2"))
        )

        val result = projectPresenter.updateCatalogItems(typeItems)

        assertEquals(2, result.size)
        assertEquals(1, result[0].id)
        assertEquals("Title1", result[0].sfera)
        assertEquals("path1", result[0].avatar)
    }

    @Test
    fun `updatePersonItems should correctly transform CatalogItems to Person`() {
        val catalogItems = listOf(
            CatalogItems(1, "Name1", null, 0, listOf(),0, "", CatalogItems.PublicAvatar(0, "", "", "", "path1"), 0, CatalogItems.Address(0, "", CatalogItems.Data("city1"))),
            CatalogItems(2, "Name2", null, 0, listOf(),0, "", CatalogItems.PublicAvatar(0, "", "", "", "path2"), 0, CatalogItems.Address(0, "", CatalogItems.Data("city2")))
        )

        val result = projectPresenter.updatePersonItems(catalogItems)

        assertEquals(2, result.size)
        assertEquals(1, result[0].id)
        assertEquals("Name1", result[0].name)
        assertEquals("city1", result[0].address)
        assertEquals("path1", result[0].avatar)
    }

}


