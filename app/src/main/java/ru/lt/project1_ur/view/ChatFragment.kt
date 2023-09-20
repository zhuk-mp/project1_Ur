package ru.lt.project1_ur.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import ru.lt.project1_ur.R
import ru.lt.project1_ur.data.ChatAdapter
import ru.lt.project1_ur.databinding.FragmentChatBinding
import ru.lt.project1_ur.model.ChatViewModel
import ru.lt.project1_ur.state.ProjectChatIntent.SendMessage
import ru.lt.project1_ur.state.ProjectChatIntent.BotSendMessage
import ru.lt.project1_ur.state.ProjectViewState.Message

@AndroidEntryPoint
class ChatFragment : Fragment(R.layout.fragment_chat){
    private val viewModel: ChatViewModel by viewModels()
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    val adapter by lazy { ChatAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter

        viewModel.viewState.observe(viewLifecycleOwner) {
            binding.textChat.text = getString(R.string.chatWhith, it.itemName)
            adapter.setMessages(it.messageList)
        }

        val dividerDrawable = context?.let { AppCompatResources.getDrawable(it, R.drawable.divider) }
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            dividerDrawable?.let { setDrawable(it) }
        })

        binding.sendMessageButton.setOnClickListener {
            val messageText = binding.messageEditText.text.toString()
            if (messageText.isNotEmpty()) {
                viewModel.processIntents(SendMessage(Message(messageText, true, "")),true)
                recyclerView.scrollToPosition(adapter.itemCount - 1)

                Handler(Looper.getMainLooper()).postDelayed({
                    viewModel.processIntents(BotSendMessage(viewModel.data.data.itemAvatar),true)
                    recyclerView.scrollToPosition(adapter.itemCount - 1)
                }, 1000)

                binding.messageEditText.setText("")
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}