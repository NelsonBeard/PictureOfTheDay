package com.geekbrains.pictureoftheday.Model.meteorite

import com.google.gson.annotations.SerializedName
import java.util.*

class MeteoriteServerResponseData(
    @SerializedName("near_earth_objects") val near_earth_objects: NearEarthObjects
) {
    data class NearEarthObjects(

        @SerializedName("2022-02-11") val meteorites: List<Meteorite>
    ) {
        data class Meteorite(
            @SerializedName("id") val id: String,
            @SerializedName("name") val name: String,
            @SerializedName("estimated_diameter") val estimated_diameter: EstimatedDiameter,
            @SerializedName("is_potentially_hazardous_asteroid") val hazardous: Boolean,
            @SerializedName("close_approach_data") val close_approach_data: List<CloseApproach>
        ) {
            data class EstimatedDiameter(
                @SerializedName("meters") val meters: Meters
            ) {
                data class Meters(
                    @SerializedName("estimated_diameter_max") val estimated_diameter_max: Double
                )
            }

            data class CloseApproach(
                @SerializedName("close_approach_date_full") val close_approach_date_full: String,
                @SerializedName("relative_velocity") val relative_velocity: RelativeVelocity,
                @SerializedName("miss_distance") val miss_distance: MissDistance
            ) {
                data class RelativeVelocity(
                    @SerializedName("kilometers_per_second") val kilometers_per_second: String,
                )

                data class MissDistance(
                    @SerializedName("kilometers") val kilometers: String,
                )
            }
        }
    }
}

