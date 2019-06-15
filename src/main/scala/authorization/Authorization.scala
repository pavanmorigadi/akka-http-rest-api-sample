package authorization

import akka.http.scaladsl.model.HttpRequest

object Authorization {

  def isAuthorized(request: HttpRequest): Boolean =
  {
    //handle authorization logic
    true
  }
}
