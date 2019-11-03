package app.tgayle.bball.ui

sealed class ItemOrAd<Item> {
    class Item<T>(val item: T) : ItemOrAd<T>()
    class Ad<T> : ItemOrAd<T>()
}