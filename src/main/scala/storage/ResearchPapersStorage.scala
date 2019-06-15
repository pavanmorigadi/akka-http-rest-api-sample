package storage

import java.util.Date

import akka.actor.ActorSystem
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future

object ResearchPapersStorage extends DefaultJsonProtocol {
  implicit val researchPaperJsonFormat = jsonFormat3(ResearchPaper)
  implicit val searchJsonFormat = jsonFormat5(Search)

  //Dummy: list of research papers
  val researchPapersDb = Seq(
    ResearchPaper("Oracledemo1", List("author1", "author2"), "2017/01/02"),
    ResearchPaper("Oracledemo2", List("author3", "author4"), "2017/02/04"),
    ResearchPaper("researchpaper3", List("author5"), "20/06/2019"),
    ResearchPaper("researchpaper4", List("author6"), "25/06/2019")
  )

  case class ResearchPaper(title: String, authors: List[String], publication_date: String)

  case class Search(title: Option[String], author: Option[String], startDate: Option[String], endDate: Option[String], containsText: Option[String])

  class ResearchPaperNotFoundException extends Throwable("No such research paper found in the database.")
}

trait ResearchPapersStorage {

  import ResearchPapersStorage._

  val actorSystem = ActorSystem("StorageContext")
  implicit lazy val executionContext = actorSystem.dispatcher

  //Considering title as unique identifier
  def getResearchPaperById(title: String): Future[ResearchPaper] = {
    Future {
      val data = researchPapersDb.filter(_.title == title)
      if (data.isEmpty)
        throw new ResearchPaperNotFoundException
      else
        data(0)
    }
  }

  def getAllResearchPapers():Future[Seq[ResearchPaper]] =
  {
   Future {
    researchPapersDb
    }
  }

  def searchResearchPapers(title: Option[String], author: Option[String], startDate: Option[String], endDate: Option[String], containsText: Option[String]) : Future[Seq[ResearchPaper]] =
  {
    Future {
      //contains logic to search based on search criteria
      //convert Date
      //returning dummy value for now
      researchPapersDb
    }
  }
}
