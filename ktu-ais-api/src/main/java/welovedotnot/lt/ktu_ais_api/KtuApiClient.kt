package welovedotnot.lt.ktu_ais_api

import welovedotnot.lt.ktu_ais_api.handlers.DataHandler
import welovedotnot.lt.ktu_ais_api.handlers.LoginHandler
import welovedotnot.lt.ktu_ais_api.models.YearModel

/**
 * Created by simonas on 9/3/17.
 */

object KtuApiClient {

    /**
     * Gets authentication cookies.
     *
     * @return Authentication creds and user info
     */
    fun login(username: String, password: String)
            = LoginHandler().getAuthCookies(username, password)

    /**
     * Gets all marks for requested year.
     * TODO Group marks by the week.
     *
     * @param studCookie response from loginModel used for authentication.
     * @param yearModel from loginModel
     *
     *
     * @return List marks
     */
    fun getGrades(studCookie: String, yearModel: YearModel)
            = DataHandler().getGrades(studCookie, yearModel)

    /**
     * Gets all marks for requested year.
     * TODO Group marks by the week.
     *
     * @param studCookie response from loginModel used for authentication.
     * @param year year of marks
     * @param yearId internal KTU year id.
     *
     * @return List marks
     */
    fun getGrades(studCookie: String, year: String, yearId: String)
            = DataHandler().getGrades(studCookie, year, yearId)
}