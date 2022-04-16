package com.paul.chef.ui.menuDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.data.DisplayDish
import com.paul.chef.databinding.*
import com.paul.chef.ui.menuEdit.DiscountAdapter

class MenuDetailFragment : Fragment() {

    private var _binding: FragmentMenuDetailBinding? = null
    private val binding get() = _binding!!

//    private var _fixedBinding: ItemDisplayFixedDishBinding? = null
//    private val fixedBinding get() = _fixedBinding!!
//
//    private var _optionalBinding: ItemDisplayOptionalDishBinding? = null
//    private val optionalBinding get() = _optionalBinding!!

    private var _itemDisplayBinding: ItemDisplayDishBinding? = null
    private val itemDisplayBinding get() = _itemDisplayBinding!!


    var bindingList = mutableListOf<ItemDisplayFixedDishBinding>()
    var allDishBindingList = mutableListOf<ItemDisplayOptionalDishBinding>()

    //safe args
    private val arg: MenuDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentMenuDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //navigation safe args
        val menu = arg.chefMenu
        val dishList = menu.dishes
        var defaultType = -1
        val or = "or"
        val and = "and"
        val displayList = mutableListOf<ItemDisplayDishBinding>()


        for (i in dishList) {
            _itemDisplayBinding =
                ItemDisplayDishBinding.inflate(LayoutInflater.from(context), container, false)

            if (i.option == 0) {
                if (i.typeNumber != defaultType) {
                    itemDisplayBinding.displayTitle.text = i.type
                    defaultType = i.typeNumber
                    binding.displayDishLinear.addView(itemDisplayBinding.root)
                    displayList.add(itemDisplayBinding)
                } else {
                    //+and
                    val andText = TextView(this.context)
                    andText.text = and
                    andText.setTextAppearance(android.R.style.TextAppearance_Material_Body2)

                    displayList[defaultType].displayRG.addView(andText)


                }

                // +textview
                val nameText = TextView(this.context)
                nameText.text = i.name

                displayList[defaultType].displayRG.addView(nameText)
                Log.d("menudetail", "defaultype= ${defaultType}")
                Log.d("menudetail", "displatlist= ${displayList}")



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
//                    itemDisplayBinding.displayRG.addView(orText)
                    displayList[defaultType].displayRG.addView(orText)
                }
                //+radiobutton  +price
                val radioText = "${i.name}    <font color = \"#03A9F4\">(${i.extraPrice})</font>"
                val nameRadioBtn = RadioButton(this.context)
                nameRadioBtn.text = Html.fromHtml(radioText)
//                itemDisplayBinding.displayRG.addView(nameRadioBtn)
                displayList[defaultType].displayRG.addView(nameRadioBtn)

            }
        }



        binding.detailName.text = menu.menuName


        binding.choice.setOnClickListener {
            Log.d("menudetailfragment", "menu=$menu")
            findNavController().navigate(MobileNavigationDirections.actionGlobalBookFragment(menu))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
