package com.example.shopplinglist.ui.shoppinglist

import com.example.shopplinglist.data.db.entities.ShoppingItem

interface AddDialogListener {
    fun onAddButtonClicked(item: ShoppingItem)
}