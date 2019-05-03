package chutien.it

import com.google.gson.annotations.SerializedName
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

            }
            get("/test") {

                val data = mutableListOf<ForexItem>()
                data.add(ForexItem("EUR/GBP",1.6924,999,true))
                data.add(ForexItem("USD/GBP",0.9824,111,false))
                data.add(ForexItem("CHF/GBP",0.6815,222,false))
                data.add (ForexItem("EUR/GBP", 1.6924, 999, true))
                data.add(ForexItem("USD/GBP", 0.9824, 111, false))
                data.add(ForexItem("CHF/GBP", 0.6815, 222, false))
                val response = Response(1,1,1,data)

                call.respond(response)
            }
        }
    }
    server.start(wait = true)
}

data class ForexItem(
    val name: String,
    val price: Double,
    val time: Long,
    val isUpTrend: Boolean
)

data class Response(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("results") val results: MutableList<ForexItem>? = null
)
