package com.paul.chef.ui.chef

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.*
import com.paul.chef.data.Review
import com.paul.chef.databinding.FragmentChefPageBinding
import com.paul.chef.ui.menu.MenuListAdapter
import com.paul.chef.ui.menuDetail.ReviewAdapter
import com.paul.chef.ui.menuDetail.bindImage

class ChefFragment : Fragment() {

    private var _binding: FragmentChefPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var reviewAdapter: ReviewAdapter
    private var reviewLayoutManager: RecyclerView.LayoutManager? = null

    private lateinit var menuListAdapter: MenuListAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private val db = FirebaseFirestore.getInstance()
    private var reviewList = emptyList<Review>()

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val chefViewModel =
            ViewModelProvider(this).get(ChefViewModel::class.java)


        _binding = FragmentChefPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mode = this.context?.let { UserManger.readData("mode", it) }
        Log.d("cheffragment", "mode=$mode")


        binding.chefPageLogout.setOnClickListener {
            (activity as MainActivity).signOut()
            findNavController().navigate(MobileNavigationDirections.actionGlobalNavigationHome())
        }

        if(mode ==Mode.USER.index){
            binding.turnToUser.text = "切換成廚師模式"
            binding.turnToUser.setOnClickListener {
                (activity as MainActivity).turnMode(Mode.CHEF.index)
                findNavController().navigate(MobileNavigationDirections.actionGlobalOrderManageFragment())
            }
        }else{
            binding.turnToUser.text = "切換成客人模式"
            binding.turnToUser.setOnClickListener {
                (activity as MainActivity).turnMode(Mode.USER.index)
                findNavController().navigate(MobileNavigationDirections.actionGlobalMenuFragment())
            }
        }


        chefViewModel.chefInfo.observe(viewLifecycleOwner){
            Log.d("CHEFFRAGMENT","接收到資料")
            binding.chefName.text = it.profileInfo.name
            binding.chefIntro.text = it.profileInfo.introduce
            if(it.reviewNumber!=null){
                binding.chefPageReviewDown.text = it.reviewNumber.toString()+" 則評價"
            }else{
                binding.chefPageReviewDown.text = "0 則評價"
            }
            bindImage(binding.chefPageImgView, it.profileInfo.avatar)
            val outlineProvider = ProfileOutlineProvider()
            binding.chefPageImgView.outlineProvider = outlineProvider


            bindImage( binding.chefPageImgView, it.profileInfo.avatar)

        }



        binding.createMenu.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalMenuEditFragment(null))
        }

        binding.editProfileBtn.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalChefEditFragment(EditPageType.EDIT_PROFILE.index, UserManger.user.profileInfo!!))
        }

        binding.bookSettingBtn.setOnClickListener {
            findNavController().navigate(MobileNavigationDirections.actionGlobalBookSetting())
        }



        //menuList recycler
        menuListAdapter = MenuListAdapter(null, null,type=MenuType.SIMPLE.index)
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.chefPageMenuRecycler.layoutManager = layoutManager
        binding.chefPageMenuRecycler.adapter = menuListAdapter

        chefViewModel.liveMenu.observe(viewLifecycleOwner){
            menuListAdapter.submitList(it)
        }


        reviewAdapter = ReviewAdapter()
        reviewLayoutManager = LinearLayoutManager(this.context)
        binding.chefPageReviewRecycler.layoutManager = reviewLayoutManager
        binding.chefPageReviewRecycler.adapter = reviewAdapter
        chefViewModel.reviewList.observe(viewLifecycleOwner){
            reviewList = it

            val filterList = it.filterIndexed { index, review ->
                index<2
            }
            reviewAdapter.submitList(filterList)
            reviewAdapter.notifyDataSetChanged()
        }



            binding.chefPageReviewMore.setOnClickListener {
                if(reviewList.isNotEmpty()){
                    val arrayList = reviewList.toTypedArray()
                    findNavController().navigate(MobileNavigationDirections.actionGlobalReviewPage(arrayList))
                }
            }




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
