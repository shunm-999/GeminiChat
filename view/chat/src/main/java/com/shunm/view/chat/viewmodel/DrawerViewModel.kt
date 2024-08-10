package com.shunm.view.chat.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shunm.domain.chat.model.ThreadSummary
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
    private var currentThread: ThreadSummary? by mutableStateOf(null)
    private var threadList: List<ThreadSummary> by mutableStateOf(emptyList())

    override val uiState: DrawerUiState by derivedStateOf {
        DrawerUiState(
            currentThread = currentThread,
            threadList = threadList,
        )
    }

    init {
        viewModelScope.launch {
            getThreadListUseCase().collectLatest {
                when (it) {
                    is Ok -> {
                        threadList = it.value
                    }

                    is Err -> {
                    }
                }
            }
        }
    }

    override fun selectThread(thread: ThreadSummary) {
        currentThread = thread
    }
}
