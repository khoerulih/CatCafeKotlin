package com.example.catcafekotlin.adapter

import com.example.catcafekotlin.entity.Food
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.catcafekotlin.R
import com.example.catcafekotlin.databinding.ItemFoodBinding


class FoodAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    var listFoods = ArrayList<Food>()
        set(listFoods) {
            if (listFoods.size > 0) {
                this.listFoods.clear()
            }
            this.listFoods.addAll(listFoods)
        }

    fun addItem(food: ArrayList<Food>) {
        this.listFoods.addAll(food)
        notifyItemInserted(this.listFoods.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(listFoods[position])
    }

    override fun getItemCount(): Int = this.listFoods.size

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFoodBinding.bind(itemView)
        fun bind(food: Food) {
            Glide.with(itemView.context)
                .load(food.thumbnail)
                .into(binding.imgThumbnail)
            binding.tvItemName.text = food.name
            binding.tvItemPrice.text = food.price
            binding.cardView.setOnClickListener {
                onItemClickCallback.onItemClicked(food, adapterPosition)
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(selectedFood: Food?, position: Int?)
    }
}