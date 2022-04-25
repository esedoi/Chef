package com.paul.chef.ui.menuDetail


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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.data.Dish
import com.paul.chef.databinding.*

class MenuDetailFragment : Fragment() {

    private var _binding: FragmentMenuDetailBinding? = null
    private val binding get() = _binding!!

    private var _itemDisplayBinding: ItemDisplayDishBinding? = null
    private val itemDisplayBinding get() = _itemDisplayBinding!!

    private lateinit var imageAdapter: DetailImagesAdapter
    private var layoutManager: RecyclerView.LayoutManager? = null

    private lateinit var reviewAdapter: ReviewAdapter
    private var reviewLayoutManager: RecyclerView.LayoutManager? = null

    //safe args
    private val arg: MenuDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentMenuDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val menuDetailViewModel =
            ViewModelProvider(this)[MenuDetailViewModel::class.java]


        //navigation safe args
        val menu = arg.chefMenu
        val dishList = menu.dishes
        var defaultType = -1
        val or = "or"
        val and = "and"
        val displayList = mutableListOf<ItemDisplayDishBinding>()
        val selectedDish = mutableListOf<Dish>()

        menuDetailViewModel.getReview(menu.id)


        //imagesRecyclerView
        imageAdapter = DetailImagesAdapter()
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.imagesRecycler.layoutManager = layoutManager
        binding.imagesRecycler.adapter = imageAdapter
        imageAdapter.submitList(menu.images)


        reviewAdapter = ReviewAdapter()
        reviewLayoutManager = LinearLayoutManager(this.context)
        binding.menuDetailReviewRecycler.layoutManager = reviewLayoutManager
        binding.menuDetailReviewRecycler.adapter = reviewAdapter
        menuDetailViewModel.reviewList.observe(viewLifecycleOwner){
            reviewAdapter.submitList(it)
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
                    displayList[defaultType].displayRG.addView(andText)
                }
                // +textview
                val nameText = TextView(this.context)
                nameText.text = i.name
                nameText.gravity = Gravity.CENTER
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
                    displayList[defaultType].displayRG.addView(orText)
                }
                //+radiobutton  +price

//                val radioText = "${i.name}    <font color = \"#03A9F4\">(${i.extraPrice})</font>"
                val radioText = if (i.extraPrice != 0) {
                    "${i.name}    <font color = \"#03A9F4\">(+NT$ ${i.extraPrice})</font>"
                } else {
                    "${i.name}"
                }

                val nameRadioBtn = RadioButton(this.context)
                nameRadioBtn.text = Html.fromHtml(radioText)
                nameRadioBtn.id = View.generateViewId()
                nameRadioBtn.tag = i
                displayList[defaultType].displayRG.addView(nameRadioBtn)
                displayList[defaultType].displayRG.gravity = Gravity.CENTER
            }
        }



        binding.detailName.text = menu.menuName
        binding.detailMenuIntro.text = menu.intro

        binding.choice.setOnClickListener {

            var isRadioSelected = true
            var typeInt = -1


            for (i in dishList) {
                if (i.option == 0) {
                    selectedDish.add(i)
                    typeInt = i.typeNumber
                } else {
                    if (typeInt != i.typeNumber) {
                        val selectedId = displayList[i.typeNumber].displayRG.checkedRadioButtonId
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

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
