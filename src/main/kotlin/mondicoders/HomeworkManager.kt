package mondicoders

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

fun createHomework(homework: Homework): Boolean {
    try {
        val outputFilePath = config.dataDirectory.toString() + "/templates/hw" + homework.hwNum + ".json"
        val file = File(outputFilePath)
        file.writeText(Json.encodeToString(homework))
        return true
    } catch (e: Exception) {
        println(e)
    }
    return false
}

fun commentHomework(homeworkCommentRequest: HomeworkCommentRequest): Boolean {
    try {
        val filePath = config.dataDirectory.toString() + "/templates/hw" + homeworkCommentRequest.hwNum + ".json"
        val file = File(filePath)
        val text = file.readText()
        val jsonObject = Json.decodeFromString<Homework>(text)
        jsonObject.tasks[homeworkCommentRequest.taskNum.toInt() - 1].comment = homeworkCommentRequest.comment
        file.writeText(Json.encodeToString(jsonObject))
        return true
    } catch (e: Exception) {
        println(e)
    }
    return false
}

fun getUsers(): UsersResponse? {
    try {
        val file = config.usersPath.toFile()
        val text = file.readText()
        val jsonObject = Json.decodeFromString<UsersResponse>(text)
        return jsonObject
    } catch (e: Exception) {
        println(e)
    }
    return null
}

fun submitTask(submitTaskRequest: SubmitTaskRequest): Boolean {
    try {
        val filePath =
            config.dataDirectory.toString() + "/users/" + "" + "hw" + submitTaskRequest.hwNum + ".json" // TODO: add auth folder (replace empty string)
        val file = File(filePath)
        val text = file.readText()
        val jsonObject = Json.decodeFromString<Homework>(text)
        jsonObject.tasks[submitTaskRequest.taskNum.toInt() - 1].userAnswer = submitTaskRequest.userAnswer
        file.writeText(Json.encodeToString(jsonObject))
        return true
    } catch (e: Exception) {
        println(e)
    }
    return false
}

fun submitHomework(submitHomeworkRequest: SubmitHomeworkRequest): Boolean {
    try {
        val filePath =
            config.dataDirectory.toString() + "/users/" + "" + "hw" + submitHomeworkRequest.hwNum + ".json" // TODO: add auth folder (replace empty string)
        val file = File(filePath)
        val text = file.readText()
        // TODO
    } catch (e: Exception) {
        println(e)
    }
    return false
}