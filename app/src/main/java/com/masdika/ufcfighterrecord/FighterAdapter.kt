package com.masdika.ufcfighterrecord

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FighterAdapter(private val listFighter: ArrayList<Fighter>) : RecyclerView.Adapter<FighterAdapter.FighterViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class FighterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fighterImage: ImageView = itemView.findViewById(R.id.fighter_image)
        val fighterName: TextView = itemView.findViewById(R.id.fighter_name_textview)
        val fighterDivision: TextView = itemView.findViewById(R.id.fighter_division_textview)
        val fighterAbout: TextView = itemView.findViewById(R.id.fighter_about_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FighterViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_fighter, parent, false)
        return FighterViewHolder(view)
    }

    override fun getItemCount(): Int = listFighter.size

    override fun onBindViewHolder(holder: FighterViewHolder, position: Int) {
        val (image, name, division, about) = listFighter[position]
        Glide.with(holder.itemView.context)
            .load(image)
            .into(holder.fighterImage)
        holder.fighterName.text = name
        holder.fighterDivision.text = division
        holder.fighterAbout.text = about

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFighter[holder.adapterPosition]) }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Fighter)
    }
}