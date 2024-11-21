package com.example.hw2_recyclerview.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hw2_recyclerview.adapter.AdapterWithMultipleHolders
import com.example.hw2_recyclerview.repository.RecyclerViewData

// Класс с реализацией логики удаления элемента по свайпу
class SwipeItemClass(
    private var rvAdapter: AdapterWithMultipleHolders
) {
    val itemTouchHelper =
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            // Метод для обработки перемещения элементов (не используется)
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            // Метод для обработки свайпа
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                Log.d("tag", position.toString() + " " + RecyclerViewData.recyclerViewList.size.toString())

                // Если позиция элемента корректна (в пределах списка), удаляем его
                if (position in 1..<RecyclerViewData.recyclerViewList.size) {
                    RecyclerViewData.recyclerViewList.removeAt(position) // Удаляем элемент из списка данных
                    rvAdapter.dataList = RecyclerViewData.recyclerViewList // Обновляем данные в адаптере
                    rvAdapter.notifyItemRemoved(position) // Сообщаем адаптеру, что элемент был удален

                } else {
                    // Восстановление элемента, если индекс некорректен
                    rvAdapter.notifyItemChanged(position)
                }
            }

            // Метод для задания порога свайпа, после которого элемент считается удаленным
            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return 0.66f
            }

            // Метод, разрешающий свайпы только для RV типа "Список"
            override fun isItemViewSwipeEnabled(): Boolean {
                return RecyclerViewData.recyclerViewType == RvTypes.LIST
            }

            // Метод для изменения визуального эффекта при свайпе
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                itemView.alpha = 1.0f - Math.abs(dX) / recyclerView.width
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })
}