package com.paul.chef.ui.reviewPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.*
import com.paul.chef.databinding.FragmentReviewPageBinding
import com.paul.chef.ui.menuDetail.ReviewAdapter

class ReviewPageFragment : Fragment(), Block {

    private var _binding: FragmentReviewPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var reviewAdapter: ReviewAdapter
    private var reviewLayoutManager: RecyclerView.LayoutManager? = null

    private val arg: ReviewPageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        reviewAdapter = ReviewAdapter(this)
        reviewLayoutManager = LinearLayoutManager(this.context)
        binding.reviewPageRecycler.layoutManager = reviewLayoutManager
        binding.reviewPageRecycler.adapter = reviewAdapter

        val reviewList = arg.review.toList()
        reviewAdapter.submitList(reviewList)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun blockReview(userId: String) {
        TODO("Not yet implemented")
    }

    override fun blockMenu(menuId: String) {
        TODO("Not yet implemented")
    }
}
