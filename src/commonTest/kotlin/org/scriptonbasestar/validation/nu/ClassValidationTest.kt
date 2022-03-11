package org.scriptonbasestar.validation.nu

import org.scriptonbasestar.validation.Validation
import org.scriptonbasestar.validation.constraint.pattern
import kotlin.test.Test

class ClassValidationTest {
    data class User(val username: String, val password: String, val email: String?, val servers: List<Server>)
    data class Server(
        val name: String,
        val serial: String,
        val config: Map<String, Any>,
        val prop: Map<String, String?>,
    )

    @Test
    fun userTest() {
        val validatation = Validation<User> {
            User::username {
                pattern("jkdf")
            }
        }
    }
}
