package ru.lt.project1_ur

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.lt.project1_ur.data.ChatAdapter
import ru.lt.project1_ur.state.ProjectViewState
@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner::class)
class ChatAdapterTest {

    private lateinit var adapter: ChatAdapter

    @Before
    fun setUp() {
        adapter = ChatAdapter()
    }

    @Test
    fun `getItemCount should return correct count`() {
        val messages = listOf(
            ProjectViewState.Message("Hi", true, ""),
            ProjectViewState.Message("Hello", false, "")
        )
        adapter.setMessages(messages)
        assertThat(adapter.itemCount).isEqualTo(2)
    }

    @Test
    fun `getItemViewType should return correct view type for user and bot`() {
        val messages = listOf(
            ProjectViewState.Message("Hi", true, ""),
            ProjectViewState.Message("Hello", false, "")
        )
        adapter.setMessages(messages)

        assertThat(adapter.getItemViewType(0)).isEqualTo(ChatAdapter.VIEW_TYPE_USER)
        assertThat(adapter.getItemViewType(1)).isEqualTo(ChatAdapter.VIEW_TYPE_BOT)
    }

    @Test
    fun `setMessages should correctly set messages`() {
        val messages = listOf(
            ProjectViewState.Message("Hi", true, ""),
            ProjectViewState.Message("Hello", false, "")
        )
        adapter.setMessages(messages)
        assertThat(adapter.itemCount).isEqualTo(2)

        val newMessages = listOf(
            ProjectViewState.Message("New Message", true, "")
        )
        adapter.setMessages(newMessages)
        assertThat(adapter.itemCount).isEqualTo(1)
    }
}
