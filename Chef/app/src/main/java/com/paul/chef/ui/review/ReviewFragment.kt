package com.paul.chef.ui.review

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.R
import com.paul.chef.databinding.BottomSheetReviewBinding
import com.paul.chef.ui.orderDetail.OrderDetailFragmentArgs
import com.paul.chef.ui.orderDetail.OrderDetailViewModel


class ReviewFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetReviewBinding? = null
    private val binding get() = _binding!!

    private val arg: ReviewFragmentArgs by navArgs()

    private lateinit var reviewViewModel: ReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetReviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        reviewViewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)

        val order = arg.order
        val rating = arg.rating

        binding.apply {
            reviewMenuName.text = order.chefName
            reviewRatingbar.rating = rating.toFloat()
            reviewMenuName.text = order.menuName
            reviewOrderDate.text = order.date.toString()
            reviewConfirmBtn.setOnClickListener {
                val reviewTxt = reviewEdittext.text.toString()
                val newRating = reviewRatingbar.rating

           reviewViewModel.rating(reviewTxt, newRating, order)
                reviewEdittext.setText("")
                findNavController().navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())
            }
            reviewClose.setOnClickListener {
                dismiss()
            }
        }

        return  root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}