package de.richargh.sandbox.kaptcodegen.app

import de.richargh.sandbox.kaptcodegen.annnotations.Interesting

@Interesting
fun main(args: Array<String>) {
    println("The compiler should have told you I am interesting")
}