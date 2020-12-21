package ru.alexanurin.alexanurinnavigationdrawer.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//  Общая viewModel
class SharedViewModel : ViewModel() {

    private val _updateUiDataEvent = MutableLiveData<Pair<Int, Int>>()

    //  Чтобы нельзя было изменить приватную _updateUiDataEvent. Работать только с updateUiDataEvent
    val updateUiDataEvent = _updateUiDataEvent

    fun refreshData(data: Pair<Int, Int>) {
        _updateUiDataEvent.value = data
    }
}