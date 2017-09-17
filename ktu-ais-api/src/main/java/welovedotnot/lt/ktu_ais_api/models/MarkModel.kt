package welovedotnot.lt.ktu_ais_api.models

/**
 * Created by simonas on 9/16/17.
 */

class MarkModel(
        val name: String,
        val id: String,
        val semester: String,
        val module_code: String,
        val module_name: String,
        val semester_number: String,
        val credits: String,
        val language: String,
        val profestor: String,
        val typeId: String,
        val type: String,
        val week: String,
        val mark: List<String>
)