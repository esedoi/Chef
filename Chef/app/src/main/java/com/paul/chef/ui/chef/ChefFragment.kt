package com.paul.chef.ui.chef

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.*
import com.paul.chef.data.Menu
import com.paul.chef.data.Review
import com.paul.chef.databinding.FragmentChefPageBinding
import com.paul.chef.ext.getVmFactory
import com.paul.chef.ui.menu.MenuListAdapter
import com.paul.chef.ui.menuDetail.ReviewAdapter
import com.paul.chef.ui.menuDetail.bindImage

class ChefFragment : Fragment(), Block, ItemMenu {

    private var _binding: FragmentChefPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var reviewAdapter: ReviewAdapter
    private var reviewLayoutManager: RecyclerView.LayoutManager? = null

    private lateinit var menuListAdapter: MenuListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var reviewList = emptyList<Review>()

    private val chefViewModel by viewModels<ChefViewModel> { getVmFactory() }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChefPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val chefId = UserManger.chef?.id!!
        chefViewModel.getChef(chefId, false)

        chefViewModel.chefInfo.observe(viewLifecycleOwner) {
            binding.chefName.text = it.profileInfo.name
            binding.chefIntro.text = it.profileInfo.introduce
            setReviewNumber(it.reviewNumber)
            val outlineProvider = ProfileOutlineProvider()
            binding.chefPageImgView.outlineProvider = outlineProvider
            bindImage(binding.chefPageImgView, it.profileInfo.avatar)
        }

        // menuList recycler
        menuListAdapter = MenuListAdapter(this, null, type = MenuType.SIMPLE.index)
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.chefPageMenuRecycler.layoutManager = layoutManager
        binding.chefPageMenuRecycler.adapter = menuListAdapter

        chefViewModel.liveMenu.observe(viewLifecycleOwner) {
            menuListAdapter.submitList(it)
            menuListAdapter.notifyDataSetChanged()
        }

        reviewAdapter = ReviewAdapter(this)
        reviewLayoutManager = LinearLayoutManager(this.context)
        binding.chefPageReviewRecycler.layoutManager = reviewLayoutManager
        binding.chefPageReviewRecycler.adapter = reviewAdapter
        chefViewModel.reviewList.observe(viewLifecycleOwner) {
            reviewList = filterBlockReview(it)

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

        binding.createMenu.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalMenuEditFragment(null)
            )
        }

        binding.editProfileBtn.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalChefEditFragment(
                    EditPageType.EDIT_PROFILE.index,
                    UserManger.user?.profileInfo!!
                )
            )
        }

        binding.bookSettingBtn.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalBookSetting())
        }

        binding.chefPageAddressListBtn.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalAddressListFragment(
                    AddressListType.NORMAL.index
                )
            )
        }

        binding.chefPageLogout.setOnClickListener {
            (activity as MainActivity).signOut()
            findNavController().navigate(MobileNavigationDirections.actionGlobalLoginFragment())
        }

        binding.turnToUser.text = getString(R.string.turn_to_customer_mode)
        binding.turnToUser.setOnClickListener {
            (activity as MainActivity).turnMode(Mode.USER.index)
            findNavController().navigate(MobileNavigationDirections.actionGlobalMenuFragment())
        }

        return root
    }

    private fun filterBlockReview(reviewList: List<Review>): List<Review> {
        return if (UserManger.user?.blockReviewList != null) {
            reviewList.filter {
                !UserManger.user?.blockReviewList!!.contains(it.userId)
            }
        } else {
            reviewList
        }
    }

    private fun setReviewNumber(reviewNumber: Int?) {
        if (reviewNumber != null) {
            binding.chefPageReviewDown.text = getString(R.string.number_of_review, reviewNumber)
        } else {
            binding.chefPageReviewDown.text = getString(R.string.number_of_review, 0)
        }
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

    override fun goDetail(menu: Menu) {
        findNavController().navigate(
            MobileNavigationDirections.actionGlobalMenuDetailFragment(menu)
        )
    }

    override fun like(menuId: String) {
        TODO("Not yet implemented")
    }
}
