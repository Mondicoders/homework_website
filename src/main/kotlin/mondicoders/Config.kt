package mondicoders

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.MordantHelpFormatter
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.path

object Config : CliktCommand(name = "java -jar homework_web.jar", printHelpOnEmptyArgs = false) {
    val dataDirectory by option(
        "-d", "--data-directory",
        help = "Path to data directory"
    ).path(mustExist = true, canBeFile = false, canBeDir = true).required()

    private val port: Int by option("-p", "--port", help = "Port for listen").int().default(8080)

    private val ktorArgs by option("--ktor-args", help = "Arguments to forward to ktor server").multiple()

    val usersPath by option("--users", help = "JSON file with users credentials")
        .path(mustExist = true, canBeFile = true, canBeDir = false).required()

    override fun run() {
        io.ktor.server.netty.EngineMain.main((listOf("-port=$port") + ktorArgs).toTypedArray())
    }

    init {
        context {
            helpFormatter = { MordantHelpFormatter(it, showRequiredTag = true, showDefaultValues = true) }
        }
    }
}

val config: Config get() = Config