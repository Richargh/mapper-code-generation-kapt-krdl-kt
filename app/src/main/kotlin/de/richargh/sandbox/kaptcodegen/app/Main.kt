package de.richargh.sandbox.kaptcodegen.app

import de.richargh.sandbox.kaptcodegen.annnotations.Mapper

fun main(args: Array<String>) {
    println("Hello ${HelloMapper().map(Hello("none", 42, Person("John"), Language.en))}")
    println(Car().greet())
}

@Mapper
data class Hello(val name: String, val messageCount: Int, val person: Person, val language: Language)

data class Person(val name: String)

sealed class Language {
    object en: Language()
    data class other(val name: String): Language()
}