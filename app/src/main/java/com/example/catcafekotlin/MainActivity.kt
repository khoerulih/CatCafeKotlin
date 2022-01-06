package com.example.catcafekotlin

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.catcafekotlin.data.FoodData
import com.example.catcafekotlin.db.DatabaseContract
import com.example.catcafekotlin.db.FoodHelper

import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catcafekotlin.adapter.FoodAdapter
import com.example.catcafekotlin.databinding.ActivityMainBinding
import com.example.catcafekotlin.entity.Food
import com.example.catcafekotlin.helper.MapHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var foodHelper: FoodHelper
    private lateinit var adapter: FoodAdapter
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM;
        supportActionBar?.setCustomView(R.layout.custom_actionbar);

        binding.rvMenu.layoutManager = LinearLayoutManager(this)
        binding.rvMenu.setHasFixedSize(true)

        adapter = FoodAdapter(object : FoodAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedFood: Food?, position: Int?) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_FOOD, selectedFood)
                startActivity(intent)
            }
        })
        binding.rvMenu.adapter = adapter

        foodHelper = FoodHelper.getInstance(applicationContext)
        foodHelper.open()

        if (savedInstanceState == null) {
            loadFoodsAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Food>(EXTRA_STATE)
            if (list != null) {
                adapter.listFoods = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFoods)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.btn_logout -> showLogoutAlertDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeData() {
        for (position in FoodData.foodNames.indices) {
            val values = ContentValues()
            values.put(DatabaseContract.FoodColumns.NAME, FoodData.foodNames[position])
            values.put(
                DatabaseContract.FoodColumns.DESCRIPTION,
                FoodData.foodDescriptions[position]
            )
            values.put(DatabaseContract.FoodColumns.PRICE, FoodData.foodPrices[position])
            values.put(DatabaseContract.FoodColumns.THUMBNAIL, FoodData.foodThumbnails[position])
            foodHelper.insert(values)
        }
        loadFoodsAsync()
    }

    private fun loadFoodsAsync() {
        lifecycleScope.launch {
            binding.progressbar.visibility = View.VISIBLE
            val deferredFoods = async(Dispatchers.IO) {
                val cursor = foodHelper.queryAll()
                MapHelper.mapCursorToArrayList(cursor)
            }
            binding.progressbar.visibility = View.INVISIBLE
            val foods = deferredFoods.await()
            if (foods.size == 0) {
                initializeData()
            } else {
                adapter.addItem(foods)
                adapter.listFoods = foods
            }
        }
    }

    private fun showLogoutAlertDialog() {

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle("Logout Message")
        alertDialogBuilder
            .setMessage("Apakah anda yakin?")
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                Firebase.auth.signOut()
                Toast.makeText(this@MainActivity, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Tidak") {dialog, _ -> dialog.cancel()}
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}