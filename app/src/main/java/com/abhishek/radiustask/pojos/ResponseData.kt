package com.abhishek.radiustask.pojos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseData {
    @SerializedName("exclusions")
    @Expose
    var exclusions: List<List<Exclusions>>? = null

    @SerializedName("facilities")
    @Expose
    var facilities: List<Facilities>? = null
}