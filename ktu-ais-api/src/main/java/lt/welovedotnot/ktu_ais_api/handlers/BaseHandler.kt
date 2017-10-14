package lt.welovedotnot.ktu_ais_api.handlers

import lt.welovedotnot.ktu_ais_api.api.RetroClient

/**
 * Created by simonas on 9/3/17.
 */

open class BaseHandler {

    protected fun <T> Class<T>.create(): T
            = RetroClient.client.create(this)

}
