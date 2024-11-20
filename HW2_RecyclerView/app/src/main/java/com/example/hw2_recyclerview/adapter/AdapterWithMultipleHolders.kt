package com.example.hw2_recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.hw2_recyclerview.databinding.ItemHolderGridTypeBinding
import com.example.hw2_recyclerview.databinding.ItemHolderListTypeBinding
import com.example.hw2_recyclerview.databinding.ViewHolderButtonsBinding
import com.example.hw2_recyclerview.model.ButtonsHolderData
import com.example.hw2_recyclerview.model.MultipleHoldersData
import com.example.hw2_recyclerview.model.ViewHolderData
import com.example.hw2_recyclerview.repository.RecyclerViewData
import com.example.hw2_recyclerview.repository.RecyclerViewRepository
import com.example.hw2_recyclerview.utils.RecyclerViewDiffUtil
import com.example.hw2_recyclerview.viewholder.ButtonsTypeViewHolder
import com.example.hw2_recyclerview.viewholder.GridTypeViewHolder
import com.example.hw2_recyclerview.viewholder.ListTypeViewHolder

class AdapterWithMultipleHolders(
    private val requestManager: RequestManager,
    private val onListButtonCLick: () -> Unit,
    private val onGridButtonCLick: () -> Unit,
    private val onItemClick: (Int) -> Unit,
    items: List<MultipleHoldersData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val dataList = mutableListOf<MultipleHoldersData>()

    init {
        dataList.addAll(items)
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_BUTTON = 2
        const val VIEW_TYPE_GRID = 1
        const val VIEW_TYPE_LIST = 0
    }

    var isGridMode = RecyclerViewData.isGridMode


    fun setGridMode() {
        RecyclerViewData.setGridMode()
        isGridMode = true
        notifyDataSetChanged()
    }

    fun setListMode() {
        RecyclerViewData.setListMode()
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
                    onItemClick = onItemClick
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
                    onItemClick = onItemClick
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

    fun addRandomElement() {
        //val RVdataList = RecyclerViewData.recyclerViewList
        val availableItems = RecyclerViewRepository.items.filter { it !in dataList }
        if (availableItems.isNotEmpty()) {
            val randomItem = availableItems.random()
            val randomIndex = (1..dataList.size).random()
            dataList.add(randomIndex, randomItem)
            RecyclerViewData.recyclerViewList = dataList

            notifyItemInserted(randomIndex)
        } else {
            //Toast.makeText(requireContext(), "Все элементы уже добавлены", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteRandomElement() {
        if (dataList.size > 1) {
            val randomIndex = (1..dataList.size - 1).random()
            dataList.removeAt(randomIndex)
            RecyclerViewData.recyclerViewList = dataList
            notifyItemRemoved(randomIndex)
        }
    }

    fun addElements(context: Context, amount: Int) {
        var newList = dataList
        val availableItems = RecyclerViewRepository.items.filter { it !in dataList }
        if (amount <= availableItems.size) {

            val newItems = availableItems.shuffled().take(amount)
            for (i in 0..<amount) {
                if (dataList.size > 1){
                    val randomIndex = (1..<dataList.size).random()
                    newList.add(randomIndex, newItems[i])
                }
                else newList.add(1, newItems[i])

            }
            RecyclerViewData.recyclerViewList = newList
            updateData(newList)
        } else {
            Toast.makeText(
                context,
                "Количество элементов, доступное для добавления: ${availableItems.size}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun updateData(newList: List<MultipleHoldersData>) {
        val diffCallback = RecyclerViewDiffUtil(
            oldList = dataList,
            newList = newList
        )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }


}