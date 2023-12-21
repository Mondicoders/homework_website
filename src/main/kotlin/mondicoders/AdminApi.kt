package mondicoders

import kotlinx.serialization.Serializable


@Serializable
data class Task(val condition: String, val answer: String, val userAnswer: String? = null, var comment: String? = null)

@Serializable
data class Homework(val hwNum: UInt, val tasks: List<Task>)

@Serializable
data class HomeworkCommentRequest(val hwNum: UInt, val taskNum: UInt, val comment: String)

@Serializable
data class TasksRequest(val hwNum: UInt)