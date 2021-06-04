package io.github.voytech.tabulatexamples

import io.github.voytech.tabulate.api.builder.dsl.header
import io.github.voytech.tabulate.template.tabulate

data class User(val name: String, val lastName: String)

fun main(args: Array<String>) {
    val users: List<User> = listOf(
        User("User1", "LastName1"),
        User("User2", "User2"))
    users.tabulate("example.xlsx") {
        columns {
            column(User::name)
            column(User::lastName)
        }
        rows {
            header("First Name", "Last Name")
        }
    }
}