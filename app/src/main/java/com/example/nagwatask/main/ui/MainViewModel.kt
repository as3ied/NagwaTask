package com.example.nagwatask.main.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nagwatask.main.connections.MainRepositoryImpl
import com.example.nagwatask.main.dataclass.Video
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: MainRepositoryImpl)  : ViewModel() {

    val vidList = MutableLiveData<List<Video>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllVids() {

        val response = repository.getAllVideos()
        response.subscribe(object : SingleObserver<List<Video>>{
            override fun onSuccess(t: List<Video>) {
                vidList.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                errorMessage.postValue(e.message)
            }

        })
    }

}