package ru.lt.project1_ur.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.lt.project1_ur.R
import ru.lt.project1_ur.data.OnItemClickListener
import ru.lt.project1_ur.data.ProjectAdapterCatalog
import ru.lt.project1_ur.databinding.FragmentCatalogBinding
import ru.lt.project1_ur.model.CatalogFragmentViewModel
import ru.lt.project1_ur.state.NavigatorIntent
import ru.lt.project1_ur.state.ProjectCatalogIntent
import ru.lt.project1_ur.state.ProjectCatalogIntent.AddRecyclerView
import ru.lt.project1_ur.state.ProjectViewState

@AndroidEntryPoint
class CatalogFragment : BaseFragment(R.layout.fragment_start){
    private val viewModel: CatalogFragmentViewModel by viewModels()
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    override val actionToLoginFragment = R.id.action_catalogFragment_to_loginFragment

    val adapter by lazy {
        ProjectAdapterCatalog(object : OnItemClickListener {
            override fun onItemClick(model: ProjectViewState.Person) {
            }

            override fun onItemClick(model: ProjectViewState.Catalog) {
                viewModel.processIntents(ProjectCatalogIntent.ModelEntered(model))
                viewModel.processIntents(ProjectCatalogIntent.NavigateTo)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter

        viewModel.viewState.observe(viewLifecycleOwner) {
           adapter.setItems(it.catalogList)
        }
        viewModel.presenter.catalogItems.observe(viewLifecycleOwner) { catalogList ->
            viewModel.processIntents(AddRecyclerView(catalogList), true)
        }

        binding.overflowButton.setOnClickListener {
            showCommonPopupMenu(binding.overflowButton, requireContext())
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                findNavController().navigate(
                    R.id.action_catalogFragment_to_startFragment,
                )
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateFlow.collect { destination ->
                    when (destination) {
                        NavigatorIntent.ToPerson -> findNavController().navigate(R.id.action_catalogFragment_to_personFragment)
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