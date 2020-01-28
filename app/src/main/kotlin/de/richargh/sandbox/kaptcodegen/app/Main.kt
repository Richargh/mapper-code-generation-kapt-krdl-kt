package de.richargh.sandbox.kaptcodegen.app

import de.richargh.sandbox.kaptcodegen.annnotations.Mapper

fun main(args: Array<String>) {
    println("Hello ${HelloMapper().map(Hello("none", 42, Person("John")))}")
    println(Car().greet())
}

@Mapper
data class Hello(val name: String, val messageCount: Int, val person: Person)

data class Person(val name: String)