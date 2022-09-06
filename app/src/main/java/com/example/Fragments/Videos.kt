package com.example.Fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Adapters.VideoRVAdapter
import com.example.DataClasses.VideoDataClass
import com.example.tablayout.R
import java.io.File

class Videos : Fragment() {

    private lateinit var videoView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_videos, container, false)

        val videoList: MutableList<VideoDataClass> = ArrayList()

        val projection = arrayOf(
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.DISPLAY_NAME,
        )
        val sortOrder = MediaStore.Video.Media.DATE_ADDED + " DESC"
        val selection = MediaStore.Video.Media.MIME_TYPE + " = ?"
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("mkv")
        val selectionArgs = arrayOf(mimeType)
        val collection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Video.Media.getContentUri("external")
        }

        requireContext().contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        ).use { cursor ->
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    val columnData: Int =
                        cursor.getColumnIndex(MediaStore.Video.Media.DATA)
                    val columnName: Int =
                        cursor.getColumnIndex(MediaStore.Video.Media.TITLE)
                    val size: Int =
                        cursor.getColumnIndex(MediaStore.Video.Media.SIZE)
                    do {
                        val path: String = cursor.getString(columnData)
                        val name: String = cursor.getString(columnName)
                        val dname: Int = cursor.getInt(size)

                        val sizeinKb:Float=(dname/1024).toFloat()
                        val sizeinMb:Float=sizeinKb/1024

                        val file = File(path)
                        val fileUri = Uri.fromFile(file)
                        if (file.exists()) {
                            val video_List = VideoDataClass()
                            video_List.video = fileUri
                            video_List.videoName = name
                            video_List.videoDuration = sizeinMb.toString()

                            videoList.add(video_List)
                        }
                    } while (cursor.moveToNext())
                }
            }
            videoView = view.findViewById(R.id.videos_rv)
            videoView.adapter = VideoRVAdapter(videoList)
            videoView.layoutManager = LinearLayoutManager(requireContext())
        }

        return view
    }
}