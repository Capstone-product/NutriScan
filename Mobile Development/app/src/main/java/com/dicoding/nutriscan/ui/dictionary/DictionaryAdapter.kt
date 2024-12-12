package com.dicoding.nutriscan.ui.dictionary

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.nutriscan.R
import com.dicoding.nutriscan.ui.detail.DictionaryDetailActivity
import com.dicoding.nutriscan.ui.home.Fruit

class DictionaryAdapter(private var dictionaryList: ArrayList<Fruit>) : RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fruit, parent, false)
        return DictionaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        val (name, description, imageResource) = dictionaryList[position]
        holder.fruitName.text = name
        holder.fruitDescription.text = description
        holder.fruitImage.setImageResource(imageResource)
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DictionaryDetailActivity::class.java)
            intentDetail.putExtra("key_article", dictionaryList[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = dictionaryList.size

    inner class DictionaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fruitName: TextView = itemView.findViewById(R.id.tv_event_name)
        val fruitDescription: TextView = itemView.findViewById(R.id.tv_event_description)
        val fruitImage: ImageView = itemView.findViewById(R.id.img_event_logo)
    }

    fun setData(dictionaryList: ArrayList<Fruit>) {
        this.dictionaryList = dictionaryList
        notifyDataSetChanged()
    }
}