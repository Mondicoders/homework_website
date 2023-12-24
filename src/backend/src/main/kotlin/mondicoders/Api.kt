package mondicoders

import kotlinx.serialization.Serializable


@Serializable
data class Task(val condition: String, val answer: String, var userAnswer: String? = null, var comment: String? = null)

@Serializable
data class Homework(val hwNum: UInt, val tasks: List<Task>)

@Serializable
data class HomeworkCommentRequest(val hwNum: UInt, val taskNum: UInt, val comment: String)

@Serializable
data class TasksRequest(val hwNum: UInt)

@Serializable
data class SubmitTaskRequest(val hwNum: UInt, val taskNum: UInt, val userAnswer: String? = null)

@Serializable
data class SubmitHomeworkRequest(val hwNum: UInt)

@Serializable
data class User(val username: String, val password: String, val role: String)

@Serializable
data class UsersResponse(val users: List<User>)