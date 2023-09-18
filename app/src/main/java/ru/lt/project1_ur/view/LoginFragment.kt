package ru.lt.project1_ur.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.lt.project1_ur.R
import ru.lt.project1_ur.databinding.FragmentLoginBinding
import ru.lt.project1_ur.model.LoginFragmentViewModel

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
                viewModel.onLoginEntered(
                    R.string.start_name,
                    name.editText!!.text.toString(),
                    pass.editText!!.text.toString()
                )
                viewModel.beckAuth()
                findNavController().navigate(R.id.action_loginFragment_to_catalogFragment)
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
            viewModel.beckAuth()
            findNavController().navigate(R.id.action_loginFragment_to_catalogFragment)

        }

        binding.buttonReg.setOnClickListener {
            viewModel.beckAuth()
            findNavController().navigate(R.id.action_loginFragment_to_webViewFragment)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}