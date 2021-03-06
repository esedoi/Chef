package com.paul.chef.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.paul.chef.MainActivity
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.signInButton.isEnabled = false

        binding.loginTermsTxt.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalTermsFragment())
        }
        binding.checkBox.setOnCheckedChangeListener { compoundButton, _ ->
            binding.signInButton.isEnabled = compoundButton.isChecked
        }

        binding.signInButton.setOnClickListener {
            (activity as MainActivity).signIn()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
