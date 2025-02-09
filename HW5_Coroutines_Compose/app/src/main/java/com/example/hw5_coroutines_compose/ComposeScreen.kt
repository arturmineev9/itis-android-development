package com.example.hw5_coroutines_compose

import android.content.Context
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
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoroutinesScreen() {
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

    DisposableEffect(Unit) {
        val lifecycleOwner = context as LifecycleOwner
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP && selectedWorkType == coroutinesLogic[0]) {
                cancelledJobsCount = activeJobs.count { it.isActive }
                activeJobs.forEach { it.cancel() }
                activeJobs.clear()
                Toast.makeText(
                    context,
                    context.getString(R.string.coroutines_cancelled, cancelledJobsCount),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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
                        context.getString(R.string.invalid_coroutine_count),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                val dispatcher = when (selectedThreadPoolType) {
                    context.getString(R.string.thread_pool_io) -> Dispatchers.IO
                    context.getString(R.string.thread_pool_default) -> Dispatchers.Default
                    context.getString(R.string.thread_pool_main) -> Dispatchers.Main
                    else -> Dispatchers.Default
                }

                val isSequential = selectedThreadType == coroutinesThreadTypes[0]
                Toast.makeText(
                    context,
                    context.getString(R.string.start_coroutines),
                    Toast.LENGTH_SHORT
                ).show()
                if (isSequential) {
                    val job = coroutineScope.launch(dispatcher) {
                        repeat(count) { index ->
                            val scopeJob = launch {
                                performTask(index, context)
                            }
                            activeJobs.add(scopeJob)
                            scopeJob.join()
                        }
                    }
                    activeJobs.add(job)
                } else {
                    repeat(count) { index ->
                        val job = coroutineScope.launch(dispatcher) {
                            performTask(index, context)
                        }
                        activeJobs.add(job)
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.start_coroutines),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = {
                val active = activeJobs.count { it.isActive }
                if (active == 0) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.no_active_coroutines),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    activeJobs.forEach { it.cancel() }
                    cancelledJobsCount = active
                    activeJobs.clear()
                    Toast.makeText(
                        context,
                        context.getString(R.string.coroutines_cancelled, cancelledJobsCount),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.stop_coroutines),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    LaunchedEffect(cancelOnBackground) {
        if (cancelOnBackground) {
            activeJobs.forEach { it.cancel() }
        }
    }
}



private suspend fun performTask(index: Int, context: Context) {
    println(context.getString(R.string.coroutine_started, index))
    delay(5000L)
    println(context.getString(R.string.coroutine_finished, index))
}
