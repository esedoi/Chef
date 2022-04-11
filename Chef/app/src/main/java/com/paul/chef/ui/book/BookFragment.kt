package com.paul.chef.ui.book

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.paul.chef.databinding.FragmentBookBinding
import com.paul.chef.databinding.FragmentChefPageBinding
import com.paul.chef.ui.chef.ChefViewModel
import com.paul.chef.ui.menuDetail.MenuDetailFragmentArgs
import java.time.LocalDate
import java.util.*

class BookFragment: Fragment() {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!
    var selectDate:Long? = null

    var picker:LocalDate? = null

    //safe args
    private val arg: MenuDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bookViewModel =
            ViewModelProvider(this).get(BookViewModel::class.java)

        _binding = FragmentBookBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //navigation safe args
        val menu = arg.chefMenu

       menu.orderSetting.defaultTime

        var datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText("Select date")
                .build()


            binding.datepicker.setOnClickListener {
                datePicker.show(this.parentFragmentManager, "tag")
            }

        datePicker.addOnPositiveButtonClickListener {
            Log.d("bookfragment", "datePicker.selection=${datePicker.selection}")
            selectDate = datePicker.selection
//            val sdf = SimpleDateFormat("YYYY-MM-dd")
//            val day = sdf.format(datePicker.selection?.let { it1 -> Date(it1) })
//            val localday:LocalDate = LocalDate.parse(day)
//            Log.d("bookfragment", "localday=${localday}")
//            picker = localday
        }



        val menuId = "WY8R85RyloUzKaTpsKsO"

        binding.pay.setOnClickListener {
            if (selectDate != null){
                bookViewModel.book(menu, selectDate!!)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

