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
        jsonObject.tasks[homeworkCommentRequest.hwNum.toInt() - 1].comment = homeworkCommentRequest.comment
        file.writeText(Json.encodeToString(jsonObject))
    } catch (e: Exception) {
        println(e)
    }
    return false
}

fun submitTask(task: Task): Boolean {
    try {
        val filePath = config.dataDirectory.toString() + "/users/"
    }
}