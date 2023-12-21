package mondicoders

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException
import kotlin.RuntimeException

inline fun <reified T> modifyJsonFile(filePath: String, function: (T) -> Unit): Boolean {
    try {
        val file = File(filePath)
        val text = file.readText()
        val jsonObject = Json.decodeFromString<T>(text)
        function(jsonObject)
        file.writeText(Json.encodeToString(jsonObject))
        return true
    } catch (e: RuntimeException) {
        println(e)
    }
    return false
}

fun createHomework(homework: Homework): Boolean {
    try {
        val outputFilePath = config.dataDirectory.toString() + "/templates/hw" + homework.hwNum + ".json"
        val file = File(outputFilePath)
        file.writeText(Json.encodeToString(homework))
        return true
    } catch (e: RuntimeException) {
        println(e)
    }
    return false
}

fun commentHomework(homeworkCommentRequest: HomeworkCommentRequest): Boolean {
    return modifyJsonFile(config.dataDirectory.toString() + "/templates/hw" + homeworkCommentRequest.hwNum + ".json") { obj: Homework ->
        obj.tasks[homeworkCommentRequest.taskNum.toInt() - 1].comment = homeworkCommentRequest.comment
    }
}

fun getUsers(): UsersResponse? {
    try {
        val file = config.usersPath.toFile()
        val text = file.readText()
        return Json.decodeFromString<UsersResponse>(text)
    } catch (e: RuntimeException) {
        println(e)
    }
    return null
}

// TODO: add auth folder (replace empty string)
fun submitTask(submitTaskRequest: SubmitTaskRequest): Boolean {
    return modifyJsonFile(config.dataDirectory.toString() + "/users/" + "" + "hw" + submitTaskRequest.hwNum + ".json") { obj: Homework ->
        obj.tasks[submitTaskRequest.taskNum.toInt() - 1].userAnswer = submitTaskRequest.userAnswer
    }
}

fun submitHomework(submitHomeworkRequest: SubmitHomeworkRequest): Boolean {
    try {
        val filePath =
            config.dataDirectory.toString() + "/users/" + "" + "hw" + submitHomeworkRequest.hwNum + ".json" // TODO: add auth folder (replace empty string)
        val file = File(filePath)
        val text = file.readText()
        // TODO
    } catch (e: IOException) {
        println(e)
    }
    return false
}