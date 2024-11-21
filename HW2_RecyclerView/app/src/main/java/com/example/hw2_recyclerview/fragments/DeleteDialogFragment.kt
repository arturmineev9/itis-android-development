package com.example.hw2_recyclerview.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.hw2_recyclerview.adapter.AdapterWithMultipleHolders
import com.example.hw2_recyclerview.model.MultipleHoldersData
import com.example.hw2_recyclerview.repository.RecyclerViewData


class DeleteDialogFragment(
    var position: Int,
    var rvAdapter: AdapterWithMultipleHolders?
) : DialogFragment()
{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Удалить элемент?")
                .setPositiveButton("Удалить") { dialog, id ->
                    RecyclerViewData.recyclerViewList.removeAt(position)
                    rvAdapter?.notifyItemRemoved(position)
                }
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}