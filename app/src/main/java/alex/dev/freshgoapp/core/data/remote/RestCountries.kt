package alex.dev.freshgoapp.core.data.remote

import alex.dev.freshgoapp.core.data.entities.RestCountriesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RestCountries {
    @GET("v3.1/all")
    suspend fun getAll(
        @Query("fields") fields: String = "name, idd,flags,cca2"
    ):List<RestCountriesDto>
}