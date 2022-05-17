package com.paul.chef.ui.chefEdit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paul.chef.*
import com.paul.chef.data.ProfileInfo
import com.paul.chef.databinding.FragmentChefEditBinding
import com.paul.chef.ui.menuDetail.bindImage

class ChefEditFragment : Fragment() {

    private var _binding: FragmentChefEditBinding? = null
    private val binding get() = _binding!!

    private val arg: ChefEditFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val chefEditViewModel =
            ViewModelProvider(this)[ChefEditViewModel::class.java]

        _binding = FragmentChefEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val editType = arg.editType
        val profileInfo = arg.profile
        var avatar = profileInfo.avatar

        binding.profileEditEmail.text = profileInfo.email
        binding.profileEditNameLayout.editText?.setText(profileInfo.name)
        if (editType == EditPageType.EDIT_PROFILE.index && profileInfo.introduce != null) {
            binding.profileEditIntro.editText?.setText(profileInfo.introduce)
        }



        bindImage(binding.profileEditImg, avatar)
        val outlineProvider = ProfileOutlineProvider()
        binding.profileEditImg.outlineProvider = outlineProvider


        chefEditViewModel.userId.observe(viewLifecycleOwner) {
            chefEditViewModel.getUser(it)
        }

        chefEditViewModel.getUserDone.observe(viewLifecycleOwner) {
            UserManger.tempMode = Mode.USER.index
            findNavController().navigate(MobileNavigationDirections.actionGlobalMenuFragment())
        }

        setFragmentResultListener("requestImg") { requestKey, bundle ->
            val result = bundle.getString("downloadUri")
            avatar = result
            bindImage(binding.profileEditImg, avatar)
        }



        binding.profileEditAvatarBtn.setOnClickListener {
            findNavController().navigate(
                MobileNavigationDirections.actionGlobalImageUploadFragment(
                    ImgType.AVATAR.index
                )
            )
        }


        binding.profileEditConfirmBtn.setOnClickListener {

            val name = binding.profileEditNameLayout.editText?.text.toString()
            val intro = binding.profileEditIntro.editText?.text.toString()


            if (name == "" || intro == "") {
                Toast.makeText(this.context, "欄位未填寫完成", Toast.LENGTH_SHORT).show()
            } else {
                val newProfile = ProfileInfo(name, profileInfo.email, avatar, intro)
                when (editType) {
                    EditPageType.CREATE_USER.index -> {

                        chefEditViewModel.createUser(newProfile)
                    }
                    EditPageType.EDIT_PROFILE.index -> {
                        val userId = UserManger.user?.userId!!
                        val chefId = UserManger.user?.chefId!!
                        chefEditViewModel.saveChef(newProfile, userId, chefId)
                    }
                }
            }
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
