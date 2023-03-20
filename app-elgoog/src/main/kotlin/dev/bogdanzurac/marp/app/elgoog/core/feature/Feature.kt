package dev.bogdanzurac.marp.app.elgoog.core.feature

interface Feature

open class KeyFeature(val key: String) : Feature {

    override fun toString(): String = key
}
