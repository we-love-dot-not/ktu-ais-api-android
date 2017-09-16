package welovedotnot.lt.ktu_ais_api

import welovedotnot.lt.ktu_ais_api.handlers.LoginHandler
import welovedotnot.lt.ktu_ais_api.models.LoginModel

/**
 * Created by simonas on 9/3/17.
 */

object KtuApiClient {

    fun login(username: String, password: String, callback: (LoginModel) -> Unit) {
        LoginHandler().getAuthCookies(username, password, callback)
    }

    fun getGrades() {

    }
}