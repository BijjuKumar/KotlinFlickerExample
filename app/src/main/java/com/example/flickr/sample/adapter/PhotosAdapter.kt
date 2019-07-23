package com.example.flickr.sample.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.flickr.R
import com.example.flickr.sample.models.PhotoItem
import com.example.flickr.sample.utils.AppConstants
import kotlinx.android.synthetic.main.item_gallery.view.*

class PhotosAdapter(
    private val context: Context,
    private val photoItems: MutableList<PhotoItem>
) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PhotosAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false))
    }

    override fun getItemCount(): Int {
        return photoItems.size
    }

    override fun onBindViewHolder(holder: PhotosAdapter.ViewHolder, position: Int) {

        val item = photoItems.get(position)
        val thumbnailLink =  AppConstants.getFlickrImageLink(item.id, item.secret, item.server, item.farm, AppConstants.SMALL_SQUARE)

        Glide.with(context)
            .load(thumbnailLink)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.image
            )

        holder.title.setText(item.title)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image =itemView.item_image
        val title = itemView.item_text


    }
}
