package ru.lt.project1_ur.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.lt.project1_ur.R
import ru.lt.project1_ur.databinding.FragmentStartBinding
import ru.lt.project1_ur.model.StartFragmentViewModel
import ru.lt.project1_ur.state.NavigatorIntent
import ru.lt.project1_ur.state.ProjectStartIntent.NavigateTo

@AndroidEntryPoint
class StartFragment : Fragment(R.layout.fragment_start){
    private val viewModel: StartFragmentViewModel by viewModels()
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner) {
                binding.textStartName.text = it.name

            viewLifecycleOwner.lifecycleScope.launch {
                delay(5000)

                viewModel.processIntents(NavigateTo)
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateFlow.collect { destination ->
                    when (destination) {
                        NavigatorIntent.ToCatalog -> findNavController().navigate(R.id.action_startFragment_to_catalogFragment)
                        NavigatorIntent.ToLogin -> findNavController().navigate(R.id.action_startFragment_to_loginFragment)
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