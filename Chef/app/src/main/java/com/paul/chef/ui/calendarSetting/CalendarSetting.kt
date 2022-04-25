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
import com.paul.chef.DateStatus
import com.paul.chef.data.MenuStatus
import com.paul.chef.databinding.FragmentCalendarSettingBinding
import com.paul.chef.ui.chefEdit.ChefEditViewModel
import com.paul.chef.ui.menuDetail.MenuDetailFragmentArgs


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
        
        
        Log.d("calendarsetting", "selectedDates=${selectedDates}")

        if(selectedDates.size == 1){
            binding.selectdatesType.visibility = View.VISIBLE
        }else{
            binding.selectdatesType.visibility = View.GONE
        }




        binding.dateSave.setOnClickListener {
            val type = 1
            val close:Int = 1
            val m1 = MenuStatus("123", 1)
            val m2 = MenuStatus("2341", 2)
            val menuList = listOf(m1, m2)

            val selectWeekDay:String = "fri"
            val session:List<String>? = listOf("11:00")
            val startTime:String? = "10:00"
            val endTime:String? = "12:00"
            val switch = binding.dateOpenSwitch.isChecked
            val status = if(switch){
                DateStatus.OPEN.index
            }else{
                DateStatus.CLOSE.index
            }
            calendarSettingViewModel.settingDate(type, status, menuList, selectedDates, selectWeekDay, session, startTime, endTime)

            findNavController().navigateUp()
        }




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}