package com.dicoding.nutriscan.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.nutriscan.R
import com.dicoding.nutriscan.ui.detail.ArticleDetailActivity


class FruitAdapter(private var fruitList: ArrayList<Fruit>) : RecyclerView.Adapter<FruitAdapter.FruitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_articel, parent, false)
        return FruitViewHolder(view)
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        val (name, description, imageResource) = fruitList[position]
        holder.fruitName.text = name
        holder.fruitDescription.text = description
        holder.fruitImage.setImageResource(imageResource)
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, ArticleDetailActivity::class.java)
            intentDetail.putExtra("key_article", fruitList[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }

    }

    override fun getItemCount(): Int = fruitList.size

    inner class FruitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fruitName: TextView = itemView.findViewById(R.id.tv_event_name)
        val fruitDescription: TextView = itemView.findViewById(R.id.tv_event_description)
        val fruitImage: ImageView = itemView.findViewById(R.id.img_event_logo)
    }

    fun setData(fruitList: ArrayList<Fruit>) {
        this.fruitList = fruitList
        notifyDataSetChanged()
    }
}




