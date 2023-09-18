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
import ru.lt.project1_ur.databinding.FragmentChatBinding
import ru.lt.project1_ur.model.ChatViewModel
import ru.lt.project1_ur.state.ProjectViewState

@AndroidEntryPoint
class ChatFragment : Fragment(R.layout.fragment_chat){
    private val viewModel: ChatViewModel by viewModels()
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner) {
            binding.textChat.text = "Чат с ${it.itemName}"
        }


        val recyclerView = binding.recyclerView
        recyclerView.adapter = viewModel.adapter
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            viewModel.adapter.setMessages(messages)
        }

        val dividerDrawable = context?.let { AppCompatResources.getDrawable(it, R.drawable.divider) }
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            dividerDrawable?.let { setDrawable(it) }
        })

        binding.sendMessageButton.setOnClickListener {
            val messageText = binding.messageEditText.text.toString()
            if (messageText.isNotEmpty()) {
                viewModel.addMessage(ProjectViewState.Message(messageText, true, ""))

                binding.recyclerView.scrollToPosition(viewModel.adapter.itemCount - 1)

                Handler(Looper.getMainLooper()).postDelayed({
                    val possibleAnswers = listOf("Привет!", "Как дела?", "Что делаешь?", "Невероятно!")
                    val randomAnswer = possibleAnswers.random()
                    viewModel.addMessage(ProjectViewState.Message(randomAnswer, false, viewModel.data.data.itemAvatar?:""))

                    binding.recyclerView.scrollToPosition(viewModel.adapter.itemCount - 1)

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