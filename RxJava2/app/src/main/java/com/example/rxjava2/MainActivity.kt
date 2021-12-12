package com.example.rxjava2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava2.databinding.ActivityMainBinding
import com.example.rxjava2.model.Fitness
import com.example.rxjava2.model.Person
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    lateinit var myMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_main)
        myMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(myMainBinding.root)
        /**
         * OPERATORS
         *
         * 1. Belajar Bagaimana penulisan dan bagaiman ini berjalan
         */
        println("1. ================")
        Observable.just(1)
                .flatMap { Observable.just(it * 10) /* hasilnya 1x10 = 10*/  }
                .flatMap { Observable.just(it * 20) /* hasilnya 10x20 = 200 dan hasil terakhir ini yang diambil oleh karena flatMap*/ }
                /* Dua statement dibawah subscribeOn dan onserverOn membuat hasil dikerjakan
                    sesuai dari thread sistem operasi */
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(
                        { println( "The Last Result is  ${it}") },
                        { it.printStackTrace() },
                        { println("Complete") }
                )

        println("2. ================")
        Observable.just("item1")
                .map { str: String ->
                        println("inside the map =  $str")
                        str
                    }
                .subscribe { s: String? -> println(s) }

        println("3. ================")
        Observable.just("item2")
                .flatMap { str: String ->
                    println("inside the flatMap = $str")
                    Observable.just("$str+", "$str++", "$str+++")
                    }
                .subscribe { s: String? -> println(s) }

        /**
         * 2. Using Object Model
         */
        println("4. ================")
        val person = Person("Paijo")
        Observable.just(person)
                .map { str: Person ->
                        println("inside the map ${str.name}")
                        str
                    }
                .subscribe { s: Person? -> println(s!!.name) }

        println("5. ================")
        Observable.just(person)
                .flatMap { str: Person ->
                        println("inside the map ${str.name}")
                        Observable.just(person.getFitnessResults())
                    }
                .subscribe { s: Observable<Fitness>? ->
                        s!!
                            .subscribe(
                                    { s1 -> println(s1.caloriesBurnedToday) },
                                    { s2 -> println(s2.printStackTrace()) },
                                    { println("onComplete") },
                            )
                    }

        /**
         * Iterable
         */
        println("6. ================")
        val subscriber = Observable.fromIterable(listOf(1, 6, 3, 5, 8))
                // use map to transform int values to random values
                .map {
                    it * 10
                }
                .subscribe {
                        println(it.toInt())
                }

        class Athletic(val sports: PublishSubject<String>)

        // create subject object take Athletic datatype
        val subjectObserverable = PublishSubject.create<Athletic>()
        val subscriber2 = subjectObserverable.
                // use flatMap to transform athletic object to sports observable and subscribe it
        flatMap {
            it.sports
        }.subscribe {
            println(it)
        }

        // create two Athletic objects
        val sherif = Athletic(PublishSubject.create())
        val john = Athletic(PublishSubject.create())

        // pass objects to the subject
        subjectObserverable.onNext(sherif)
        subjectObserverable.onNext(john)

        sherif.sports.onNext("football")
        john.sports.onNext("volleyball")
        sherif.sports.onNext("basketball")

        // create observable emit number's types
        val numbers = Observable.just("odd", "even")

        // create subscriber to numbers: hasilnya kok hampir sama dengan flatMap (hehehe)
        val subscriber3 = numbers.concatMap { emitter ->
            when(emitter){
                "odd" -> {
                    println("**odd**")
                    Observable.just(1, 3, 5, 7, 9)
                }
                "even" -> {
                    println("**even**")
                    Observable.just(2, 4, 6, 8, 10)
                }
                else -> Observable.empty()
            }
        }.subscribe { number ->
            println(number)
        }

        val race = listOf("Alan", "Bob", "Cobb", "Dan", "Evan", "Finch")





    }

}