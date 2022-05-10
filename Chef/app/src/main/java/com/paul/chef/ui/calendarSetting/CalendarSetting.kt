package com.paul.chef.ui.calendarSetting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kizitonwose.calendarview.utils.yearMonth
import com.paul.chef.DateStatus
import com.paul.chef.data.MenuStatus
import com.paul.chef.databinding.FragmentCalendarSettingBinding
import com.paul.chef.ui.chefEdit.ChefEditViewModel
import com.paul.chef.ui.menuDetail.MenuDetailFragmentArgs
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


class CalendarSetting : Fragment() {

    private var _binding: FragmentCalendarSettingBinding? = null
    private val binding get() = _binding!!


    private val arg: CalendarSettingArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCalendarSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val calendarSettingViewModel =
            ViewModelProvider(this).get(CalendarSettingViewModel::class.java)


        val selectedDates = arg.selectedDates.selectedDates

        if(selectedDates.size==1){
            val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MMMM dd, EEEE", Locale.TAIWAN);
            binding.calendarSetSelectDateTxt.text = selectedDates[0].format(dateTimeFormatter)

        }else{
            binding.calendarSetSelectDateTxt.text = "已選定 ${selectedDates.size} 個日期"
        }

        if(binding.dateOpenSwitch.isChecked){
            binding.calendarSetSwitchTxt.text = "開放中"
        }else{
            binding.calendarSetSwitchTxt.text = "不可訂"
        }
        binding.dateOpenSwitch.setOnCheckedChangeListener { compoundButton, b ->
            if(compoundButton.isChecked){
                binding.calendarSetSwitchTxt.text = "開放中"
            }else{
                binding.calendarSetSwitchTxt.text = "不可訂"
            }
        }



        Log.d("calendarsetting", "selectedDates=${selectedDates}")

//        if(selectedDates.size == 1){
//            binding.selectdatesType.visibility = View.VISIBLE
//        }else{
//            binding.selectdatesType.visibility = View.GONE
//        }


        binding.dateSave.setOnClickListener {

            val switch = binding.dateOpenSwitch.isChecked
            val status = if (switch) {
                DateStatus.OPEN.index
            } else {
                DateStatus.CLOSE.index
            }
            calendarSettingViewModel.settingDate(status, selectedDates)

            findNavController().navigateUp()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}