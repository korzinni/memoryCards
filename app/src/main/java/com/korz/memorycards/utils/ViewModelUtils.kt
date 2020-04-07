package ru.id_east.gm.utils

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import java.util.*

class EventLiveData<T>(
    private val src: MutableLiveData<T>,
    private val compare: (T?, T?) -> Boolean,
    private val delay: Int = 200
) : MediatorLiveData<T>() {

    var lastUpdate = 0L
    override fun onActive() {
        super.onActive()
        addSource(src) { srcValue ->
            val currentTime = Calendar.getInstance().time.time
            if (!compare(srcValue, value) && currentTime > lastUpdate + delay) {
                lastUpdate = currentTime
                value = srcValue
                src.value = null
            }
        }
    }

    override fun onInactive() {
        removeSource(src)
        value = null
        super.onInactive()
    }
}

fun <T> LiveData<T>.notNullObserve(owner: LifecycleOwner, block: (T) -> Unit) {
    this.observe(owner, Observer { it -> if (it != null) block(it) })
}

fun <T> LiveData<T>.notNullResumeObserve(owner: LifecycleOwner, block: (T) -> Unit) {
    this.observe(
        owner,
        Observer { it ->
            if (it != null && owner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) block(
                it
            )
        })
}

class NonNullLiveData<T>(
    private val src: MutableLiveData<T>
) : MediatorLiveData<T>() {
    override fun onActive() {
        super.onActive()
        addSource(src) { srcValue ->
            if (srcValue != null) {
                value = srcValue
            }
        }
    }

    override fun onInactive() {
        removeSource(src)
        super.onInactive()
    }
}

