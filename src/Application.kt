package chutien.it

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    val port = System.getenv("PORT")?.toInt() ?: 23567
    val server = embeddedServer(Netty, port) {
        install(StatusPages) {
            exception<Throwable> { e ->
                call.respondText(
                    e.localizedMessage,
                    ContentType.Text.Plain, HttpStatusCode.InternalServerError
                )
            }
        }
        install(ContentNegotiation) {
            gson()

        }
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(Application::class.java.classLoader, "templates")
        }

        routing {
            get("/data") {
                val data = Person("Tien", 25)
                call.respond(
                    FreeMarkerContent(
                        "index.ftl",
                        mapOf("data" to data), "e"
                    )
                )
            }
            post("/test") {
                val request = call.receive<Request>()
                call.respond(request)
            }
        }
    }
    server.start(wait = true)
}

data class Request(
    val token: String
)

data class Person(val name: String, val age: Int)
