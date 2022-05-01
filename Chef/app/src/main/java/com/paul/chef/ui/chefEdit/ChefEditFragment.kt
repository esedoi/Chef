package com.paul.chef.ui.chefEdit

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.FirebaseFirestore
import com.paul.chef.EditPageType
import com.paul.chef.MobileNavigationDirections
import com.paul.chef.ProfileOutlineProvider
import com.paul.chef.UserManger
import com.paul.chef.data.ProfileInfo
import com.paul.chef.databinding.FragmentChefEditBinding
import com.paul.chef.ui.menuDetail.bindImage
import kotlinx.parcelize.Parcelize

class ChefEditFragment : Fragment() {

    private var _binding: FragmentChefEditBinding? = null
    private val binding get() = _binding!!

    private val arg: ChefEditFragmentArgs by navArgs()

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val chefEditViewModel =
            ViewModelProvider(this).get(ChefEditViewModel::class.java)

        _binding = FragmentChefEditBinding.inflate(inflater, container, false)
        val root: View = binding.root



        val editType = arg.editType
        val profileInfo = arg.profile





        binding.profileEditEmail.text = profileInfo.email
        binding.profileEditNameLayout.editText?.setText(profileInfo.name)

        bindImage(binding.profileEditImg, profileInfo.avatar)
        val outlineProvider = ProfileOutlineProvider()
        binding.profileEditImg.outlineProvider =outlineProvider

        binding.profileEditConfirmBtn.setOnClickListener {

            val name = binding.profileEditNameLayout.editText?.text.toString()
            val intro = binding.profileEditIntro.editText?.text.toString()
            val avatar = profileInfo.avatar

//            @Parcelize
//            data class ProfileInfo(
//                val name: String,
//                val email:String,
//                val avatar: String? =null,
//                val introduce: String?=null
//            ): Parcelable

            if (name == "" || intro == "") {
                Toast.makeText(this.context, "欄位未填寫完成", Toast.LENGTH_SHORT).show()
            } else {
                val newProfile = ProfileInfo(name, profileInfo.email,avatar, intro)
                when(editType){
                    EditPageType.CREATE_USER.index ->{
                        Log.d("chefeditfragment", "newprofile=$newProfile")
                        chefEditViewModel.createUser(newProfile)
                    }
                    EditPageType.EDIT_PROFILE.index->{
                        val userId = UserManger.user.userId
                        val chefId = UserManger.user.chefId
                        if (chefId != null&&userId!=null) {
                            chefEditViewModel.saveChef(newProfile,userId, chefId )
                        }
                    }
                }
            }
        }

        chefEditViewModel.userId.observe(viewLifecycleOwner){
            chefEditViewModel.getUser(it)
        }

        chefEditViewModel.getUserDone.observe(viewLifecycleOwner){
            findNavController().navigate(MobileNavigationDirections.actionGlobalMenuFragment())
        }



//        binding.chefSave.setOnClickListener {
//            val name = binding.chefNameEdit.text.toString()
//            val email = binding.chefEmailEdit.text.toString()
//            val intro = binding.chefIntroEdit.text.toString()
//            if (name != "" && email != "" && intro != "") {
//                chefEditViewModel.saveChef(chefId, name, email, intro)
//            } else {
//                Toast.makeText(this.context, "欄位未填寫完成", Toast.LENGTH_SHORT).show()
//            }
//        }

//
//        binding.createChef.setOnClickListener {
//            Log.d("chefeditfragment", "binding.chefNameEdit.text=${binding.chefNameEdit.text}")
//            val name = binding.chefNameEdit.text.toString()
//            val email = binding.chefEmailEdit.text.toString()
//            val intro = binding.chefIntroEdit.text.toString()
//            if (name != "" && email != "" && intro != "") {
//                chefEditViewModel.createChef(name, email, intro)
//            } else {
//                Toast.makeText(this.context, "欄位未填寫完成", Toast.LENGTH_SHORT).show()
//            }
//        }





        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
