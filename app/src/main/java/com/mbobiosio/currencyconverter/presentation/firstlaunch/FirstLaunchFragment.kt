package com.mbobiosio.currencyconverter.presentation.firstlaunch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mbobiosio.currencyconverter.databinding.FragmentFirstLaunchBinding
import com.mbobiosio.currencyconverter.presentation.base.BaseBindingFragment
import com.mbobiosio.currencyconverter.util.navigateSafe
import com.mbobiosio.currencyconverter.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@AndroidEntryPoint
class FirstLaunchFragment : BaseBindingFragment() {
    private var _binding: FragmentFirstLaunchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()

    override fun bindFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentFirstLaunchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {

        with(binding) {

            btnStart.setOnClickListener {
                viewModel.setFirstLaunch(false)
                navigateSafe(FirstLaunchFragmentDirections.actionFirstLaunchToHomeFragment())
            }
        }
    }

    override fun unbindFragment() {
        _binding = null
    }
}
