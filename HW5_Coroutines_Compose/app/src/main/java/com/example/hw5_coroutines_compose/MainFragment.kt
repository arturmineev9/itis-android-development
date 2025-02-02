package com.example.hw5_coroutines_compose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.Preview
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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hw5_coroutines_compose.databinding.FragmentMainBinding
import kotlinx.coroutines.FlowPreview


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

        var text by remember { mutableStateOf("") }
        var selectedThreadType by remember { mutableStateOf(coroutinesThreadTypes[0]) }
        var selectedWorkType by remember { mutableStateOf(coroutinesLogic[0]) }
        var selectedThreadPoolType by remember { mutableStateOf(threadPoolTypes[0]) }
        var expanded by remember { mutableStateOf(false) } // Состояние раскрытия меню

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            TextField(
                value = text,
                onValueChange = { newText -> text = newText },
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
                onClick = { Toast.makeText(requireContext(), R.string.start_coroutines, Toast.LENGTH_SHORT).show() },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(stringResource(R.string.start_coroutines), color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { Toast.makeText(requireContext(), R.string.stop_coroutines, Toast.LENGTH_SHORT).show() },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(stringResource(R.string.stop_coroutines), color = Color.White)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}