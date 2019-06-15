package boot

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.pathPrefix
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import controllers.ResearchPapersController

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Server extends App with ResearchPapersController {

  implicit override val actorSystem: ActorSystem = ActorSystem("AkkaHTTPResearchPapers")
  implicit val materializer = ActorMaterializer()

  lazy val apiRoutes: Route = pathPrefix("api") {
    researchPaperRoutes
  }

  Http().bindAndHandle(apiRoutes, "localhost", 8080)
  logger.info("Starting the HTTP server at 8080")
  Await.result(actorSystem.whenTerminated, Duration.Inf)
}
