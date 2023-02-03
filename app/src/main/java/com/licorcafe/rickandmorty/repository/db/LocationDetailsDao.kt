package com.licorcafe.rickandmorty.repository.db

import androidx.room.*
import com.licorcafe.rickandmorty.repository.db.model.LocationDetailsEntity

@Dao
interface LocationDetailsDao {
    @Query("SELECT * FROM location_details WHERE url = :locationUrl LIMIT 1")
    fun getLocationDetailsEntity(locationUrl: String): LocationDetailsEntity

    @Query("SELECT * FROM location_details")
    fun geAllLocationDetailsEntity(): List<LocationDetailsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocationDetailsEntity(locationDetailsEntity: LocationDetailsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocationsDetailsEntity(locationDetailsEntityList: List<LocationDetailsEntity>)

    @Delete
    fun deleteLocationDetailsEntity(locationDetailsEntity: LocationDetailsEntity)
}