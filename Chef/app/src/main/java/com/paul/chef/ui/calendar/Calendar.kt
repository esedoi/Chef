package com.paul.chef.ui.calendar

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.paul.chef.BookSettingType
import com.paul.chef.CalendarType
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.R
import com.paul.chef.data.BookSetting
import com.paul.chef.data.DateStatus
import com.paul.chef.data.SelectedDates
import com.paul.chef.databinding.CalendarDayLayoutBinding
import com.paul.chef.databinding.CalendarMonthHeaderLayoutBinding
import com.paul.chef.databinding.FragmentCalendarBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

class Calendar : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val selectedDates = mutableSetOf<LocalDate>()
    private val bookDates = mutableSetOf<LocalDate>()
    var bookSetting: BookSetting? = null

    private val today = LocalDate.now()

    var type: Int = -1
    var calendarDefault: Int = -1
    var dateList = mutableListOf<DateStatus>()


    val orderList = mutableListOf<LocalDate>()

    @SuppressLint("SimpleDateFormat", "WeekBasedYear")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.d("calendarfragment", "today=$today")


        val calendarViewModel =
            ViewModelProvider(this).get(CalendarViewModel::class.java)

        calendarViewModel.orderList.observe(viewLifecycleOwner) {
            orderList.addAll(it)
            Log.d("calendar", "datelist live = $orderList")
            binding.calendarView.notifyCalendarChanged()
        }

        calendarViewModel.bookSetting.observe(viewLifecycleOwner) {
            calendarDefault = it.calendarDefault
            type = it.type
            binding.calendarView.notifyCalendarChanged()
        }

        calendarViewModel.dateSetting.observe(viewLifecycleOwner){
            dateList.addAll(it)
            binding.calendarView.notifyCalendarChanged()
        }


        binding.editBtn.setOnClickListener {
            val list = SelectedDates(selectedDates.toList())
            findNavController().navigate(MobileNavigationDirections.actionGlobalCalendarSetting(list))
            selectedDates.clear()
        }



        class DayViewContainer(view: View) : ViewContainer(view) {
            // With ViewBinding
            val textView = CalendarDayLayoutBinding.bind(view).calendarDayText

            // Will be set when this container is bound
            lateinit var day: CalendarDay


            init {

                view.setOnClickListener {
                    // Use the CalendarDay associated with this container.
                    Log.d("dayviewcontainer", "day is $day")

                    if (day.owner == DayOwner.THIS_MONTH && (day.date == today || day.date.isAfter(
                            today
                        ))
                    ) {
                        if (selectedDates.contains(day.date)) {
                            selectedDates.remove(day.date)
                        } else {
                            selectedDates.add(day.date)
                        }
                        binding.calendarView.notifyDayChanged(day)
                        if (selectedDates.size > 0) {
                            binding.editBtn.visibility = View.VISIBLE
                        } else {
                            binding.editBtn.visibility = View.GONE
                        }
                    }
                }
            }

        }


        class MonthViewContainer(view: View) : ViewContainer(view) {

            val textView = CalendarMonthHeaderLayoutBinding.bind(view).headerTextView
        }


        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
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
//
                        day.date.isBefore(today) -> {
                            container.textView.setTextColor(resources.getColor(R.color.example_4_grey_past))
                            container.textView.paint.flags =
                                Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
                        }

                        selectedDates.contains(day.date) -> {
                            container.textView.setTextColor(Color.WHITE)
                            container.textView.setBackgroundResource(R.drawable.selection_background)

                        }
                        today == day.date -> {
                            container.textView.setTextColor(Color.BLACK)
                            container.textView.setBackgroundResource(R.drawable.today_background)
                        }
                        orderList.contains(day.date) -> {
                            Log.d("calendar", "dateList=$orderList")
                            container.textView.setTextColor(Color.BLACK)
                            container.textView.setBackgroundResource(R.drawable.booked_day_background)
                        }

                        else -> {
                            container.textView.setTextColor(Color.BLACK)
                            container.textView.background = null
                        }
                    }
                } else {
                    container.textView.visibility = View.INVISIBLE
                }
                ////////

            }
        }

        binding.calendarView.monthHeaderBinder = object :
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
        binding.calendarView.setup(currentMonth, lastMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)




        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
