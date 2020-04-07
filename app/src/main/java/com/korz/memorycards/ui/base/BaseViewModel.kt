package ru.id_east.gm.ui.common

import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.id_east.gm.utils.EventLiveData

open class BaseViewModel : ViewModel() {

    protected val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress

    protected val _loadError = MutableLiveData<Throwable>()

    fun <T : Any> MutableLiveData<T>.toEventLiveData(compare: ((T?, T?) -> Boolean) = { t1, t2 -> t1 == t2 }): LiveData<T> {
        return EventLiveData<T>(this, compare)
    }

    fun loadData(finally: () -> Unit = {}, block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _progress.value = true
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                _loadError.value = e
            } finally {
                finally()
                _progress.value = false
            }
        }
    }
}

fun <T : Any> MutableLiveData<T>.toNonNullLiveData(): LiveData<T> {
    return MediatorLiveData<T>().also {
        it.addSource(this) { value ->
            if (value != null) {
                it.value = value
            }
        }
    }
}

