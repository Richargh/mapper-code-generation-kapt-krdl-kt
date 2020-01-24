package de.richargh.sandbox.kaptcodegen.app

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CarTest {
    @Test
    fun `generated extension method exists and returns a result`() {
        // arrange
        val car = Car()

        // act
        val result = car.toJson()

        // assert
        assertThat(result).isEqualTo("{ }")
    }
}