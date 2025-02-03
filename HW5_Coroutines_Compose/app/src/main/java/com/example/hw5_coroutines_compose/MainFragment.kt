package com.example.hw5_coroutines_compose

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.hw5_coroutines_compose.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private var viewBinding: FragmentMainBinding? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.composeContainerId?.let { composeView ->
            (composeView as? ComposeView)?.setContent {
                CoroutinesChooserLayout()
            }
        }

    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun CoroutinesChooserLayout() {
        val context = LocalContext.current
        val coroutinesThreadTypes = remember {
            context.resources.getStringArray(R.array.coroutines_thread_types).toList()
        }
        val coroutinesLogic = remember {
            context.resources.getStringArray(R.array.coroutines_work_types).toList()
        }
        val threadPoolTypes = remember {
            context.resources.getStringArray(R.array.pool_thread_types).toList()
        }



        var coroutineCount by remember { mutableStateOf("") }
        var selectedThreadType by remember { mutableStateOf(coroutinesThreadTypes[0]) }
        var selectedWorkType by remember { mutableStateOf(coroutinesLogic[0]) }
        var selectedThreadPoolType by remember { mutableStateOf(threadPoolTypes[0]) }
        var expanded by remember { mutableStateOf(false) }
        var activeJobs by remember { mutableStateOf(mutableListOf<Job>()) }
        var cancelledJobsCount by remember { mutableStateOf(0) }
        var cancelOnBackground by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()
        val lifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(lifecycleOwner, activeJobs, selectedWorkType) {
            val observer = CoroutineLifecycleObserver(coroutineScope, activeJobs, selectedWorkType)
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            TextField(
                value = coroutineCount,
                onValueChange = { newText -> coroutineCount = newText },
                label = { Text(stringResource(R.string.enter_coroutines_number)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            coroutinesThreadTypes.forEach { type ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = (type == selectedThreadType),
                        onClick = { selectedThreadType = type }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = type)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            coroutinesLogic.forEach { type ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = (type == selectedWorkType),
                        onClick = { selectedWorkType = type }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = type)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                TextField(
                    value = selectedThreadPoolType,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true },
                    label = { Text(stringResource(R.string.choose_thread_pool_type)) }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    threadPoolTypes.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                selectedThreadPoolType = option
                                expanded = false
                            }
                        ) {
                            Text(text = option)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val count = coroutineCount.toIntOrNull()
                    if (count == null || count <= 0) {
                        Toast.makeText(
                            context,
                            "Введите корректное число корутин",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    val dispatcher = when (selectedThreadPoolType) {
                        "IO" -> Dispatchers.IO
                        "Default" -> Dispatchers.Default
                        "Main" -> Dispatchers.Main
                        else -> Dispatchers.Default
                    }

                    val isSequential = selectedThreadType == coroutinesThreadTypes[0]
                    if (isSequential)
                    {
                        val job = coroutineScope.launch(dispatcher) {
                            repeat(count) { index ->
                                val scopeJob = launch {
                                    performTask(index)
                                }
                                activeJobs.add(scopeJob)
                                scopeJob.join()
                            }
                        }
                        activeJobs.add(job)
                    }
                    else {
                        repeat(count) { index ->
                            val job = coroutineScope.launch(dispatcher) {
                                performTask(index)
                            }
                            activeJobs.add(job)
                        }
                    }
                    /*coroutineScope.launch(dispatcher) {
                        try {
                            if (isSequential) {
                                for (i in 1..count) {
                                    launch { performTask(i) }.also { jobs.add(it) }

                                }
                            } else {
                                repeat(count) { i ->
                                    launch { performTask(i + 1) }.also { jobs.add(it) }
                                }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Ошибка: ${'$'}{e.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }*/
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(stringResource(R.string.start_coroutines), color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val cancelled = activeJobs.count { it.isActive }
                    Log.d("activeJobs", cancelled.toString())
                    activeJobs.forEach { it.cancel() }
                    cancelledJobsCount = cancelled
                    activeJobs.clear()
                    Toast.makeText(context, "Отменено ${cancelledJobsCount} корутин", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(stringResource(R.string.stop_coroutines), color = Color.White)
            }
        }

        LaunchedEffect(cancelOnBackground) {
            if (cancelOnBackground) {
                activeJobs.forEach { it.cancel() }
            }
        }
    }

    private suspend fun performTask(index: Int) {
        println("Корутина ${index} запущена")
        delay(5000) // Симуляция тяжёлой работы
        println("Корутина ${index} завершена")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    class CoroutineLifecycleObserver(
        private val scope: CoroutineScope,
        private val jobs: List<Job>,
        private val selectedWorkType: String
    ) : LifecycleEventObserver {

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    if (selectedWorkType == "Отменять при сворачивании") {
                        jobs.forEach {
                            it.cancel()
                            println("Корутина завершена из-за сворачивания")
                        }
                    }
                }
                else -> {}
            }
        }
    }

}

