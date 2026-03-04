package com.example.pleasework.presentation.spaceX.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pleasework.feature.spaceX.data.model.ResultState
import com.example.pleasework.feature.spaceX.domain.model.RocketLaunch
import com.example.pleasework.feature.spaceX.domain.usecase.IGetLaunchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RocketLaunchViewModel(private val getLaunchesUseCase: IGetLaunchesUseCase) : ViewModel() {
    private val _state = MutableStateFlow<ResultState<List<RocketLaunch>>>(ResultState.Loading)
    val state: StateFlow<ResultState<List<RocketLaunch>>> = _state

    init {
        loadLaunches()
    }

    fun loadLaunches(forceReload: Boolean = false) {
        viewModelScope.launch {
            _state.value = ResultState.Loading
            _state.value = getLaunchesUseCase.invoke(forceReload)
        }
    }
}