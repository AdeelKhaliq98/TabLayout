package com.example.Fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Adapters.MusicRVAdapter
import com.example.DataClasses.MusicDataClass
import com.example.tablayout.R
import java.io.File

class Music : Fragment() {
    private lateinit var musicView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_music, container, false)
        val musicList: MutableList<MusicDataClass> = ArrayList()

        val musicContent=requireContext().contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if(musicContent!=null){
            if(musicContent.moveToFirst()==true){
                do{
                    val video =
                        musicContent.getString(musicContent.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val audioUri = Uri.fromFile(File(video))
                    val audioName=musicContent.getString(musicContent.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    val audioDuration=musicContent.getString(musicContent.getColumnIndex(MediaStore.Audio.Media.SIZE))

                    var audio_dataClass=MusicDataClass()
                    audio_dataClass.music=audioUri
                    audio_dataClass.musicName=audioName
                    audio_dataClass.musicDuration=audioDuration

                    musicList.add(audio_dataClass)
                }
                while (musicContent.moveToNext())
            }
            musicContent.close()
            musicView = view.findViewById(R.id.music_rv)
            musicView.adapter = MusicRVAdapter(musicList)
            musicView.layoutManager = LinearLayoutManager(requireContext())
        }
        return view
    }
}