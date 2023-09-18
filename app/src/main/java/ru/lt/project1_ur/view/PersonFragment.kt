package ru.lt.project1_ur.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.lt.project1_ur.R
import ru.lt.project1_ur.data.PageScrollListener
import ru.lt.project1_ur.databinding.FragmentPersonBinding
import ru.lt.project1_ur.model.PersonFragmentViewModel

@AndroidEntryPoint
class PersonFragment : BaseFragment(R.layout.fragment_person){
    private val viewModel: PersonFragmentViewModel by viewModels()
    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!
    private var countPage = 1

    override val actionToLoginFragment = R.id.action_personFragment_to_loginFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        return binding.root


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView

        viewModel.viewState.observe(viewLifecycleOwner) {
            if (it.navi){
                viewModel.onNaviFalse()
                findNavController().navigate(
                    if (it.auth) R.id.action_personFragment_to_cartFragment
                    else {
                        viewModel.beckAuth()
                        R.id.action_personFragment_to_loginFragment
                    }
                )
            }

            binding.textCatalogName.text = it.type

        }
        viewModel.presenter.personItems.observe(viewLifecycleOwner) { personList ->
            recyclerView.adapter = viewModel.adapter
            viewModel.adapter.setItems(personList)
            val manager = LinearLayoutManager(context)
            recyclerView.layoutManager = manager

            val paging = PageScrollListener(manager).apply {
                onLoadMore = {
                    viewModel.presenter.fetchCatalogList(viewModel.data.data.typeId ?: 0, ++countPage) { newItems ->
                        viewModel.adapter.addItems(newItems)
                        setLoading(false)
                    }
                }
            }
            recyclerView.addOnScrollListener(paging)
        }
        binding.scrollUpButton.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        binding.overflowButton.setOnClickListener {
            showCommonPopupMenu(binding.overflowButton, requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}