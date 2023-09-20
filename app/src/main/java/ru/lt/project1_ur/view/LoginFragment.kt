package ru.lt.project1_ur.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.lt.project1_ur.R
import ru.lt.project1_ur.databinding.FragmentLoginBinding
import ru.lt.project1_ur.model.LoginFragmentViewModel
import ru.lt.project1_ur.state.NavigatorIntent.ToWeb
import ru.lt.project1_ur.state.NavigatorIntent.ToCatalog
import ru.lt.project1_ur.state.ProjectLoginIntent.LoginEntered
import ru.lt.project1_ur.state.ProjectLoginIntent.NavigateTo

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_start){
    private val viewModel: LoginFragmentViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner) {
            if (it.beckAuth){
                binding.beckAuth.visibility = View.VISIBLE
            }
        }

        val name = binding.editTextText
        val pass = binding.editTextTextPassword

        binding.button.setOnClickListener {
            if (name.editText!!.text.toString().isNotEmpty() && pass.editText!!.text.toString().length > 5) {
                viewModel.processIntents(
                    LoginEntered(
                    R.string.start_name,
                    name.editText!!.text.toString(),
                    pass.editText!!.text.toString()
                ))
                viewModel.processIntents(NavigateTo(ToCatalog))
            }
            name.error = if (name.editText!!.text.toString().isNotEmpty()) null else "Обязательно к заполнению"
            pass.error = if (pass.editText!!.text.toString().isNotEmpty() && pass.editText!!.text.toString().length > 5) null else
                if (pass.editText!!.text.toString().isNotEmpty()) "Минимум 6 символов" else "Обязательно к заполнению"
        }

        name.editText?.doOnTextChanged { inputText, _, _, _ ->
            val charCount = inputText?.length ?: 0
            name.error = if (charCount == 0) "Обязательно к заполнению" else null
        }

        pass.editText?.doOnTextChanged { inputText, _, _, _ ->
            val charCount = inputText?.length ?: 0
            pass.error = if (charCount > 5) null else
                if (charCount == 0) "Обязательно к заполнению" else "Минимум 6 символов"
        }

        binding.buttonLose.setOnClickListener {
            viewModel.processIntents(NavigateTo(ToCatalog))

        }

        binding.buttonReg.setOnClickListener {
            viewModel.processIntents(NavigateTo(ToWeb))

        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateFlow.collect { destination ->
                    when (destination) {
                        ToCatalog -> findNavController().navigate(R.id.action_loginFragment_to_catalogFragment)
                        ToWeb -> findNavController().navigate(R.id.action_loginFragment_to_webViewFragment)
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