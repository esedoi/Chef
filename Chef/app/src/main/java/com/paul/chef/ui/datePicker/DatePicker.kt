package com.paul.chef.ui.datePicker


import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.paul.chef.BookSettingType
import com.paul.chef.CalendarType
import com.paul.chef.R
import com.paul.chef.data.BookSetting
import com.paul.chef.data.DateStatus
import com.paul.chef.data.SelectedDate
import com.paul.chef.databinding.*
import com.paul.chef.ui.book.BookFragmentArgs
import com.paul.chef.ui.calendarSetting.CalendarSettingViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoField
import java.time.temporal.WeekFields
import java.util.*

class DatePicker: BottomSheetDialogFragment() {

    private var _binding: BottomSheetDatepickerBinding? = null
    private val binding get() = _binding!!

    private val today = LocalDate.now()
    private var selectedDate: LocalDate? = null

    private val arg: DatePickerArgs by navArgs()

    var type: Int = -1
    var calendarDefault: Int = -1
    var dateList = mutableListOf<DateStatus>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetDatepickerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val datePickerViewModel =
            ViewModelProvider(this).get(DatePickerViewModel::class.java)
//        val bookSetting = arg.bookSetting
        val chefId = arg.chefId
//        datePickerViewModel.chefId = chefId


        datePickerViewModel.bookSetting.observe(viewLifecycleOwner) {
            calendarDefault = it.calendarDefault
            type = it.type
            binding.datePickerCalendarView.notifyCalendarChanged()
        }

        datePickerViewModel.dateSetting.observe(viewLifecycleOwner){
            dateList.addAll(it)
            binding.datePickerCalendarView.notifyCalendarChanged()
        }


        class DayViewContainer(view: View) : ViewContainer(view) {
            // With ViewBinding
            val textView = CalendarDayLayoutBinding.bind(view).calendarDayText

            // Will be set when this container is bound
            lateinit var day: CalendarDay


            init {
//                view.isClickable =
//                    !(calendarDefault == CalendarType.AllDayClose.index || type == BookSettingType.RefuseAll.index)

//                for (i in dateList) {
//                    val localDate: LocalDate = LocalDate.ofEpochDay(i.date)
//                    if (localDate == day.date) {
//                        view.isClickable = i.status != com.paul.chef.DateStatus.CLOSE.index
//                    }
//                }

                view.setOnClickListener {



                    if (day.owner == DayOwner.THIS_MONTH
                        && (day.date == today || day.date.isAfter(today))

                    ) {
                        val currentSelection = selectedDate
                        if (currentSelection == day.date) {
                            selectedDate = null
                            binding.datePickerCalendarView.notifyDateChanged(currentSelection)
                        } else {
                            selectedDate = day.date
                            binding.datePickerCalendarView.notifyDateChanged(day.date)
                            if (currentSelection != null){
                                binding.datePickerCalendarView.notifyDateChanged(currentSelection)
                            }
                        }
                    }
                }
            }

        }


        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = CalendarMonthHeaderLayoutBinding.bind(view).headerTextView
        }


        binding.datePickerCalendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                container.textView.text = day.date.dayOfMonth.toString()

                if (calendarDefault == CalendarType.AllDayClose.index || type == BookSettingType.RefuseAll.index) {
                    container.textView.paint.flags =
                        Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
                    Log.d("calendar", "畫線")
                } else {
                    container.textView.paint.flags = 0
                    container.textView.paint.isAntiAlias = true
                }

                for (i in dateList) {
                    val localDate: LocalDate = LocalDate.ofEpochDay(i.date)

                    if (localDate == day.date) {
                        if (i.status == com.paul.chef.DateStatus.CLOSE.index) {
                            container.textView.paint.flags =
                                Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
                            Log.d("calendar", "if_____________")

                        } else {
                            container.textView.paint.flags = 0
                            container.textView.paint.isAntiAlias = true
                            Log.d("calendar", "else_____________")

                        }
                    }
                }


                if (day.owner == DayOwner.THIS_MONTH) {
                    container.textView.visibility = View.VISIBLE

                    when {
                        day.date.isBefore(today)->{
                            container.textView.setTextColor(resources.getColor(R.color.example_4_grey_past))
                        }

                        selectedDate == day.date -> {
                            container.textView.setTextColor(Color.WHITE)
                            container.textView.setBackgroundResource(R.drawable.selection_background)
                        }
                        today == day.date -> {
                            container.textView.setTextColor(Color.BLACK)
                            container.textView.setBackgroundResource(R.drawable.today_background)
                        }
                        else -> {
                            container.textView.setTextColor(Color.BLACK)
                            container.textView.background = null
                        }
                    }
                } else {
                    container.textView.visibility = View.INVISIBLE
                }

            }
        }

        binding.datePickerCalendarView.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)

            @SuppressLint("SetTextI18n")
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.textView.text = "${
                    month.yearMonth.month.name.lowercase(Locale.getDefault())
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                } ${month.year}"
            }
        }


        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        binding.datePickerCalendarView.setup(currentMonth, lastMonth, firstDayOfWeek)
        binding.datePickerCalendarView.scrollToMonth(currentMonth)


        binding.dateSelected.setOnClickListener {

            val result = selectedDate?.toEpochDay()
            Log.d("datePicker", "result=$result")

            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
            dismiss()
            selectedDate = null
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}