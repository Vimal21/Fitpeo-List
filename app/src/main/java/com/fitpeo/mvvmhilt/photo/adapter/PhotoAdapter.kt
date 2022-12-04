package com.fitpeo.mvvmhilt.photo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitpeo.mvvmhilt.R
import com.fitpeo.mvvmhilt.base.BaseAdapter
import com.fitpeo.mvvmhilt.photo.model.Photo
import com.fitpeo.mvvmhilt.databinding.ItemPhotoBinding
import com.squareup.picasso.Picasso

/**
 * Photo adapter
 *
 * @constructor Create Photo adapter
 */
class PhotoAdapter : BaseAdapter<Photo, PhotoAdapter.PhotoViewHolder>() {

    /**
     * This method calls onCreateViewHolder(ViewGroup, int) to create a new RecyclerView.ViewHolder
     * and initializes some private fields to be used by RecyclerView.
     *
     * @param parent     parent viewGroup on the adapter
     * @param viewType  view type for the adapter
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(ItemPhotoBinding.bind(view))
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   the blogViewHolder view at specified position
     * @param position indexed position need to update item
     */
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = items[position]
        holder.binding.textTitle.text = photo.title
        Picasso.get().load(photo.thumbnailUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.binding.image)

        holder.binding.blogLayout.setOnClickListener {
            listener?.onClick(photo)
        }
    }

    /**
     * @return the total number of items in the data set held by the adapter.
     * */
    override fun getItemCount(): Int = items.size

    /**
     * Photo view holder
     *
     * @property binding
     * @constructor Create photo view holder
     */
    class PhotoViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)
}
