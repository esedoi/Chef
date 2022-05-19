package com.paul.chef.ui.review

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.ProfileOutlineProvider
import com.paul.chef.databinding.BottomSheetReviewBinding
import com.paul.chef.ext.getVmFactory
import com.paul.chef.ui.menuDetail.bindImage
import com.paul.chef.ui.userProfile.UserProfileViewModel


class ReviewFragment : DialogFragment() {

    private var _binding: BottomSheetReviewBinding? = null
    private val binding get() = _binding!!

    private val arg: ReviewFragmentArgs by navArgs()

    private val reviewViewModel by viewModels<ReviewViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetReviewBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val order = arg.order
        val rating = arg.rating

        binding.apply {
            reviewMenuName.text = order.menuName
            reviewRatingbar.rating = rating.toFloat()
            reviewMenuChef.text = "由 " + order.chefName + " 提供"
            reviewDate.text = order.date.toString()
            val outlineProvider = ProfileOutlineProvider()
            reviewChefAvatar.outlineProvider = outlineProvider
            bindImage(reviewChefAvatar, order.chefAvatar)
            reviewConfirmBtn.setOnClickListener {
                val reviewTxt = reviewDescription.editText?.text.toString()
                val newRating = reviewRatingbar.rating

                reviewViewModel.rating(reviewTxt, newRating, order)
                reviewDescription.editText?.setText("")
                findNavController().navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())
            }
            reviewClose.setOnClickListener {
                dismiss()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}