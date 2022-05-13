package com.paul.chef.ui.addTag

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.paul.chef.MainActivity
import com.paul.chef.R
import com.paul.chef.databinding.FragmentAddTagBinding
import com.paul.chef.databinding.FragmentAddressListBinding

class AddTagFragment : DialogFragment() {

    private var _binding: FragmentAddTagBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddTagViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddTagBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val tagList = listOf<String>("素食","清真","魚","法式", "中式","日式","分子料理","有機", "無麩質", "蛋奶素", "泰式", "傳統", "小吃", "美式", "創意料理", "義大利", "道地風味")

        for(i in tagList){
            addTag(i)
        }
        binding.addTagAddTag.setOnClickListener {

            if(binding.addTagEdittxt.editText?.text.toString()!=""){
                val text = binding.addTagEdittxt.editText?.text.toString()
                addTag(text)
                binding.addTagEdittxt.editText?.setText("")
            }else{
                Log.d("addtagfragment", "empty")
            }

        }

        binding.addTagSelect.setOnClickListener {
            val chipIdList = binding.bookChipGroup.checkedChipIds
            val chipTxtList = mutableListOf<String>()
            chipIdList.forEach {
                val text = binding.bookChipGroup.findViewById<Chip>(it).text
                chipTxtList.add(text.toString())
            }
            val arrayList:ArrayList<String> = chipTxtList as ArrayList
            setFragmentResult("tagList", bundleOf("tagList" to arrayList, "filterTagList" to arrayList))
            chipIdList.clear()
            chipTxtList.clear()
            dismiss()
        }

        return root
    }

    private fun addTag(text:String){
        val chip = Chip(this.context)
        chip.text = text
        chip.isCheckable = true
        chip.isCheckedIconVisible = true
        binding.bookChipGroup.addView(chip)
    }

}