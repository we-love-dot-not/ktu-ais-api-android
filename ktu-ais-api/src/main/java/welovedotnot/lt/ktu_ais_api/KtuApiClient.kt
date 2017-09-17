package welovedotnot.lt.ktu_ais_api

import welovedotnot.lt.ktu_ais_api.handlers.DataHandler
import welovedotnot.lt.ktu_ais_api.handlers.LoginHandler
import welovedotnot.lt.ktu_ais_api.models.LoginModel
import welovedotnot.lt.ktu_ais_api.models.YearModel

/**
 * Created by simonas on 9/3/17.
 */

object KtuApiClient {

    fun login(username: String, password: String)
            = LoginHandler().getAuthCookies(username, password)

    fun getGrades(loginModel: LoginModel, yearModel: YearModel = loginModel.studentSemesters.first())
            = DataHandler().getGrades(loginModel, yearModel)
}