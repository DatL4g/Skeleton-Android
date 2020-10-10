package de.datlag.skeleton.commons

fun <T> List<T>.copyOf(): List<T> = this.mutableCopyOf().toList()

fun <T> List<T>.mutableCopyOf(): MutableList<T> {
    val original = this
    return mutableListOf<T>().apply { addAll(original) }
}
