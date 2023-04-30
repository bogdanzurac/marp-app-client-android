package dev.bogdanzurac.marp.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType

enum class NavArgType {
    STRING_ARG,
    LONG_ARG
}

fun NavArgType.toNavigationType(): NavType<*> =
    when (this) {
        NavArgType.STRING_ARG -> NavType.StringType
        NavArgType.LONG_ARG -> NavType.LongType
    }

fun NavArg.toNavigationArgument(): NamedNavArgument =
    androidx.navigation.navArgument(name) {
        this.type = argType.toNavigationType()
        this.nullable = isNullable
        if (defaultValuePresent)
            this.defaultValue = defaultValue
    }


fun navArgument(
    builder: NavArgBuilder.() -> Unit
): NavArg = NavArgBuilder().apply(builder).build()


fun longArg(name: String): NavArg =
    navArgument {
        this.name = name
        this.type = NavArgType.LONG_ARG
    }

fun stringArg(
    name: String,
    isNullable: Boolean = false,
    default: String? = null
): NavArg =
    navArgument {
        this.name = name
        this.type = NavArgType.STRING_ARG
        this.nullable = isNullable
        if (isNullable || default != null)
            defaultValue = default
    }


class NavArgBuilder {
    /**
     * The NavType for this argument.
     *
     * If you don't set a type explicitly, it will be inferred
     * from the default value of this argument.
     */
    lateinit var name: String

    /**
     * The NavType for this argument.
     *
     * If you don't set a type explicitly, it will be inferred
     * from the default value of this argument.
     */
    lateinit var type: NavArgType

    /**
     * Controls if this argument allows null values.
     */
    var nullable: Boolean = false

    /**
     * An optional default value for this argument.
     *
     * Any object that you set here must be compatible with [type], if it was specified.
     */
    var defaultValue: Any? = null
        set(value) {
            field = value
            defaultValuePresent = true
        }

    private var defaultValuePresent = false

    /**
     * Builds the NavArgument.
     */
    fun build(): NavArg {
        return NavArg(type, name, nullable, defaultValue, defaultValuePresent)
    }
}

data class NavArg(
    val argType: NavArgType,
    val name: String,
    val isNullable: Boolean = false,
    val defaultValue: Any?,
    val defaultValuePresent: Boolean
)
