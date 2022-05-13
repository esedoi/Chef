package com.paul.chef.ui.login

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.paul.chef.MainActivity
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.databinding.FragmentLoginBinding


/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LoginFragment : Fragment() {



    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.signInButton.isEnabled = false

        binding.loginTermsTxt.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalTermsFragment())
        }
        binding.checkBox.setOnCheckedChangeListener { compoundButton, b ->
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