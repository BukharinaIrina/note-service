package ru.netology

fun <T> size(t: List<T>): Int {
    return t.size
}

fun <T> display(t: T) {
    println(t)
}

fun <T> displayList(t: List<T>) {
    for (i in t.indices) {
        display(t[i])
    }
}