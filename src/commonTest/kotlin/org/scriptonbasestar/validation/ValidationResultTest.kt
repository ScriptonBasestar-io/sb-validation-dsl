package org.scriptonbasestar.validation

import org.scriptonbasestar.validation.constraint.maxLength
import org.scriptonbasestar.validation.constraint.minLength
import org.scriptonbasestar.validation.constraint.pattern
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ValidationResultTest {

    @Test
    fun singleValidation() {
        val validation = Validation<Person> {
            Person::name {
                minLength(1)
            }

            Person::addresses onEach {
                Address::city {
                    City::postalCode {
                        minLength(4)
                        maxLength(5)
                        pattern("\\d{4,5}") hint ("must be a four or five digit number")
                    }
                }
            }
        }

        val result = validation(Person("", addresses = listOf(Address(City("", "")))))
        println(result)
        assertEquals(3, result.errors.values.flatten().size)

        val keyName = ".name"
        assertNotNull(result.errors[keyName])
        assertEquals("must have at least 1 characters", result.errors[keyName]!!.first().message)

        val keyPostalCode = ".addresses[0].city.postalCode"
        assertEquals("must have at least 4 characters", result.errors[keyPostalCode]!!.first().message)
        assertEquals("must be a four or five digit number", result.errors[keyPostalCode]!!.last().message)
    }

    private data class Person(val name: String, val addresses: List<Address>)
    private data class Address(val city: City)
    private data class City(val postalCode: String, val cityName: String)
}
