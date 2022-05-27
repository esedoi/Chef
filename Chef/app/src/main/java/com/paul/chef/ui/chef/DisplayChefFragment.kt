package com.paul.chef.ui.chef

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.*
import com.paul.chef.data.Review
import com.paul.chef.databinding.FragmentChefPageBinding
import com.paul.chef.ext.getVmFactory
import com.paul.chef.ui.menu.MenuListAdapter
import com.paul.chef.ui.menuDetail.ReviewAdapter
import com.paul.chef.ui.menuDetail.bindImage

class DisplayChefFragment : Fragment(), Block {

    private var _binding: FragmentChefPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var reviewAdapter: ReviewAdapter
    private var reviewLayoutManager: RecyclerView.LayoutManager? = null

    private lateinit var menuListAdapter: MenuListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var reviewList = emptyList<Review>()

    private val chefViewModel by viewModels<ChefViewModel> { getVmFactory() }

    private val arg: DisplayChefFragmentArgs by navArgs()

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChefPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val displayChefId = arg.chefId

        binding.editProfileBtn.visibility = View.GONE
        binding.createMenu.visibility = View.GONE
        binding.bookSettingBtn.visibility = View.GONE
        binding.chefPageAddressListBtn.visibility = View.GONE
        binding.turnToUser.visibility = View.GONE
        binding.chefPageLogout.visibility = View.GONE
        binding.divider16.visibility = View.GONE
        binding.divider11.visibility = View.GONE
        binding.chefPageSetTxt.visibility = View.GONE
        chefViewModel.getChef(displayChefId)

        chefViewModel.chefInfo.observe(viewLifecycleOwner) {
            binding.chefName.text = it.profileInfo.name
            binding.chefIntro.text = it.profileInfo.introduce
            if (it.reviewNumber != null) {
                binding.chefPageReviewDown.text = getString(
                    R.string.number_of_review,
                    it.reviewNumber
                )
            } else {
                binding.chefPageReviewDown.text = getString(R.string.number_of_review, 0)
            }
            bindImage(binding.chefPageImgView, it.profileInfo.avatar)
            val outlineProvider = ProfileOutlineProvider()
            binding.chefPageImgView.outlineProvider = outlineProvider
            bindImage(binding.chefPageImgView, it.profileInfo.avatar)
        }

        // menuList recycler
        menuListAdapter = MenuListAdapter(null, null, type = MenuType.SIMPLE.index)
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.chefPageMenuRecycler.layoutManager = layoutManager
        binding.chefPageMenuRecycler.adapter = menuListAdapter

        chefViewModel.liveMenu.observe(viewLifecycleOwner) {
            menuListAdapter.submitList(it)
        }

        reviewAdapter = ReviewAdapter(this)
        reviewLayoutManager = LinearLayoutManager(this.context)
        binding.chefPageReviewRecycler.layoutManager = reviewLayoutManager
        binding.chefPageReviewRecycler.adapter = reviewAdapter
        chefViewModel.reviewList.observe(viewLifecycleOwner) {
            reviewList = if (UserManger.user?.blockReviewList != null) {
                it.filter { review ->
                    !UserManger.user?.blockReviewList!!.contains(review.userId)
                }
            } else {
                it
            }

            val filterList = reviewList.filterIndexed { index, _ ->
                index < 2
            }
            reviewAdapter.submitList(filterList)
            reviewAdapter.notifyDataSetChanged()
        }

        binding.chefPageReviewMore.setOnClickListener {
            if (reviewList.isNotEmpty()) {
                val arrayList = reviewList.toTypedArray()
                findNavController().navigate(
                    MobileNavigationDirections.actionGlobalReviewPage(arrayList)
                )
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun blockReview(userId: String) {
        val blockReviewList = mutableListOf<String>()
        if (UserManger.user?.blockReviewList != null) {
            blockReviewList.addAll(UserManger.user?.blockMenuList!!)
        }
        blockReviewList.add(userId)
        (activity as MainActivity).block(UserManger.user?.userId!!, null, blockReviewList)
        UserManger.user?.blockReviewList = blockReviewList
    }

    override fun blockMenu(menuId: String) {
        TODO("Not yet implemented")
    }
}
