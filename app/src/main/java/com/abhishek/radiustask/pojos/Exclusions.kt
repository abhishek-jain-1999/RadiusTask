package com.abhishek.radiustask.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Exclusions(
    @SerializedName("options_id")
    @Expose
    var options_id: Int? = null,

    @SerializedName("facility_id")
    @Expose
    var facility_id: Int? = null
)
/*class Exclusions {
    @SerializedName("options_id")
    @Expose
    var options_id: Int? = null

    @SerializedName("facility_id")
    @Expose
    var facility_id: Int? = null

    @PrimaryKey(autoGenerate = true)
    var preId: Int? = null
}*/
