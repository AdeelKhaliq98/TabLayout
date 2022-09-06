package com.example.Adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.DataClasses.MusicDataClass
import com.example.tablayout.R


class MusicRVAdapter(
    var music_list: List<MusicDataClass>
) : RecyclerView.Adapter<MusicRVAdapter.VideosViewHolder>() {
    private lateinit var audio: ImageView
    private lateinit var audio_name: TextView
    private lateinit var audio_duration: TextView

    inner class VideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            VideosViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.music_view_list, parent, false)
        return VideosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return music_list.size
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        holder.itemView.apply {
            audio = findViewById(R.id.musicView)
            audio_name = findViewById(R.id.music_name)
            audio_duration = findViewById(R.id.music_duration)

            var selectedImage: Uri? =music_list[position].music
            Glide.with(this)
                .load(selectedImage)
                 .thumbnail(0.6f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(audio)

           audio.setBackgroundResource(R.drawable.download)
            audio_name.text = music_list[position].musicName
            audio_duration.text = music_list[position].musicDuration
        }
    }
}