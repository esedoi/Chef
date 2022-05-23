package com.paul.chef.ui.addTag


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.chip.Chip
import com.paul.chef.R
import com.paul.chef.databinding.FragmentAddTagBinding

class AddTagFragment : DialogFragment() {

    private var _binding: FragmentAddTagBinding? = null
    private val binding get() = _binding!!


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


        val tagList = resources.getStringArray(R.array.tag_list).toList()

        for (i in tagList) {
            addTag(i)
        }
        binding.addTagAddTag.setOnClickListener {

            if (binding.addTagEditTxt.editText?.text.toString() != "") {
                val text = binding.addTagEditTxt.editText?.text.toString()
                addTag(text)
                binding.addTagEditTxt.editText?.setText("")
            }
        }

        binding.addTagSelect.setOnClickListener {
            val chipIdList = binding.bookChipGroup.checkedChipIds
            val chipTxtList = mutableListOf<String>()
            chipIdList.forEach {
                val text = binding.bookChipGroup.findViewById<Chip>(it).text
                chipTxtList.add(text.toString())
            }
            val arrayList: ArrayList<String> = chipTxtList as ArrayList
            setFragmentResult(
                "tagList",
                bundleOf("tagList" to arrayList, "filterTagList" to arrayList)
            )
            chipIdList.clear()
            chipTxtList.clear()
            dismiss()
        }

        return root
    }

    private fun addTag(text: String) {
        val chip = Chip(this.context)
        chip.text = text
        chip.isCheckable = true
        chip.isCheckedIconVisible = true
        binding.bookChipGroup.addView(chip)
    }

}