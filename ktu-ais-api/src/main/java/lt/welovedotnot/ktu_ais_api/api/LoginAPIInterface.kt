package lt.welovedotnot.ktu_ais_api.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by simonas on 9/3/17.
 */

interface LoginAPIInterface {
    @GET("https://uais.cr.ktu.lt/ktuis/studautologin")
    fun requestAutoLogin(): Call<ResponseBody>
}