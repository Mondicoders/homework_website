package mondicoders

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

fun Route.setupAdminRouting() {
    authenticate("auth-basic") {
        post("/create_new_homework") {
            try {
                val text = call.receiveText()
                val homework = Json.decodeFromString<Homework>(text)
                if (createHomework(homework)) {
                    val url = "http://127.0.0.1:8080/homeworks/" + homework.hwNum
                    call.respondRedirect(url)
                }
            } catch (e: SerializationException) {
                println(e)
            }
            call.respond(HttpStatusCode.InternalServerError)
        }

        post("/comment_task") {
            try {
                val text = call.receiveText()
                val homeworkCommentRequest = Json.decodeFromString<HomeworkCommentRequest>(text)
                if (commentHomework(homeworkCommentRequest)) {
                    val url = "http://127.0.0.1:8080/admin"
                    call.respondRedirect(url)
                }
            } catch (e: SerializationException) {
                println(e)
            }
            call.respond(HttpStatusCode.InternalServerError)
        }

        get("/tasks") {
            try {
                val text = call.receiveText()
                val tasksRequest = Json.decodeFromString<TasksRequest>(text)
                val filePath = config.dataDirectory.toString() + "/templates/hw" + tasksRequest.hwNum + ".json"
                val file = File(filePath)
                val textFile = file.readText()
                call.respond(Json.decodeFromString<Homework>(textFile))
            } catch (e: SerializationException) {
                println(e)
            }
            call.respond(HttpStatusCode.InternalServerError)
        }

        get("/users") {
            val users = getUsers()
            try {
                call.respond(if (users != null) Json.encodeToString(users) else HttpStatusCode.InternalServerError)
            } catch (e: SerializationException) {
                println(e)
            }
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}

