package com.example.rxjava2.model

import io.reactivex.rxjava3.core.Observable

data class Person(val name: String){
    fun getFitnessResults(): Observable<Fitness>? {
        return Observable.create<Fitness>{ emitter ->
            emitter.onNext(Fitness(this))
            emitter.onComplete()
        }
    }
}

data class Fitness(val person: Person){
    val caloriesBurnedToday = 5
}
