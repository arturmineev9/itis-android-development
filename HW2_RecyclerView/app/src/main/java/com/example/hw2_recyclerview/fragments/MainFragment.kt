package com.example.hw2_recyclerview.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hw2_recyclerview.R
import com.example.hw2_recyclerview.adapter.AdapterWithMultipleHolders
import com.example.hw2_recyclerview.adapter.AdapterWithMultipleHolders.Companion.VIEW_TYPE_BUTTON
import com.example.hw2_recyclerview.databinding.FragmentFirstScreenBinding
import com.example.hw2_recyclerview.repository.RecyclerViewData
import com.example.hw2_recyclerview.utils.RvTypes
import com.example.hw2_recyclerview.utils.SwipeItemClass


class MainFragment : Fragment() {

    private var viewBinding: FragmentFirstScreenBinding? = null
    private var rvAdapter: AdapterWithMultipleHolders? = null
    private val mainContainerId = R.id.main_fragment_container

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Инициализация RecyclerView
        initRecyclerView()
        // Показ BottomSheet при нажатии на FAB
        showBottomSheet()
        // Установка функционала для свайпа элементов для их удаления
        setupSwipeToDelete(viewBinding?.mainRecycler)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    // Метод для настройки свайпа для удаления элементов RecyclerView
    private fun setupSwipeToDelete(recyclerView : RecyclerView?) {
        val swipeItemClass = rvAdapter?.let { SwipeItemClass(it) }
        swipeItemClass?.itemTouchHelper?.attachToRecyclerView(recyclerView)
    }

    // Метод для отключения свайпа при смене режима отображения (Сетка)
    private fun disableSwipe() {
        val swipeItemClass = rvAdapter?.let { SwipeItemClass(it) }
        swipeItemClass?.itemTouchHelper?.attachToRecyclerView(null)
    }

    // Инициализация RecyclerView и установка адаптера и LayoutManager в зависимости от типа отображения
    private fun initRecyclerView() {
        rvAdapter = AdapterWithMultipleHolders(
            requestManager = Glide.with(requireContext()),
            onGridButtonCLick = ::onGridButtonClick, // Обработчик для кнопки "Сетка №1"
            onListButtonCLick = ::onListButtonClick, // Обработчик для кнопки "Список"
            onModifiedGridButtonCLick = ::onModifiedGridButtonClick, // Обработчик для кнопки "Сетка №2"
            onItemClick = ::onItemClick, // Обработчик для клика на элемент списка
            onLongItemClick = ::onLongItemClick, // Обработчик для долгого клика на элемент списка
            items = RecyclerViewData.recyclerViewList // Список данных для RecyclerView
        )

        // Устанавливаем адаптер и LayoutManager в зависимости от типа отображения
        viewBinding?.let { binding ->
            binding.mainRecycler.adapter = rvAdapter
            binding.mainRecycler.layoutManager = when (RecyclerViewData.recyclerViewType){
                RvTypes.LIST -> getLinearLayout()
                RvTypes.GRID -> getGridLayout()
                RvTypes.MODIFIED_GRID -> getModifiedGridLayoutManager()
            }
        }

    }


    // Метод для получения LinearLayoutManager
    private fun getLinearLayout() : LinearLayoutManager {
        return LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    // Метод для получения LayoutManager для сетки с 3 столбцами
    private fun getGridLayout() : GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(context, 3)

        // Рястягиваем ViewHolder с кнопками на всю ширину
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (rvAdapter?.getItemViewType(position)) {
                    VIEW_TYPE_BUTTON -> gridLayoutManager.spanCount
                    else -> 1
                }
            }
        }
    return gridLayoutManager
    }

    // Метод для получения LayoutManager для модифицированной сетки (с переменным количеством столбцов)
    private fun getModifiedGridLayoutManager() : GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)

        // Логика для разных размеров ячеек
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) {
                    return 2
                }
                return when ((position - 1) % 4) {
                    0, 3 -> 2
                    else -> 1
                }
            }
        }
        return gridLayoutManager
    }

    // Метод для обработки нажатия на кнопку "Список"
    private fun onListButtonClick() {
        viewBinding?.mainRecycler?.layoutManager =
            getLinearLayout()
        rvAdapter?.setListMode()
        setupSwipeToDelete(viewBinding?.mainRecycler)
    }

    // Метод для обработки нажатия на кнопку "Сетка №1"
    private fun onGridButtonClick() {
        val gridLayoutManager = getGridLayout()
        viewBinding?.mainRecycler?.layoutManager = gridLayoutManager
        rvAdapter?.setGridMode()
        disableSwipe()
    }

    // Метод для обработки нажатия на кнопку "Сетка №2"
    private fun onModifiedGridButtonClick() {
        val modifiedGridLayoutManager = getModifiedGridLayoutManager()
        viewBinding?.mainRecycler?.layoutManager = modifiedGridLayoutManager
        rvAdapter?.setModifiedGridMode()
        disableSwipe()
    }

    // Метод для обработки клика по элементу списка (открывает подробности о нем)
    private fun onItemClick(position: Int) {
        val itemId = rvAdapter?.dataList?.get(position)?.id
        // Проверка, существует ли уже фрагмент с деталями
        val existingFragment =
            parentFragmentManager.findFragmentByTag(DetailedItemFragment.TAG)

        if (existingFragment is DetailedItemFragment) {
            // Если фрагмент уже открыт, обновляем данные
            existingFragment.updateData(itemId.toString())
        } else {
            // Если фрагмент не открыт, создаем новый
            val detailedItemFragment = DetailedItemFragment.newInstance(itemId.toString())
            parentFragmentManager.beginTransaction()
                .replace(mainContainerId, detailedItemFragment, DetailedItemFragment.TAG)
                .addToBackStack(null)
                .commit()
        }
    }

    // Метод для обработки долгого клика по элементу списка (открывает диалог для удаления)
    private fun onLongItemClick(position: Int) {
        val dialog = DeleteDialogFragment(position, rvAdapter)
        dialog.show(parentFragmentManager, "DeleteDialog")
    }

    // Метод для отображения BottomSheet при нажатии на FAB
    private fun showBottomSheet() {
        viewBinding?.run {
            fab.setOnClickListener {
                val bottomSheet = BottomSheetFragment(rvAdapter)
                bottomSheet.show(parentFragmentManager, "MyBottomSheetDialog")
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}
