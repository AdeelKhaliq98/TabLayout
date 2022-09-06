package com.example.Adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.DataClasses.VideoDataClass
import com.example.tablayout.R

class VideoRVAdapter(
    var video_list: List<VideoDataClass>
) : RecyclerView.Adapter<VideoRVAdapter.VideosViewHolder>() {
    private lateinit var video: ImageView
    private lateinit var video_name: TextView
    private lateinit var video_duration: TextView

    inner class VideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            VideosViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.video_view_list, parent, false)
        return VideosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return video_list.size
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        holder.itemView.apply {
            video = findViewById(R.id.videoView)
            video_name = findViewById(R.id.video_name)
            video_duration = findViewById(R.id.video_duration)

            var selectedImage: Uri? =video_list[position].video
            Glide.with(this)
                .load(selectedImage)
                .into(video)
            video_name.text = video_list[position].videoName
            video_duration.text = video_list[position].videoDuration + " Mb"
        }
    }
}