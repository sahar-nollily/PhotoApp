package com.bignerdranch.android.photoapp

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.photoapp.database.Photo
import kotlinx.android.synthetic.main.fragment_photo_list.view.*

class PhotoListFragment : Fragment() {
    private lateinit var  photoViewModel: PhotoViewModel1
    lateinit var recyclerView: RecyclerView
    //lateinit var  imageView: ImageView
    var photo = emptyList<Photo>()
    private var adapter:PhotoAdaptor = PhotoAdaptor(photo)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context,3)
        photoViewModel= ViewModelProvider(this).get(PhotoViewModel1::class.java)
        setHasOptionsMenu(true)
        photoViewModel.photos.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
        recyclerView.adapter = adapter
        if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),arrayOf(android.Manifest.permission.CAMERA),100)
        }

        view.floatingActionButton.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,100)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== 100){
            var bitmap = data?.extras?.get("data")
            var photo = Photo(0,"", bitmap as Bitmap?)
            var action = PhotoListFragmentDirections.actionPhotoListFragmentToAddPhotoFragment(photo)
            findNavController().navigate(action)
            //imageView.setImageBitmap(bitmap as Bitmap?)
        }

    }

    private inner class PhotoHolder(view: View): RecyclerView.ViewHolder(view){
        private lateinit var photo: Photo
        var imageView : ImageView = view.findViewById(R.id.image)

        fun bind(photo: Photo){
            this.photo = photo
            imageView.setImageBitmap(photo.photo)

        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        var searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    SearchPhoto(query, )
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    fun SearchPhoto(query: String ){
        var temp = mutableListOf<Photo>()
        for (i in 0 until adapter.photoList.size){
            if(adapter.photoList[i].photoType == query){
                temp.add(adapter.photoList[i])
            }
        }
        adapter.setData(temp)
    }

    private inner class PhotoAdaptor(var photoList: List<Photo>): RecyclerView.Adapter<PhotoHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
            val view = layoutInflater.inflate(R.layout.photo_list_item, parent, false)

            return PhotoHolder(view)
        }

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            var photoList = this.photoList[position]
            holder.bind(photoList)
        }


        override fun getItemCount(): Int {
            return photoList.size
        }

        fun setData(photo: List<Photo>){
            this.photoList = photo
            notifyDataSetChanged()
        }

    }

}