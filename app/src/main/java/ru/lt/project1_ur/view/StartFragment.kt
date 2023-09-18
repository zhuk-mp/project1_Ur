package ru.lt.project1_ur.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.lt.project1_ur.databinding.FragmentStartBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.lt.project1_ur.R
import ru.lt.project1_ur.model.StartFragmentViewModel

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
            if (it.auth) {
                binding.textStartName.text = "Здравствуйте, ${it.name}"
            }

            viewLifecycleOwner.lifecycleScope.launch {
                delay(5000)

                findNavController().navigate(
                    if (it.auth) R.id.action_startFragment_to_catalogFragment
                    else R.id.action_startFragment_to_loginFragment
                )
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}