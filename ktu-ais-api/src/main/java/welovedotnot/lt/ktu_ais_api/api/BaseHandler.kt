package welovedotnot.lt.ktu_ais_api.api

/**
 * Created by simonas on 9/3/17.
 */

open class BaseHandler {

    protected fun <T> Class<T>.create(): T
            = RetroClient.client.create(this)

}
