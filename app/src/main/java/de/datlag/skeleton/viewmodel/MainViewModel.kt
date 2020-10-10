package de.datlag.skeleton.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.datlag.skeleton.commons.mutableLiveData
import de.datlag.skeleton.data.RecyclerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    var listData: MutableLiveData<MutableList<RecyclerItem>> = mutableLiveData {
        repeat(10) {
            delay(1000)
            val list = latestValue ?: mutableListOf()
            list.add(RecyclerItem("Data ${list.size}"))
            emit(list)
        }
    }

    private var currentJob: Job? = null

    fun reload() {
        listData.value = mutableListOf()
        currentJob?.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            repeat(10) {
                delay(1000)
                val list = listData.value ?: mutableListOf()
                list.add(RecyclerItem("Data ${list.size}"))
                withContext(Dispatchers.Main) {
                    listData.value = list
                }
            }
        }
    }
}
