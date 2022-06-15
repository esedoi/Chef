package com.paul.chef.ui.calendarSetting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paul.chef.DateStatus
import com.paul.chef.R
import com.paul.chef.databinding.FragmentCalendarSettingBinding
import com.paul.chef.ext.getVmFactory
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarSettingFragment : Fragment() {

    private var _binding: FragmentCalendarSettingBinding? = null
    private val binding get() = _binding!!

    private val arg: CalendarSettingFragmentArgs by navArgs()

    private val calendarSettingViewModel by viewModels<CalendarSettingViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val selectedDates = arg.selectedDates.selectedDates

        if (selectedDates.size == 1) {
            val dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy MMMM dd, EEEE", Locale.TAIWAN)
            binding.calendarSetSelectDateTxt.text = selectedDates[0].format(dateTimeFormatter)
        } else {
            binding.calendarSetSelectDateTxt.text =
                getString(R.string.calendar_set_selected_dates_txt, selectedDates.size)
        }

        binding.dateSave.setOnClickListener {
            val radio = binding.calendarRadioGroup.checkedRadioButtonId

            val status = if (radio == binding.calendarSetOpenRadio.id) {
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
