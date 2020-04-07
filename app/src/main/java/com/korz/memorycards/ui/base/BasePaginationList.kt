package ru.id_east.gm.ui.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BasePaginationList<R, T>(
    val scope: CoroutineScope,
    val request: suspend (Int) -> R?,
    val listFromPage: suspend (R) -> List<T>,
    val nextPageIndex: suspend (R) -> Int?,
    val prevPageIndex: suspend (R) -> Int?,
    val error: (Exception) -> Unit = {}
) {
    val _resultList = MutableLiveData<List<T>>()
    val resultList: LiveData<List<T>> = _resultList

    protected val _emptyList = MutableLiveData<Boolean>()
    val emptyList: LiveData<Boolean> = _emptyList

    var isPageLoading: Boolean = false
        set(value) {
            field = value
            _pageLoading.value = value
        }

    private val _pageLoading = MutableLiveData<Boolean>()
    val pageLoading: LiveData<Boolean> = _pageLoading
    var lastPageLoaded: Boolean = false
    private var nextPage: Int = 1

    private suspend fun loadPage(startAgain: Boolean = false) {
        if (startAgain) {
            nextPage = 1
        }

        val page = request(nextPage)
        if (page != null) {
            val list =
                if (prevPageIndex(page) != null) {
                    _resultList.value?.toMutableList().also {
                        it?.addAll(listFromPage(page))
                    } ?: mutableListOf()
                } else {
                    listFromPage(page)
                }
            _resultList.value = list
            lastPageLoaded = nextPageIndex(page) == null
            nextPage = nextPageIndex(page) ?: 1
        } else {
            _resultList.value = emptyList()
            nextPage = 1
            lastPageLoaded = false
        }

    }

    fun loadFirstPage() {
        _emptyList.value = null
        _resultList.value = listOf()
        execute(true)
    }

    private fun execute(startAgain: Boolean = false) {
        scope.launch {
            try {
                isPageLoading = true
                loadPage(startAgain)
            } catch (e: Exception) {
                error(e)
            } finally {
                isPageLoading = false
                _emptyList.value = _resultList.value?.isNullOrEmpty()
            }
        }
    }

    fun updateScrollPosition(lastVisiblePosition: Int, offsetLoading: Int = 1) {
        val size = _resultList.value?.size ?: 0
        if (lastVisiblePosition + offsetLoading >= size && !isPageLoading && !lastPageLoaded) {
            Log.d("WWTTFF","updateScrollPosition")
            execute()
        }
    }

}