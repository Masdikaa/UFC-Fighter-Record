package com.masdika.ufcfighterrecord

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FighterAdapter(
    private val listFighter: ArrayList<Fighter>,
    private var isGridLayout: Boolean
) : RecyclerView.Adapter<FighterAdapter.FighterViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    companion object {
        private const val VIEW_TYPE_LINEAR = 0
        private const val VIEW_TYPE_GRID = 1
    }

    class FighterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fighterImage: ImageView = itemView.findViewById(R.id.fighter_image)
        val fighterName: TextView = itemView.findViewById(R.id.fighter_name_textview)
        val fighterDivision: TextView = itemView.findViewById(R.id.fighter_division_textview)
        val fighterAbout: TextView = itemView.findViewById(R.id.fighter_about_textview)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGridLayout) VIEW_TYPE_GRID else VIEW_TYPE_LINEAR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FighterViewHolder {
        val layoutId = if (viewType == VIEW_TYPE_GRID) {
            R.layout.item_fighter_grid
        } else {
            R.layout.item_fighter_linear
        }
        val view: View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
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

//        if (!isGridLayout) {
//            holder.fighterAbout?.text = about
//        }

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFighter[holder.adapterPosition]) }
    }

    fun setLayoutMode(isGrid: Boolean) {
        isGridLayout = isGrid
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Fighter)
    }
}