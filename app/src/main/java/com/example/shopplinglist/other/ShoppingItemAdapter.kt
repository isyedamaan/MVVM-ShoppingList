package com.example.shopplinglist.other

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopplinglist.R
import com.example.shopplinglist.data.db.entities.ShoppingItem
import com.example.shopplinglist.databinding.ShoppingItemBinding
import com.example.shopplinglist.ui.shoppinglist.ShoppingViewModel

class ShoppingItemAdapter(
    var items: List<ShoppingItem>,
    private val viewModel: ShoppingViewModel
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val binding =
            ShoppingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val curShoppingItem = items[position]

        with(holder.binding){
            tvName.text = curShoppingItem.name
            tvAmount.text = "${curShoppingItem.amount}"

            ivDelete.setOnClickListener {
                viewModel.delete(curShoppingItem)
            }

            ivPlus.setOnClickListener {
                curShoppingItem.amount++
                viewModel.upsert(curShoppingItem)
            }

            ivMinus.setOnClickListener {
                if (curShoppingItem.amount > 0) {
                    curShoppingItem.amount--
                    viewModel.upsert(curShoppingItem)
                }
            }
        }


    }

    inner class ShoppingViewHolder(val binding: ShoppingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}