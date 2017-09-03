package welovedotnot.lt.ktu_ais_api.api

import org.jsoup.Connection
import org.jsoup.Jsoup
import welovedotnot.lt.ktu_ais_api.models.LoginModel
import welovedotnot.lt.ktu_ais_api.models.SemesterModel

/**
 * Created by simonas on 9/3/17.
 */

class LoginHandler: BaseHandler() {

    fun getAuthCookies(username: String, password: String, callback: (LoginModel) -> Unit) {
        val autoLogin = getAutoLogin()
        val postLogin = postLogin(username, password, autoLogin)
        val agreeLogin = getAgree(postLogin)
        val postContinue = postContinue(agreeLogin)
        val loginResponse = getInfo(postContinue)
        callback.invoke(loginResponse)
    }

    private class AutoLoginResponse(
            val authState: String,
            val cookies: Map<String, String>
    )

    private class PostLoginResponse(
            val stateId: String,
            val cookies: Map<String, String>
    )

    private class AgreeResponse(
            val samlResponse: String,
            val relayState: String
    )
    private class AuthResponse(
            val studCookie: String
    )

    private fun getAutoLogin(): AutoLoginResponse {
        val url = "https://uais.cr.ktu.lt/ktuis/studautologin"
        val request = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .execute()
        val parse = request.parse()
        val select = parse.select("input[name=\"AuthState\"]")
        val attr = select[0].attr("value")
        return AutoLoginResponse(attr, request.cookies())
    }

    private fun postLogin(
            username: String,
            password: String,
            autoLoginResponse: AutoLoginResponse): PostLoginResponse {

        val url = "https://login.ktu.lt/simplesaml/module.php/core/loginuserpass.php"
        val request = Jsoup.connect(url)
                .cookies(autoLoginResponse.cookies)
                .data(mapOf(
                    "username" to username,
                    "password" to password,
                    "AuthState" to autoLoginResponse.authState
                ))
                .method(Connection.Method.POST)
                .execute()
        val parse = request.parse()
        val filteredInputList = parse.select("input").first { it.attr("name") == "StateId" }
        val stateId = filteredInputList.attr("value")
        return PostLoginResponse(stateId, request.cookies() + autoLoginResponse.cookies)
    }

    private fun getAgree(postLoginResponse: PostLoginResponse): AgreeResponse {
        val url = "https://login.ktu.lt/simplesaml/module.php/consent/getconsent.php?" +
                "StateId=${postLoginResponse.stateId}&" +
                "yes=Yes%2C%20continue%0D%0A&" +
                "saveconsent=1"
        val request = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .cookies(postLoginResponse.cookies)
                .execute()

        val parse = request.parse()
        val inputList = parse.select("input")
        val samlResponse = inputList.first { it.attr("name") == "SAMLResponse" }.attr("value")
        val relayState = inputList.first { it.attr("name") == "RelayState" }.attr("value")
        return AgreeResponse(samlResponse, relayState)
    }

    private fun postContinue(agreeResponse: AgreeResponse): AuthResponse {
        val url = "https://uais.cr.ktu.lt/shibboleth/SAML2/POST"
        val request = Jsoup.connect(url)
                .method(Connection.Method.POST)
                .data(mapOf(
                        "SAMLResponse" to agreeResponse.samlResponse,
                        "RelayState" to agreeResponse.relayState
                ))
                .execute()

        return AuthResponse(request.cookie("STUDCOOKIE"))
    }

    private fun getInfo(authResponse: AuthResponse): LoginModel {
        val url = "https://uais.cr.ktu.lt/ktuis/vs.ind_planas"
        val request = Jsoup.connect(url)
                .cookie("STUDCOOKIE", authResponse.studCookie)
                .method(Connection.Method.GET)
                .execute()

        request.charset("windows-1257")
        val parse = request.parse()
        val nameItemText = parse.select("#ais_lang_link_lt").parents().first().text()
        val studentId = nameItemText.split(' ')[0].trim()
        val studentName = nameItemText.split(' ')[1].trim()
        val studyList = mutableListOf<SemesterModel>().apply {
            val studyYears = parse.select(".ind-lst.unstyled > li > a")
            val yearRegex = "plano_metai=([0-9]+)".toRegex()
            val idRegex = "p_stud_id=([0-9]+)".toRegex()
            studyYears.forEach { yearHtml ->
                val link = yearHtml.attr("href")
                val id = idRegex.find(link)!!.groups[1]!!.value
                val year = yearRegex.find(link)!!.groups[1]!!.value
                add(SemesterModel(id, year))
            }
        }

        return LoginModel(
                studCookie = authResponse.studCookie,
                studentName =studentName,
                studentId = studentId,
                studentSemesters = studyList
        )
    }
}