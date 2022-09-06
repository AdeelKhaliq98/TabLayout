package com.example.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.DataClasses.DocumentDataClass
import com.example.tablayout.R

class DocumentRVAdapter(
    var document_list: List<DocumentDataClass>
) : RecyclerView.Adapter<DocumentRVAdapter.PicturesViewHolder>() {
    private lateinit var document_name: TextView
    private lateinit var document_size: TextView
    private lateinit var document_uri: ImageView

    inner class PicturesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PicturesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.document_view_list, parent, false)
        return PicturesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return document_list.size
    }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        holder.itemView.apply {
            document_uri = findViewById(R.id.document_Uri)
            document_name = findViewById(R.id.document_title)
            document_size = findViewById(R.id.document_size)

            document_uri.setBackgroundResource(R.drawable.pdf)
            document_name.text = document_list[position].documentdate
            document_size.text = document_list[position].id

        }
    }
}