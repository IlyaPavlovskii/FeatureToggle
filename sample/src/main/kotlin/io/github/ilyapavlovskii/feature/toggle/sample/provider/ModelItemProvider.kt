package io.github.ilyapavlovskii.feature.toggle.sample.provider

import io.github.ilyapavlovskii.feature.toggle.sample.R
import io.github.ilyapavlovskii.feature.toggle.sample.model.MenuItem

internal interface ModelItemProvider {
    fun getItems(): List<MenuItem>

    companion object {
        fun create(): ModelItemProvider = DefaultModelItemProvider()
    }
}

private class DefaultModelItemProvider : ModelItemProvider {
    override fun getItems(): List<MenuItem> {
        return listOf(
            MenuItem(
                icon = R.drawable.ic_burger,
                title = R.string.menu_item__burger,
                price = "9.99 $",
            ),
            MenuItem(
                icon = R.drawable.ic_pizza,
                title = R.string.menu_item__pizza,
                price = "11.0 $",
            ),
            MenuItem(
                icon = R.drawable.ic_kebab,
                title = R.string.menu_item__kebab,
                price = "4.5 $",
            ),
            MenuItem(
                icon = R.drawable.ic_salad,
                title = R.string.menu_item__salad,
                price = "10 $",
            ),
            MenuItem(
                icon = R.drawable.ic_soup,
                title = R.string.menu_item__soup,
                price = "11 $",
            ),
            MenuItem(
                icon = R.drawable.ic_sushi,
                title = R.string.menu_item__sushi,
                price = "12.3 $",
            ),
            MenuItem(
                icon = R.drawable.ic_dumpling,
                title = R.string.menu_item__dumpling,
                price = "5 $",
            ),
        )
    }
}