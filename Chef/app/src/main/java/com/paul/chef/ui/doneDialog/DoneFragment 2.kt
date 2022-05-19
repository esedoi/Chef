package com.paul.chef.ui.doneDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paul.chef.MainActivity
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.databinding.FragmentDoneBinding
import java.util.*
import kotlin.concurrent.schedule



class DoneFragment : DialogFragment() {

    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!
    private val arg: DoneFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        val root: View = binding.root



        Timer().schedule(2000){
            (activity as MainActivity).runOnUiThread {
                when(arg.direction){
                    "orderMange"->{
                        findNavController().navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())
                    }
                    "chefPage"->{
                        findNavController().navigate(MobileNavigationDirections.actionGlobalChefFragment())
                    }
                }
            }
        }




        return root
    }

}