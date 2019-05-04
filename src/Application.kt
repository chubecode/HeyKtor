package chutien.it

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.reactivex.Flowable
import kotlinx.coroutines.reactive.awaitLast
import response.ForexListResponse
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val port = System.getenv("PORT")?.toInt() ?: 9254
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
            get("/") {
                val result = Flowable.range(1, 10)
                    .map { it * it }
                    .delay(300L, TimeUnit.MILLISECONDS)
                    .awaitLast()

                call.respondText("LAST ITEM: $result")
            }
            get("/test") {

                val client = HttpClient(OkHttp) {
                    engine {
                        config { // this: OkHttpClient.Builder ->
                            // ...
                            followRedirects(true)
                            // ...
                        }


                    }
                }

                val response =  client.get<ForexListResponse>("http://data.fixer.io/api/latest?access_key=33b7a261560056619bb1fb1bcf653b8b")

//
//                val data = mutableListOf<ForexItem>()
//                data.add(ForexItem("EUR/GBP",1.6924,999,true))
//                data.add(ForexItem("USD/GBP",0.9824,111,false))
//                data.add(ForexItem("CHF/GBP",0.6815,222,false))
//                data.add (ForexItem("EUR/GBP", 1.6924, 999, true))
//                data.add(ForexItem("USD/GBP", 0.9824, 111, false))
//                data.add(ForexItem("CHF/GBP", 0.6815, 222, false))
//                val response = ForexListResponse(true, 1, "USD", "2019",data)
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


