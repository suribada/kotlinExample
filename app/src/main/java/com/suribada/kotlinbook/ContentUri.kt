package com.suribada.kotlinbook

/**
 * Created by Noh.Jaechun on 2017. 7. 27..
 */
class Uri(var scheme : String = "http", var host:String? = null, var path : String? = null, var query:String?) {
    operator fun plusAssign(pair : Pair<String, String>) {
        query.takeIf {  true }
        query.takeUnless { false }
    }
}