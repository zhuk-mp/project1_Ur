package ru.lt.project1_ur.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import ru.lt.project1_ur.R
import ru.lt.project1_ur.databinding.FragmentCartBinding
import ru.lt.project1_ur.model.CartFragmentViewModel

@AndroidEntryPoint
class CartFragment : BaseFragment(R.layout.fragment_cart){
    private val viewModel: CartFragmentViewModel by viewModels()
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    override val actionToLoginFragment = R.id.action_cartFragment_to_loginFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner) {

            if (!it.address.isNullOrEmpty())
                binding.cartAddress.text = it.address
            else{
                binding.cartAddress.visibility = View.GONE
                binding.cartInfoAddress.visibility = View.GONE
            }
            if (!it.spec.isNullOrEmpty())
                binding.cartSpec.text = it.spec
            else{
                binding.cartSpec.visibility = View.GONE
                binding.cartInfoSpec.visibility = View.GONE
            }
            if (!it.type.isNullOrEmpty())
                binding.cartSfera.text = it.type
            else{
                binding.cartSfera.visibility = View.GONE
                binding.cartInfoSfera.visibility = View.GONE
            }
            if (!it.desc.isNullOrEmpty())
                binding.cartDesc.text = it.desc
            else{
                binding.cartDesc.visibility = View.GONE
                binding.cartInfoDesc.visibility = View.GONE
            }

            binding.cartName.text = it.name

            binding.cartPhone.text = it.phone
            binding.viewView.text = it.count.toString()
            binding.rewView.text = it.stars.toString()

            val avatar: ImageView = binding.avatar
            val imageUrl = it.avatar

            if (!imageUrl.isNullOrEmpty())
                Picasso.get()
                    .load(imageUrl)
                    .resize(200, 200)
                    .centerCrop()
                    .transform(CropCircleTransformation())
                    .into(avatar)
            else
                avatar.setImageResource(R.drawable.avatar)

        }

        binding.goChat.setOnClickListener {
            viewModel.update()
            findNavController().navigate(
                if (viewModel.viewState.value!!.auth) R.id.action_cartFragment_to_chatFragment
                else {
                    viewModel.beckAuth()
                    R.id.action_cartFragment_to_loginFragment
                }
            )
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