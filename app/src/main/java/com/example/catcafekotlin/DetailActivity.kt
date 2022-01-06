package com.example.catcafekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.catcafekotlin.databinding.ActivityDetailBinding
import com.example.catcafekotlin.db.FoodHelper
import com.example.catcafekotlin.entity.Food

class DetailActivity : AppCompatActivity() {
    private var food: Food? = null
    private lateinit var foodHelper: FoodHelper
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_FOOD = "extra_food"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        food = intent.getParcelableExtra(EXTRA_FOOD)

        food?.let {
            binding.tvDetailName.text = it.name
            binding.tvDetailDescription.text = it.description
            binding.tvDetailPrice.text = it.price
            Glide.with(applicationContext)
                .load(it.thumbnail)
                .into(binding.imageDetailThumbnail)

            supportActionBar?.title = it.name
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}