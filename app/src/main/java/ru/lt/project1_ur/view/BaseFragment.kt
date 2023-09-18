package ru.lt.project1_ur.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.lt.project1_ur.R
import ru.lt.project1_ur.model.BaseFragmentViewModel

@AndroidEntryPoint
open class BaseFragment(fragmentStart: Int) : Fragment(fragmentStart) {

    private lateinit var viewModel: BaseFragmentViewModel
    open val actionToLoginFragment: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[BaseFragmentViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigateToChatFragment.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                findNavController().navigate(actionToLoginFragment)
                viewModel.navigateToChatFragment.value = false
            }
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            if (it.isSwitchTheme) {
                requireActivity().recreate()
                viewModel.isSwitchThemeOff()
            }
        }
    }


    fun showCommonPopupMenu(view: View, context: Context) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.common_menu)



        val menuItem1 = popupMenu.menu.findItem(R.id.option_1)
        val menuItem2 = popupMenu.menu.findItem(R.id.option_2)

        menuItem1.title = viewModel.viewState.value!!.menu1
        menuItem2.title = viewModel.viewState.value!!.menu2
        menuItem2.isVisible = viewModel.viewState.value!!.menu2IsVisible

        popupMenu.setOnMenuItemClickListener { menuItem ->
            viewModel.handleCommonMenuItemClick(menuItem)
        }
        popupMenu.show()
    }
}

