package mondicoders

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

fun Route.setupUserRouting() {
    authenticate("user-auth-basic") {
        get("/stat") {
            call.respond(HttpStatusCode.OK)
        }

        get("/homework") {

        }

        get("/homework_result") {

        }

        post("/submit_homework") {
            try {
                val text = call.receiveText()
                val submitHomeworkRequest = Json.decodeFromString<SubmitHomeworkRequest>(text)
                if (submitHomework(submitHomeworkRequest)) {
                    call.respond(HttpStatusCode.OK)
                }
            } catch (e: SerializationException) {
                println(e)
            }
            call.respond(HttpStatusCode.InternalServerError)
        }

        post("/submit_task") {
            try {
                val text = call.receiveText()
                val submitTaskRequest = Json.decodeFromString<SubmitTaskRequest>(text);
                if (submitTask(submitTaskRequest)) {
                    call.respond(HttpStatusCode.OK)
                }
            } catch (e: SerializationException) {
                println(e)
            }
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}