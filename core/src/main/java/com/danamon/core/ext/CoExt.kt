package com.danamon.core.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun FragmentActivity.getLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit,
) =
    lifecycleScope.launch(context, start) { block.invoke(this) }

fun Fragment.getLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit,
) =
    viewLifecycleOwner.lifecycleScope.launch(context, start) { block.invoke(this) }

fun ViewModel.getLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit,
) =
    viewModelScope.launch(context, start) { block.invoke(this) }

suspend fun <R, T> getLaunchDefault(
    defaultData: T,
    mutableLiveData: MutableLiveData<R>,
    data: R,
    dataNew: R,
    isWithCompare: Boolean = true,
): R {
    mutableLiveData.postValue(data)
    delay(500)
    if (isWithCompare && defaultData != dataNew) mutableLiveData.postValue(dataNew)
    else mutableLiveData.postValue(dataNew)
    return dataNew
}
