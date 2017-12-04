package com.pabloserrano.cleancomponents.ui.browse

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pabloserrano.cleancomponents.ui.R
import com.pabloserrano.cleancomponents.ui.model.PhotoViewModel
import javax.inject.Inject

class BrowseAdapter @Inject constructor() : RecyclerView.Adapter<BrowseAdapter.ViewHolder>() {

    var photos: List<PhotoViewModel> = arrayListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]
        Glide.with(holder.itemView.context)
                .load(photo.urls)
                .into(holder.photoImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_photo, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var photoImage: ImageView

        init {
            photoImage = view.findViewById(R.id.image_photo)
        }
    }
}