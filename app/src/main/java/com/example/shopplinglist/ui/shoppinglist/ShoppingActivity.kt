package com.example.shopplinglist.ui.shoppinglist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopplinglist.R
import com.example.shopplinglist.data.db.entities.ShoppingItem
import com.example.shopplinglist.databinding.ActivityShoppingBinding
import com.example.shopplinglist.other.ShoppingItemAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ShoppingActivity : AppCompatActivity(), KodeinAware{

    override val kodein by kodein()
    private val factory: ShoppingViewModelFactory by instance()

    private lateinit var binding: ActivityShoppingBinding
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val viewModel = ViewModelProvider(this, factory)[ShoppingViewModel::class.java]

        val adapter = ShoppingItemAdapter(listOf(),viewModel)

        binding.rvShoppingItems.layoutManager = LinearLayoutManager(this)
        binding.rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(this) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }

        binding.fab.setOnClickListener{
            AddShoppingItemDialog(
                this,
                object: AddDialogListener {
                    override fun onAddButtonClicked(item: ShoppingItem) {
                        viewModel.upsert(item)
                    }
                }).show()
        }
    }
}