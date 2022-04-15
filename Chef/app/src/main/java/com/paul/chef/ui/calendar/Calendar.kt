package com.paul.chef.ui.calendar

import android.annotation.SuppressLint
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.R
import com.paul.chef.data.Order
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

    private val db = FirebaseFirestore.getInstance()

    private var selectedDate: LocalDate? = null
    private val selectedDates = mutableSetOf<LocalDate>()
    private val bookDates = mutableSetOf<LocalDate>()

    private val today = LocalDate.now()


    val orderList = mutableListOf<Order>()

    val dateList = mutableListOf<LocalDate>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.d("calendarfragment", "today=$today")

        db.collection("Order")
            .whereEqualTo("chefId", "9qKTEyvYbiXXEJSjDJGF")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("notification", "Listen failed.", e)
                    return@addSnapshotListener
                }
                Log.d("calendar", "接收到訂單資料")
                for (doc in value!!.documents) {
                    val item = doc.data
                    val json = Gson().toJson(item)
                    val data = Gson().fromJson(json, Order::class.java)
                    orderList.add(data)
                }
                for (i in orderList){
                    val sdf = SimpleDateFormat("YYYY-MM-dd")
                    val day = sdf.format(i.date)
                    val localDate:LocalDate = LocalDate.parse(day)
                    Log.d("calendar", "localday=${localDate}")
                    bookDates.add(localDate)
                    dateList.add(localDate)
                }
                binding.calendarView.notifyCalendarChanged()
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

                    if (day.owner == DayOwner.THIS_MONTH) {
                        if (selectedDates.contains(day.date)) {
                            selectedDates.remove(day.date)
                        } else {
                            selectedDates.add(day.date)
                        }
                        binding.calendarView.notifyDayChanged(day)
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
                if (day.owner == DayOwner.THIS_MONTH) {
                    container.textView.visibility = View.VISIBLE

                    when {
                        selectedDates.contains(day.date) -> {
                            container.textView.setTextColor(Color.WHITE)
                            container.textView.setBackgroundResource(R.drawable.selection_background)
                        }
                        today == day.date -> {
                            container.textView.setTextColor(Color.BLACK)
                            container.textView.setBackgroundResource(R.drawable.today_background)
                        }
//                        bookDates.contains(day.date) -> {
//                            Log.d("calendar", "dateList=$dateList")
//                            container.textView.setTextColor(Color.BLACK)
//                            container.textView.setBackgroundResource(R.drawable.booked_day_background)
//                        }
                        dateList.contains(day.date) -> {
                            Log.d("calendar", "dateList=$dateList")
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
        binding.calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)

        binding.edit.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalCalendarSetting())
        }


        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
