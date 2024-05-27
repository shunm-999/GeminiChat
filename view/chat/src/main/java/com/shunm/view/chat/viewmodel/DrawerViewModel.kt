package com.shunm.view.chat.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shunm.domain.chat.usecase.GetThreadListUseCase
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.Ok
import com.shunm.view.chat.uiState.DrawerUiState
import com.shunm.view.chat.uiState.DrawerUiStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DrawerViewModel
    @Inject
    constructor(
        private val getThreadListUseCase: GetThreadListUseCase,
    ) : ViewModel(), DrawerUiStateHolder {
        override var uiState: DrawerUiState by mutableStateOf(DrawerUiState(emptyList()))

        init {
            viewModelScope.launch {
                getThreadListUseCase().collectLatest {
                    when (it) {
                        is Ok -> {
                            uiState =
                                DrawerUiState(
                                    threadList = it.value,
                                )
                            println("⭐️ DrawerViewModel: ${uiState.threadList}")
                        }

                        is Err -> {
                            println("⭐️ DrawerViewModel: ${it.error}")
                        }
                    }
                }
            }
        }
    }
