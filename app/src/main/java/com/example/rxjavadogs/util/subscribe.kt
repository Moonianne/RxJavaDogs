package com.example.rxjavadogs.util

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

inline fun <T : Any> Observable<T>.subscribe(
    noinline onNext: (T) -> Unit = {},
    noinline onError: (Throwable) -> Unit = {},
    noinline onComplete: () -> Unit = {},
): Disposable = subscribe(
    /* onNext = */ onNext,
    /* onError = */ onError,
    /* onComplete = */ onComplete,
)
