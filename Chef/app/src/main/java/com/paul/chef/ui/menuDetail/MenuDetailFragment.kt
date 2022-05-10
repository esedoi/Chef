package com.paul.chef.ui.menuDetail


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.*
import com.paul.chef.data.Dish
import com.paul.chef.data.Menu
import com.paul.chef.data.Review
import com.paul.chef.databinding.*

class MenuDetailFragment : Fragment(), Block {

    private var _binding: FragmentMenuDetailBinding? = null
    private val binding get() = _binding!!

    private var _itemDisplayBinding: ItemDisplayDishBinding? = null
    private val itemDisplayBinding get() = _itemDisplayBinding!!

    private lateinit var imageAdapter: DetailImagesAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private lateinit var reviewAdapter: ReviewAdapter
    private var reviewLayoutManager: RecyclerView.LayoutManager? = null

    private var reviewList = emptyList<Review>()

    lateinit var menuDetailViewModel: MenuDetailViewModel
    lateinit var menu: Menu

    var openBoolean: Boolean = false

    //safe args
    private val arg: MenuDetailFragmentArgs by navArgs()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("menudetailfragment", "onattach")
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentMenuDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        menuDetailViewModel =
            ViewModelProvider(this)[MenuDetailViewModel::class.java]


        //navigation safe args
        menu = arg.menu
        val dishList = menu.dishes
        var defaultType = -1
        val or = "or"
        val and = "and"
        val displayList = mutableListOf<ItemDisplayDishBinding>()
        val selectedDish = mutableListOf<Dish>()

        menuDetailViewModel.getReview(menu.id)
        val outlineProvider = ProfileOutlineProvider()
        binding.imageView5.outlineProvider = outlineProvider
        bindImage(binding.imageView5, menu.chefAvatar)

        binding.detailChefName.text = menu.chefName + " 建立的菜單"
        if (menu.reviewRating != null) {
            binding.menuDetailRatingNum.visibility = View.VISIBLE
            binding.ratingBar4.visibility = View.VISIBLE
            binding.menuDetailRatingNum.text = menu.reviewNumber.toString() + " 則評價"
            binding.ratingBar4.rating = menu.reviewRating!!
            val str: String = String.format("%.1f", menu.reviewRating)
            binding.menuDetailRatingTxt.text = str
            binding.menuDetailReviewTitle.visibility = View.VISIBLE
            binding.menuDetailMoreReviewBtn.visibility = View.VISIBLE
            binding.menuDetailRatingTxt.visibility = View.VISIBLE
        } else {
            binding.menuDetailRatingTxt.visibility = View.GONE
            binding.menuDetailRatingNum.visibility = View.GONE
            binding.ratingBar4.visibility = View.GONE
            binding.menuDetailReviewTitle.visibility = View.GONE
            binding.menuDetailMoreReviewBtn.visibility = View.GONE
        }
        binding.menuDetailReviewTitle.text = menu.reviewNumber.toString() + "  則評價"


        //imagesRecyclerView
        imageAdapter = DetailImagesAdapter(ImgRecyclerType.IMAGE.index, null)
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.imagesRecycler.layoutManager = layoutManager
        binding.imagesRecycler.adapter = imageAdapter
        imageAdapter.submitList(menu.images)


        reviewAdapter = ReviewAdapter(this)
        reviewLayoutManager = LinearLayoutManager(this.context)
        binding.menuDetailReviewRecycler.layoutManager = reviewLayoutManager
        binding.menuDetailReviewRecycler.adapter = reviewAdapter

        menuDetailViewModel.reviewList.observe(viewLifecycleOwner) { review ->


            reviewList = if (UserManger.user?.blockReviewList != null) {
                review.filter {
                    !UserManger.user?.blockReviewList!!.contains(it.userId)
                }
            } else {
                review
            }
            val filterList = reviewList.filterIndexed { index, review ->
                index < 2
            }
            reviewAdapter.submitList(filterList)
            reviewAdapter.notifyDataSetChanged()
        }

        binding.menuDetailBlockMenuBtn.setOnClickListener {

            val alertDialog=AlertDialog.Builder(this.context)
            alertDialog.setTitle("封鎖此菜單？")
                .setMessage("${menu.menuName}")
                .setPositiveButton("確認"){_,_->
                    blockMenu(menu.id)
                }
                .setNegativeButton("取消"){dialog,_->
                  dialog.dismiss()
                }
                .show()
        }


