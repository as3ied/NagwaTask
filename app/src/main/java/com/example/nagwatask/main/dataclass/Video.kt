package com.example.nagwatask.main.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(val name: String, val type: String, val url:String,val id:Int):Parcelable {
    var progress: Int = -1
    var state: String = ""
    var downloadId: Long = -1
}