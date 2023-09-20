package ru.lt.project1_ur.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.lt.project1_ur.R
import ru.lt.project1_ur.data.OnItemClickListener
import ru.lt.project1_ur.data.PageScrollListener
import ru.lt.project1_ur.data.ProjectAdapterPerson
import ru.lt.project1_ur.databinding.FragmentPersonBinding
import ru.lt.project1_ur.model.PersonFragmentViewModel
import ru.lt.project1_ur.state.NavigatorIntent
import ru.lt.project1_ur.state.ProjectPersonIntent
import ru.lt.project1_ur.state.ProjectPersonIntent.AddRecyclerView
import ru.lt.project1_ur.state.ProjectPersonIntent.ScrollUp
import ru.lt.project1_ur.state.ProjectViewState

@AndroidEntryPoint
class PersonFragment : BaseFragment(R.layout.fragment_person){
    private val viewModel: PersonFragmentViewModel by viewModels()
    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!

    override val actionToLoginFragment = R.id.action_personFragment_to_loginFragment


    val adapter by lazy {
        ProjectAdapterPerson(object : OnItemClickListener {
            override fun onItemClick(model: ProjectViewState.Person) {
                viewModel.processIntents(ProjectPersonIntent.ModelEntered(model))
                viewModel.processIntents(ProjectPersonIntent.NavigateTo)
            }

            override fun onItemClick(model: ProjectViewState.Catalog) {
            }
        })
    }

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
        recyclerView.adapter = adapter
        val manager = LinearLayoutManager(context)

        viewModel.viewState.observe(viewLifecycleOwner) {
            binding.textCatalogName.text = it.type

            adapter.setItems(it.personList)
            recyclerView.layoutManager = manager

            val paging = PageScrollListener(manager).apply {
                onLoadMore = {
                    viewModel.presenter.fetchCatalogList(it.typeId, it.countPage) { newItems ->
                        adapter.addItems(newItems)
                        setLoading(false)
                    }
                }
            }
            recyclerView.addOnScrollListener(paging)
        }
        viewModel.presenter.personItems.observe(viewLifecycleOwner) { personList ->
            viewModel.processIntents(AddRecyclerView(personList), true)
        }
        binding.scrollUpButton.setOnClickListener {
            viewModel.processIntents(ScrollUp(recyclerView))
        }

        binding.overflowButton.setOnClickListener {
            showCommonPopupMenu(binding.overflowButton, requireContext())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateFlow.collect { destination ->
                    when (destination) {
                        NavigatorIntent.ToCart -> findNavController().navigate(R.id.action_personFragment_to_cartFragment)
                        else -> {}
                    }
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}