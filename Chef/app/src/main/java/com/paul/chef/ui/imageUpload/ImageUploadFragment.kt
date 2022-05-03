package com.paul.chef.ui.imageUpload

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.os.UserManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.storage.FirebaseStorage
import com.paul.chef.*
import com.paul.chef.databinding.FragmentHomeBinding
import com.paul.chef.databinding.FragmentImageUploadBinding
import com.paul.chef.ui.book.BookFragmentArgs
import com.paul.chef.ui.home.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*


class ImageUploadFragment : DialogFragment() {

    private var _binding: FragmentImageUploadBinding? = null
    private val binding get() = _binding!!
    lateinit var ImageUri:Uri

    private val arg: ImageUploadFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectImg()
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentImageUploadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val imageType  = arg.imageType
        val density = Resources.getSystem().displayMetrics.density
        when(imageType){
            ImgType.AVATAR.index->{
                binding.imageView.layoutParams.height = 240 * density.toInt()
                binding.imageView.layoutParams.width = 240 * density.toInt()
                val outlineProvider = ProfileOutlineProvider()
                binding.imageView.outlineProvider =outlineProvider
            }
            ImgType.MENU.index->{
//                binding.imageView.layoutParams.height = 240 * density.toInt()
//                binding.imageView.layoutParams.width = 240 * density.toInt()
            }
        }




        binding.imgUploadUpload.setOnClickListener {
            uploadImg()
        }

        return root
    }

    private fun uploadImg() {
        val progressDialog = ProgressDialog(this.context)
        progressDialog.setMessage("Uploading file...")
         progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm", Locale.getDefault())
        val now = Date()
        val userId = UserManger.user.userId
        val fileName = userId+"_"+formatter.format(now)
         val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
        val uploadTask = storageReference.putFile(ImageUri )
            .addOnSuccessListener {

//                binding.imageView.setImageURI(null)
//                Toast.makeText(this.context, "successfully upload", Toast.LENGTH_SHORT)
//                if(progressDialog.isShowing) progressDialog.dismiss()
                Log.d("uploadfragment", "upload success,  and then get url")
                
        }
            .addOnFailureListener{
                if(progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this.context, "failed", Toast.LENGTH_SHORT)

            }

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.imageView.setImageURI(null)
                Toast.makeText(this.context, "successfully upload", Toast.LENGTH_SHORT)
                if(progressDialog.isShowing) progressDialog.dismiss()
                val downloadUri = task.result
                Log.d("imguploadfragment", "downloadUri}=${downloadUri}")
                setFragmentResult("requestImg", bundleOf("downloadUri" to downloadUri.toString()))
                findNavController().navigateUp()
            } else {
                // Handle failures
                // ...
            }
        }
    }

    private fun selectImg() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action   = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 200)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==200 && resultCode == RESULT_OK){
            ImageUri = data?.data!!
            binding.imageView.setImageURI(ImageUri)
        }
    }

}
