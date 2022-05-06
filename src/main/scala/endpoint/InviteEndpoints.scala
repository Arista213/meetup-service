package endpoint

import domain.InviteRequest
import sttp.model.StatusCode
import sttp.tapir.generic.auto.schemaForCaseClass
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.{AnyEndpoint, Endpoint, endpoint, statusCode}

object InviteEndpoints {
  val inviteEndpoint: Endpoint[Unit, InviteRequest, Unit, Unit, Any] =
    endpoint.post
      .in("invites")
      .in(jsonBody[InviteRequest])
      .out(statusCode(StatusCode.Created))

  val acceptEndpoint: Endpoint[Unit, InviteRequest, Unit, Unit, Any] =
    endpoint.patch
      .in("invites").in("accept")
      .in(jsonBody[InviteRequest])
      .out(statusCode(StatusCode.Ok))

  val cancelEndpoint: Endpoint[Unit, InviteRequest, Unit, Unit, Any] =
    endpoint.delete
      .in("invites")
      .in(jsonBody[InviteRequest])
      .out(statusCode(StatusCode.Ok))

  private val inviteEndpoints: List[AnyEndpoint] = List(inviteEndpoint, acceptEndpoint, cancelEndpoint)

  def getAllEndpoints: List[AnyEndpoint] = inviteEndpoints
}
