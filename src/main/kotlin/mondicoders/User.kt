package mondicoders

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Route.setupUserRouting() {
    get("/user") {

    }

    get("/homework") {

    }

    get("/homework_result") {

    }

    post("/submit_homework") {

    }

    post("/submit_task") {
        try {
            val text = call.receiveText()
            val task = Json.decodeFromString<Task>(text);

        } catch (e: Exception) {
            println(e)
        }
        call.respond(HttpStatusCode.InternalServerError)
    }
}