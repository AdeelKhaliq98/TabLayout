package com.example.Adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.DataClasses.PictureDataClass
import com.example.tablayout.R

class PictureRVAdapter(
    var picture_list: List<PictureDataClass>
) : RecyclerView.Adapter<PictureRVAdapter.PicturesViewHolder>() {
    private lateinit var picture_imageView: ImageView
    private lateinit var picture_name: TextView
    private lateinit var picture_description: TextView

    inner class PicturesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PicturesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.pictures_view_list, parent, false)
        return PicturesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return picture_list.size
    }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        holder.itemView.apply {
            picture_imageView = findViewById(R.id.picture_imageView)
            picture_name = findViewById(R.id.picture_name)
            picture_description = findViewById(R.id.picture_discription)
            var selectedImage: Uri? =picture_list[position].picture
            Glide.with(this)
                .load(selectedImage)
                .into(picture_imageView)
            picture_name.text = picture_list[position].picture_name
            picture_description.text = picture_list[position].picture_discrpition + " MB"
        }
    }
}