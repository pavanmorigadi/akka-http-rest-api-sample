package controllers

import java.util.Date

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import authorization.Authorization
import spray.json.DefaultJsonProtocol
import storage.ResearchPapersStorage
import storage.ResearchPapersStorage.{ResearchPaperNotFoundException, Search}

import scala.util.{Failure, Success}

trait ResearchPapersController extends ResearchPapersStorage with SprayJsonSupport with DefaultJsonProtocol {
  implicit def actorSystem: ActorSystem

  /**
    * Logging using the actor system.
    */
  lazy val logger = Logging(actorSystem, classOf[ResearchPapersController])

  /**
    * Research Papers Routes for the GET/POST/Other REST endpoints.
    */
  lazy val researchPaperRoutes: Route = extractRequest { request =>

    if (Authorization.isAuthorized(request).equals(false)) {
      complete(StatusCodes.Unauthorized, "provide valid user credentials")
    }
    else {
      pathPrefix("research-papers") {
        //GET-ALL
        pathEndOrSingleSlash {
          get {
            complete(getAllResearchPapers())
          }
        } ~
          //GET BY ID
          path(Segment) {
            id: String =>
              get {
                onComplete(getResearchPaperById(id)) {
                  _ match {
                    case Success(paper) =>
                      logger.info(s"Got the research paper record for id(title) ${id}")
                      complete(StatusCodes.OK, paper)

                    case Failure(throwable) =>
                      logger.error(s"Failed to get the research paper record for id(title) ${id}")
                      throwable match {
                        case e: ResearchPaperNotFoundException => complete(StatusCodes.NotFound, "No research paper found")
                        case _ => complete(StatusCodes.InternalServerError, "Failed to get the research paper.")
                      }
                  }
                }
              }
          } ~
          path("search") {
            post {
              entity(as[Search]) {
                searchObj =>
                  complete(searchResearchPapers(searchObj.title, searchObj.author, searchObj.startDate, searchObj.endDate, searchObj.containsText))
              }
            }
          }
      }
    }
  }
}
