package com.abhishek.radiustask.pojos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Options {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null
    var selectType = 0
    var clickCount = 0
        private set

    fun incClickCount() {
        clickCount += 1
    }

    fun decClickCount() {
        clickCount -= 1
    }
}