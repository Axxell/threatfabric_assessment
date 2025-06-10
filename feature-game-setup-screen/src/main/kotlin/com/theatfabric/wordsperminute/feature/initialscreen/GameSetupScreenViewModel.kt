package com.theatfabric.wordsperminute.feature.initialscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GameSetupScreenViewModel @Inject constructor() : ViewModel() {
    var userName by mutableStateOf("")
    var gameId by mutableStateOf("")

    private val mutableNavigationFlow = MutableSharedFlow<String>()
    val navigationFlow = mutableNavigationFlow.asSharedFlow()

    fun onStartClicked() {
        if (userName.isNotBlank()) {
            gameId = UUID.randomUUID().toString()
            viewModelScope.launch {
                mutableNavigationFlow.emit(userName)
            }
        }
    }
}