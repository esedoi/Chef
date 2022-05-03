package com.paul.chef.ui.reviewPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.MainActivity
import com.paul.chef.MainViewModel
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.Mode
import com.paul.chef.databinding.FragmentHomeBinding
import com.paul.chef.databinding.FragmentReviewPageBinding
import com.paul.chef.ui.bottomSheetPicker.PickerBottomSheetArgs
import com.paul.chef.ui.home.HomeViewModel
import com.paul.chef.ui.menuDetail.ReviewAdapter

class ReviewPage: Fragment() {

    private var _binding: FragmentReviewPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var reviewAdapter: ReviewAdapter
    private var reviewLayoutManager: RecyclerView.LayoutManager? = null

    private val arg: ReviewPageArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentReviewPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        reviewAdapter = ReviewAdapter()
        reviewLayoutManager = LinearLayoutManager(this.context)
        binding.reviewPageRecycler.layoutManager = reviewLayoutManager
        binding.reviewPageRecycler.adapter = reviewAdapter
//        menuDetailViewModel.reviewList.observe(viewLifecycleOwner){
//            reviewAdapter.submitList(it)
//        }

        val reviewList = arg.review.toList()
        Log.d("pagefragment", "reviewList=$reviewList")
        reviewAdapter.submitList(reviewList)




        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}
