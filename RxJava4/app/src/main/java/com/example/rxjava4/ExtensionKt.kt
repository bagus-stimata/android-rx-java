package com.example.rxjava4


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

/**
 * Created by wisnu on 8/7/18
 */
fun <X, Y> LiveData<X>.map(func: (source: X) -> Y) = Transformations.map(this, func)