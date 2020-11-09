package com.bignerdranch.android.photoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.photoapp.database.Photo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddPhotoFragment : Fragment() {
    val args by navArgs<AddPhotoFragmentArgs>()
    private lateinit var  photoViewModel: PhotoViewModel1

    lateinit var photoImageView: ImageView
    lateinit var photoTypeEditText: EditText
    lateinit var addButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_add_photo, container, false)
        photoImageView = view.findViewById(R.id.photo_image_view)
        photoTypeEditText= view.findViewById(R.id.type_edit_text)
        addButton = view.findViewById(R.id.add_button)
        photoViewModel= ViewModelProvider(this).get(PhotoViewModel1::class.java)

        photoImageView.setImageBitmap(args.Photo.photo)
        photoTypeEditText.setText(args.Photo.photoType)
        addButton.setOnClickListener {
            var type = photoTypeEditText.text.toString()
            if (type==""){
                photoTypeEditText.error= "Please enter type"
            }
            else {
                var photo = Photo(0,type,args.Photo.photo)
                    photoViewModel.addPhoto(photo)
                    findNavController().navigate(R.id.action_addPhotoFragment_to_photoListFragment)



            }
        }


        return view
    }

}