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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Adapters.DocumentRVAdapter
import com.example.DataClasses.DocumentDataClass
import com.example.tablayout.R
import java.io.File

class Documents : Fragment() {

    private lateinit var documentView: RecyclerView

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
        var view = inflater.inflate(R.layout.fragment_documents, container, false)

        val listDocuments: ArrayList<DocumentDataClass> = ArrayList()
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
        )
        val sortOrder = MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        val selection = MediaStore.Files.FileColumns.MIME_TYPE + " = ?"
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")
        val selectionArgs = arrayOf(mimeType)

        val collection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Files.getContentUri("external")
        }

        requireContext().contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        ).use { cursor ->
            if (cursor != null) {
                if (cursor.moveToFirst() == true) {
                    val columnID: Int = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)
                    val columnData: Int =
                        cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                    val columnName: Int =
                        cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE)
                    val displayName: Int =
                        cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
                    do {
                        val id: String = cursor.getString(columnID)
                        val path: String = cursor.getString(columnData)
                        val name: String = cursor.getString(columnName)
                        val displayName: String = cursor.getString(displayName)

                        val file = File(path)
                        val fileUri = Uri.fromFile(file)
                        if (file.exists()) {
                            var documentlist = DocumentDataClass()
                            documentlist.id = id
                            documentlist.documentname = name
                            documentlist.documentdate = displayName
                            documentlist.documentUri = fileUri
                            listDocuments.add(documentlist)
                        }
                    } while (cursor.moveToNext())
                }
            }
            documentView = view.findViewById(R.id.document_rv)
            documentView.adapter = DocumentRVAdapter(listDocuments)
            documentView.layoutManager = LinearLayoutManager(requireContext())
        }
        return view
    }
}