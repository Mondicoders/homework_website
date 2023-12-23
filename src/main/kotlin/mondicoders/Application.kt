package mondicoders

import com.sun.security.auth.UserPrincipal
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.auth.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import mondicoders.plugins.*
import org.slf4j.event.*

fun main(args: Array<String>): Unit = Config.main(args);

fun defaultJsonSettings() = Json {
    encodeDefaults = true
    isLenient = true
    allowSpecialFloatingPointValues = true
}

private fun Application.setupKtorPlugins() {
    install(DefaultHeaders)
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    install(AutoHeadResponse)
    install(IgnoreTrailingSlash)
    install(ContentNegotiation) {
        json(defaultJsonSettings())
    }
    val hashedUserTable = UserHashedTableAuth(
        table = getAuthUsers(),
        digester = digestFunction
    )

    install(Authentication) {
        basic("auth-basic") {
            realm = "Access to the '/api/admin' path"
            validate { credentials ->
                hashedUserTable.authenticate(credentials)
            }
        }
    }
}

val digestFunction = getDigestFunction("SHA-256") { "ktor${it.length}" }
fun getAuthUsers(): Map<String, ByteArray> {
    val users = getUsers()
    val auth: MutableMap<String, ByteArray> = mutableMapOf()
    for (user in users?.users!!) {
        auth[user.username] = digestFunction(user.password)
    }
    return auth
}

@Suppress("unused")
fun Application.module() {
    setupKtorPlugins()
    routing {
        get {

        }

        route("/api") {
            route("/admin") {
                setupAdminRouting()
            }
            route("/user") {
                setupUserRouting()
            }
        }
    }
}
