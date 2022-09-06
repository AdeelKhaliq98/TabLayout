package com.example.Fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Adapters.PictureRVAdapter
import com.example.DataClasses.PictureDataClass
import com.example.tablayout.R
import java.io.File

class Pictures : Fragment() {
    private lateinit var rvTodos: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    @SuppressLint("Range", "WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_pictures, container, false)
        val pictureList: MutableList<PictureDataClass> = ArrayList()

        val content = requireContext().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, null, null, null
        )

        if (content != null) {
            if (content.moveToFirst() == true) {
                do {
                    val pictured =
                        content.getString(content.getColumnIndex(MediaStore.Images.Media.DATA))
                    val image_Uri = Uri.fromFile(File(pictured))
                    val name =
                        content.getString(content.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
                    val discription =
                        content.getInt(content.getColumnIndex(MediaStore.Images.Media.SIZE))

                    val sizeinKb: Float = ((discription / 1024).toFloat())
                    val sizeinMb: Float = sizeinKb / 1024
                    var picsture_DataClass = PictureDataClass()
                    picsture_DataClass.picture = image_Uri
                    picsture_DataClass.picture_name = name
                    picsture_DataClass.picture_discrpition = sizeinMb.toString()
                    pictureList.add(picsture_DataClass)
                } while (content.moveToNext())
            }
        }
        content?.close()
        rvTodos = view.findViewById(R.id.music_rv)
        rvTodos.adapter = PictureRVAdapter(pictureList)
        rvTodos.layoutManager = LinearLayoutManager(requireContext())
        return view
    }
}
