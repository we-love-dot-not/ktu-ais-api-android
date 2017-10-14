package lt.welovedotnot.ktu_ais_api.api

import retrofit2.Retrofit

/**
 * Created by simonas on 9/3/17.
 */

object RetroClient {
    const val BASE_URL = "https://uais.cr.ktu.lt"
    val client = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()!!
}