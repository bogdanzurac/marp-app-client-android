package dev.bogdanzurac.marp.core.feature

interface Feature

open class KeyFeature(val key: String) : Feature {

    override fun toString(): String = key
}
