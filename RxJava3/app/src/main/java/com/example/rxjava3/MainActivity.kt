package com.example.rxjava3

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.rxjava3.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    val TAG = "MAIN_ACTIVITY"

    private val fullname_MutableLiveData = MutableLiveData<String>()

    private val pokemon_MutableLiveData: MutableLiveData<String> = MutableLiveData<String>()
    private val pokemonLiveData: LiveData<String>? = null

    lateinit var myMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        myMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(myMainBinding.root)

        setFullNameData("hello this is fullname")
        getFullname().observe(this, Observer {
            myMainBinding.text1.text = it
        })


        myMainBinding.button1.setOnClickListener{
            when(getFullname().value) {
                "HIDUP" -> {
                    setFullNameData("MATI")
                }
                "MATI" -> {
                    setFullNameData("NYALA")
                }
                else ->{
                    setFullNameData("MATI")
                }
            }
        }


        getPokemon().observe(this, Observer {
            myMainBinding.text2.text = it
        })

        myMainBinding.button2.setOnClickListener{
            when(getPokemon().value) {
                "HIDUP" -> {
                    setPokemon("MATI")
                }
                "MATI" -> {
                    setPokemon("NYALA")
                }
                else ->{
                    setPokemon("MATI")
                }
            }
        }

        myMainBinding.button3.setOnClickListener{
            fetchRepos(listOf("Bagus", "Anis", "Tyat"))
        }



    }

    fun setFullNameData(str: String) {
        fullname_MutableLiveData.value = str
    }
    fun getFullname(): LiveData<String> {
        return fullname_MutableLiveData
    }

    /**
     * RxJava -> MutableLiveData -> LiveData
     */
    fun setPokemon(str: String) {
        Observable.just(str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { s -> pokemon_MutableLiveData.value = s }, //onNext
                        { error -> print("Error: $error") }, //onError
                        { println("Complete") } //onComplete
                )
    }
    fun getPokemon(): LiveData<String> {
        return pokemon_MutableLiveData
    }

    /**
     * RxJava Pure
     * List -> Observable List -> subscribe to Component
     */
    fun getObservableData(list: List<String>): Observable<List<String>> {
        val myEmitter: Observable<List<String>> = Observable.create<List<String>> { emitter ->
            emitter.onNext(list)
            emitter.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        return myEmitter
    }

    private fun fetchRepos(list: List<String>) {
                getObservableData(list)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { value -> myMainBinding.text3.text = value.toString() }, //onNext
                            { error -> print("Error: $error") }, //onError
                            { println("Complete") } //onComplete
                    )
    }



    private fun createButtonClickObservable(str: String): Observable<String> {

        return Observable.create<String> { emitter ->

            myMainBinding.button4.setOnClickListener {
                emitter.onNext(str)
            }

            emitter.setCancellable {
                myMainBinding.button4.setOnClickListener(null)
            }
        }
    }


//    private fun createTextChangeObservable(): Observable<String> {
//
//        val textChangeObservable = Observable.create<String> { emitter ->
//
//            val textWatcher = object : TextWatcher {
//
//                override fun afterTextChanged(s: Editable?) = Unit
//
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
//
//                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                    s?.toString()?.let { emitter.onNext(it) }
//                }
//
//            }
//
//            queryEditText.addTextChangedListener(textWatcher)
//
//            emitter.setCancellable {
//                queryEditText.removeTextChangedListener(textWatcher)
//            }
//        }
//
//        return textChangeObservable
//                .filter { it.length >= 2 }
//                .debounce(1000, TimeUnit.MILLISECONDS)
//
//    }



}