package com.example.hw2_recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.hw2_recyclerview.databinding.ViewHolderButtonsBinding

class ButtonsTypeViewHolder(
    private val onListButtonCLick: () -> Unit,
    private val onGridButtonCLick: () -> Unit,
    private val viewBinding: ViewHolderButtonsBinding,
    ) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem() {
        with(viewBinding) {
            buttonList.text = "Список"
            buttonList.setOnClickListener {
            onListButtonCLick()
        }
            buttonGrid.text = "Сетка"
            buttonGrid.setOnClickListener {
                onGridButtonCLick()
            }}

    }
}