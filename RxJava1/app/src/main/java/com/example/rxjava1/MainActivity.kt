package com.example.rxjava1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava1.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    val text1: TextView by lazy {
       findViewById(R.id.text1)
    }
    val text2: TextView by lazy {
        findViewById(R.id.text2)
    }

    lateinit var myMainBinding: ActivityMainBinding

    lateinit var myObservable: Observable<String>
    lateinit var myObserver: Observer<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        myMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(myMainBinding.root)

        /**
         * 1. Paling simple. Observable(emitter) juga sebagai Observer(subscriber)
         */
        Observable.just("Hello World")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { value -> text1.text = "Hello: $value" }, //onNext
                        { error -> print("Error: $error")}, //onError
                        { println("Complete")} //onComplete
                )
        Observable.fromArray("Bagus", "Anis", "Tyat", "Ari")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread()) //supaya tampilan ditampilkan dahulu -> lalu kalau berhasil subscribe baru ditampilkan
                .filter{ item -> item.contains("Ba") }
                .subscribe{
                    onNext -> text2.text = "Hasil Pencarian ${onNext?.let { it }}"
                }

        /**
         * 2. Penngembangan dari cara pertama yaitu menggunakan "Create"
         */
        val statusResult = Observable.create<String> { emitter ->
            emitter.onNext("Hello Tiga Menggunakan Emmiter")
            emitter.onComplete()
        }
                .subscribe(
                        { s -> myMainBinding.text3.text = s },
                        { e -> myMainBinding.text3.text = e.toString() },
                        { println("onComplete") }
                )
        myMainBinding.apply {
            text4.text = statusResult.toString()
        }

        /**
         * 3. Meggunakan emitter(Observerable) dan subscriber(Observer)
         */
        val emitter1: Observable<String> = Observable.create<String> { emitter ->
            emitter.onNext("Menggunakan Emiter dan Subscriber sebagai penerima umpan balik")
            emitter.onComplete()
        }
        val mySubscriber1 = object: Observer<String> {
            override fun onNext(s: String) {
                println(s)
                myMainBinding.text6.text = s
            }

            override fun onComplete() {
                println("onComplete")
            }

            override fun onError(e: Throwable) {
                println("onError")
            }

            override fun onSubscribe(s: Disposable) {
                println("onSubscribe")
            }
        }
        emitter1.subscribe(mySubscriber1)

        val emitter2 = createStringEmmiter()
        val subscriber2 = createStringSubscriber()
        emitter2.subscribe(subscriber2)


        val observableNews : Observable<List<String>> = Observable.create { emitter ->
            val news = mutableListOf<String>()
            // api call...
            news.add("Satu")
            news.add("Dua")
            emitter.onNext(news)
            emitter.onComplete()
        }

    }


    fun getNews(): Observable<List<String>> {
        return Observable.create {
            subscriber ->

            val news = mutableListOf<String>()
            // api call...
            subscriber.onNext(news)
            subscriber.onComplete()
        }
    }



    fun createStringEmmiter(): Observable<String> {
        val myEmitter: Observable<String> = Observable.create<String> { emitter ->
            emitter.onNext("Menggunakan Emiter dan Subscriber sebagai penerima umpan balik (FUNCTION)")
            emitter.onComplete()
        }
        return myEmitter
    }

    fun createStringSubscriber(): Observer<String> {
        val mySubscriber = object: Observer<String> {
            override fun onNext(s: String) {
                println(s)
                myMainBinding.text7.text = s
            }

            override fun onComplete() {
                println("onComplete")
            }

            override fun onError(e: Throwable) {
                println("onError")
            }

            override fun onSubscribe(s: Disposable) {
                println("onSubscribe")
            }
        }
        return mySubscriber
    }

}