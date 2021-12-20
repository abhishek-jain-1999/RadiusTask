package com.abhishek.radiustask.pojos

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "exclusion_table")
data class ExclusionsList(

    @PrimaryKey
    @NonNull
    var exclusions: List<Exclusions>
) {

    companion object {
        fun toList(list: List<ExclusionsList>): MutableList<List<Exclusions>> {
            val exclusions: MutableList<List<Exclusions>> = mutableListOf()
            list.forEach {
                exclusions.apply { add(it.exclusions!!) }
            }
            return exclusions
        }
        fun toObject(list: List<List<Exclusions>>): List<ExclusionsList>{
            val exclusions: MutableList<ExclusionsList> = mutableListOf()
            list.forEach {
                exclusions.apply { add(ExclusionsList(it)) }
            }
            return exclusions
        }
    }
}
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
