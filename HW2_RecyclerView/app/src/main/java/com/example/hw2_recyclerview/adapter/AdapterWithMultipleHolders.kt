package com.example.hw2_recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.hw2_recyclerview.databinding.ItemHolderGridTypeBinding
import com.example.hw2_recyclerview.databinding.ItemHolderListTypeBinding
import com.example.hw2_recyclerview.databinding.ViewHolderButtonsBinding
import com.example.hw2_recyclerview.model.ButtonsHolderData
import com.example.hw2_recyclerview.model.MultipleHoldersData
import com.example.hw2_recyclerview.model.ViewHolderData
import com.example.hw2_recyclerview.viewholder.ButtonsTypeViewHolder
import com.example.hw2_recyclerview.viewholder.GridTypeViewHolder
import com.example.hw2_recyclerview.viewholder.ListTypeViewHolder

class AdapterWithMultipleHolders(
    private val requestManager: RequestManager,
    private val onListButtonCLick: () -> Unit,
    private val onGridButtonCLick: () -> Unit,
    items: List<MultipleHoldersData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val dataList = mutableListOf<MultipleHoldersData>()

    init {
        dataList.addAll(items)
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_BUTTON = 2
        const val VIEW_TYPE_GRID = 1
        const val VIEW_TYPE_LIST = 0
    }

    var isGridMode = false

    fun setGridMode() {
        isGridMode = true
        notifyDataSetChanged()
    }

    fun setListMode() {
        isGridMode = false
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val item = dataList[position]
        return when {
            item is ButtonsHolderData -> VIEW_TYPE_BUTTON
            item is ViewHolderData && isGridMode -> VIEW_TYPE_GRID
            item is ViewHolderData && !isGridMode -> VIEW_TYPE_LIST
            else -> throw IllegalStateException("Incorrect holder type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LIST -> {
                ListTypeViewHolder(
                    viewBinding = ItemHolderListTypeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    requestManager = requestManager,
                )
            }

            VIEW_TYPE_GRID -> {
                GridTypeViewHolder(
                    viewBinding = ItemHolderGridTypeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    requestManager = requestManager,
                )
            }

            VIEW_TYPE_BUTTON -> {
                ButtonsTypeViewHolder(
                    onListButtonCLick = onListButtonCLick,
                    onGridButtonCLick = onGridButtonCLick,
                    viewBinding = ViewHolderButtonsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> throw IllegalStateException("Unknown holder")
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (dataList[position]) {
            is ViewHolderData -> {
                if (isGridMode) {
                    (holder as? GridTypeViewHolder)?.bindItem(dataList[position] as ViewHolderData)
                } else {
                    (holder as? ListTypeViewHolder)?.bindItem(dataList[position] as ViewHolderData)
                }
            }
            is ButtonsHolderData -> {
                (holder as? ButtonsTypeViewHolder)?.bindItem()
            }
        }
    }



}