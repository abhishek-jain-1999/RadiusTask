package com.abhishek.radiustask.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhishek.radiustask.pojos.Exclusions
import com.abhishek.radiustask.pojos.ExclusionsList
import com.abhishek.radiustask.pojos.Facilities

@Dao
interface AppDao {

    @Query("SELECT * FROM facilities_table ")
    fun getAllFacilitiesData(): LiveData<List<Facilities>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFacilitiesData(Facilities: List<Facilities>)

    @Query("SELECT * FROM exclusion_table ")
    fun getAllExclusionsData(): LiveData<List<ExclusionsList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllExclusionsData(Facilities: List<ExclusionsList>)

    @Query("DELETE FROM facilities_table")
    fun deleteAllFacilitiesData()

    @Query("DELETE FROM exclusion_table")
    fun deleteAllExclusionsData()

}