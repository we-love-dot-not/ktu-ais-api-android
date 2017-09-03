package welovedotnot.lt.ktu_ais_api.models

/**
 * Created by simonas on 9/3/17.
 */

data class LoginModel(
        val studCookie: String,
        val studentName: String,
        val studentId: String,
        val studentSemesters: List<SemesterModel>
)