package endpoint

import domain.User
import sttp.model.StatusCode
import sttp.tapir.generic.auto.schemaForCaseClass
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.{AnyEndpoint, Endpoint, endpoint, path, statusCode, stringBody}

object UserEndpoints {

  val userCreateEndpoint: Endpoint[Unit, User, Unit, Unit, Any] =
    endpoint.post
      .in("users")
      .in(jsonBody[User])
      .out(statusCode(StatusCode.Created))

  val userUpdateEndpoint: Endpoint[Unit, User, Unit, Unit, Any] =
    endpoint.patch
      .in("users")
      .in(jsonBody[User])
      .out(statusCode(StatusCode.Ok))

  val userRemoveEndpoint: Endpoint[Unit, Long, String, Unit, Any] =
    endpoint.delete
      .in("user")
      .in(path[Long]("id"))
      .errorOut(stringBody)
      .out(statusCode(StatusCode.Ok))

  val userGetEndpoint: Endpoint[Unit, Long, String, User, Any] =
    endpoint.get
      .in("user")
      .in(path[Long]("id"))
      .out(jsonBody[User])
      .out(statusCode(StatusCode.Ok))
      .errorOut(stringBody)

  private val userEndpoints: List[AnyEndpoint] = List(userCreateEndpoint, userUpdateEndpoint, userGetEndpoint, userRemoveEndpoint)

  def getAllEndpoints: List[AnyEndpoint] = userEndpoints
}
