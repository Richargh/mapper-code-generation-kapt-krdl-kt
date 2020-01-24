package de.richargh.sandbox.kaptcodegen.app

import de.richargh.sandbox.kaptcodegen.annnotations.GenName

fun main(args: Array<String>) {
    println("Hello ${Generated_Hello().getName()}")
    println(Car().greet())
}

@GenName
class Hello