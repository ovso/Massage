package io.github.ovso.massage.utils

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SchedulerProvider {
    fun ui() = AndroidSchedulers.mainThread()
    fun io() = Schedulers.io()
}