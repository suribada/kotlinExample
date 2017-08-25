package com.suribada.kotlinbook

import android.util.Range

/**
 * Created by Noh.Jaechun on 2017. 7. 26..
 */
const val ASTERISK = "*"

fun parseRange(input: String) : IntRange? {
    var splits = input.split("-");
    return when {
        input == ASTERISK -> IntRange(Integer.MIN_VALUE, Integer.MAX_VALUE)
        splits.size == 1 -> throw IllegalArgumentException("not legal")
        splits[0] == ASTERISK -> IntRange(Integer.MIN_VALUE, splits[1].toInt())
        splits[1] == ASTERISK -> IntRange(splits[0].toInt(), Integer.MAX_VALUE)
        else -> IntRange(splits[0].toInt(), splits[1].toInt())
    }
}

fun asSet(input: String) : Set<String> {
    return input.split("|").toSet()
}

class Device(val apiLevel:IntRange?, val appVersion:IntRange?, val webViewVersion:IntRange?,
             val vendors:Set<String>, val models:Set<String>) {
    companion object {
        fun parse(input : String) : Device? {
            var splits = input.split(",");
            if (splits.size != 5) {
                throw IllegalArgumentException("needs 5 size")
            }
            var apiLevel : IntRange? = parseRange(splits[0])
            var appVersion : IntRange? = parseRange(splits[1])
            var webViewVersion : IntRange? = parseRange(splits[2])
            var vendors : Set<String> = asSet(splits[4])
            var models : Set<String> = asSet(splits[4])
            return Device(apiLevel, appVersion, webViewVersion, vendors, models)
        }
    }
}