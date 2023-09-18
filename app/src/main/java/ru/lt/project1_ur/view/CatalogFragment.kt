package ru.lt.project1_ur.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.lt.project1_ur.R
import ru.lt.project1_ur.databinding.FragmentCatalogBinding
import ru.lt.project1_ur.model.CatalogFragmentViewModel

@AndroidEntryPoint
class CatalogFragment : BaseFragment(R.layout.fragment_start){
    private val viewModel: CatalogFragmentViewModel by viewModels()
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    override val actionToLoginFragment = R.id.action_catalogFragment_to_loginFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner) {
            if (it.navi){
                viewModel.onNaviFalse()

                findNavController().navigate(
                    R.id.action_catalogFragment_to_personFragment,
                )
            }

        }
        viewModel.presenter.catalogItems.observe(viewLifecycleOwner) { catalogList ->
            val recyclerView = binding.recyclerView
            recyclerView.adapter = viewModel.adapter
            viewModel.adapter.setItems(catalogList)
            val manager = LinearLayoutManager(context)
            recyclerView.layoutManager = manager
        }

        binding.overflowButton.setOnClickListener {
            showCommonPopupMenu(binding.overflowButton, requireContext())
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                findNavController().navigate(
                    R.id.action_catalogFragment_to_startFragment,
                )
        }

    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}