        for (i in dishList) {
            _itemDisplayBinding =
                ItemDisplayDishBinding.inflate(LayoutInflater.from(context), container, false)

            if (i.option == 0) {
                if (i.typeNumber != defaultType) {
                    //setType
                    itemDisplayBinding.displayTitle.text = i.type
                    defaultType = i.typeNumber
                    binding.displayDishLinear.addView(itemDisplayBinding.root)
                    displayList.add(itemDisplayBinding)
                } else {
                    //+and
                    val andText = TextView(this.context)
                    andText.text = and
                    andText.gravity = Gravity.CENTER
                    andText.setTextAppearance(android.R.style.TextAppearance_Material_Body2)
                    andText.setTextColor(resources.getColor(R.color.teal_200))
                    andText.setTypeface(null, Typeface.BOLD_ITALIC);
                    displayList[defaultType].displayRG.addView(andText)
                }
                // +textview
                val nameText = TextView(this.context)
                nameText.text = i.name
                nameText.gravity = Gravity.CENTER
                nameText.setTextAppearance(android.R.style.TextAppearance_Material_Medium)
                displayList[defaultType].displayRG.addView(nameText)
                displayList[defaultType].displayRG.gravity = Gravity.CENTER

            } else {
                if (i.typeNumber != defaultType) {
                    itemDisplayBinding.displayTitle.text = i.type
                    defaultType = i.typeNumber
                    binding.displayDishLinear.addView(itemDisplayBinding.root)
                    displayList.add(itemDisplayBinding)

                } else {
                    //+or
                    val orText = TextView(this.context)
                    orText.text = or
                    orText.setTextAppearance(android.R.style.TextAppearance_Material_Body2)
                    orText.setTextColor(resources.getColor(R.color.teal_200))
                    orText.setTypeface(null, Typeface.BOLD_ITALIC)
                    displayList[defaultType].displayRG.addView(orText)
                }
                //+radiobutton  +price

//                val radioText = "${i.name}    <font color = \"#03A9F4\">(${i.extraPrice})</font>"
                val radioText = if (i.extraPrice != 0) {
                    "${i.name}    <I><font color = \"#03DAC5\">(+NT$ ${i.extraPrice})</font></I>"
                } else {
                    "${i.name}"
                }

                val nameRadioBtn = RadioButton(this.context)
                nameRadioBtn.text = Html.fromHtml(radioText)
                nameRadioBtn.id = View.generateViewId()
                nameRadioBtn.tag = i
                nameRadioBtn.setTextAppearance(android.R.style.TextAppearance_Material_Medium)
                displayList[defaultType].displayRG.addView(nameRadioBtn)
                displayList[defaultType].displayRG.gravity = Gravity.CENTER
            }
        }


        binding.menuDetailPerPrice.text = "NT$" + menu.perPrice.toString()

        binding.detailName.text = menu.menuName
        binding.detailMenuIntro.text = menu.intro
        binding.menuDetailMoreReviewBtn.setOnClickListener {

            findNavController().navigate(
                MobileNavigationDirections.actionGlobalReviewPage(
                    reviewList.toTypedArray()
                )
            )
        }

        menuDetailViewModel.checkOpen(menu.chefId)
        menuDetailViewModel.openBoolean.observe(viewLifecycleOwner){
            if(!it){
                binding.choice.isEnabled = false
                binding.choice.text = "不開放"
            }else{
                binding.choice.isEnabled = true
                binding.choice.text = "預訂"
            }
        }


        binding.choice.setOnClickListener {

            if (menu.chefId != UserManger.user?.chefId ?: "") {
                var isRadioSelected = true
                var typeInt = -1

                for (i in dishList) {
                    if (i.option == 0) {
                        selectedDish.add(i)
                        typeInt = i.typeNumber
                    } else {
                        if (typeInt != i.typeNumber) {
                            val selectedId =
                                displayList[i.typeNumber].displayRG.checkedRadioButtonId
                            if (selectedId != -1) {
                                val radioButton = root.findViewById<RadioButton>(selectedId)
                                val radioDish: Dish = radioButton.tag as Dish
                                selectedDish.add(radioDish)
                                typeInt = i.typeNumber
                            } else {
                                isRadioSelected = false
                            }
                        }
                    }
                }

                if (isRadioSelected) {

                    Log.d("menudetailfragment", "selectedDish=${selectedDish}")
                    val list = selectedDish.toTypedArray()
                    findNavController().navigate(
                        MobileNavigationDirections.actionGlobalBookFragment(
                            menu,
                            list
                        )
                    )
                } else {
                    Toast.makeText(this.context, "請選擇菜品", Toast.LENGTH_SHORT).show()
                    selectedDish.clear()
                }
            } else {
                Toast.makeText(this.context, "無法預訂自己的菜單", Toast.LENGTH_SHORT).show()
            }

        }





        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun blockReview(blockUserId: String) {

        val blockReviewList = mutableListOf<String>()
        if (UserManger.user?.blockReviewList != null) {
            blockReviewList.addAll(UserManger.user?.blockMenuList!!)
        }
        blockReviewList.add(blockUserId)
        (activity as MainActivity).block(UserManger.user?.userId!!, null, blockReviewList)
        UserManger.user?.blockReviewList = blockReviewList
        menuDetailViewModel.getReview(menu.id)

    }

    override fun blockMenu(blockMenuId: String) {

        val blockMenuList = mutableListOf<String>()
        if (UserManger.user?.blockMenuList != null) {
            blockMenuList.addAll(UserManger.user?.blockMenuList!!)
        }
        blockMenuList.add(blockMenuId)
        (activity as MainActivity).block(UserManger.user?.userId!!, blockMenuList, null)
        UserManger.user?.blockMenuList = blockMenuList
        findNavController().navigateUp()

    }

}
