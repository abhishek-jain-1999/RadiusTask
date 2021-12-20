package com.abhishek.radiustask.pojos

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "facilities_table")
data class Facilities(
    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("options")
    @Expose
    var options: List<Options>? = null,

    @SerializedName("facility_id")
    @Expose
    @PrimaryKey
    @NonNull
    var facility_id: Int
    )
/*
public class Facilities {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("options")
    @Expose
    var options: List<Options>? = null

    @SerializedName("facility_id")
    @Expose
    var facility_id: Int? = null

    @PrimaryKey(autoGenerate = true)
    var preId: Int? = null
}*/
