package endpoint

import domain.Meetup
import sttp.model.StatusCode
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.{AnyEndpoint, Endpoint, endpoint, path, statusCode, stringBody}

object MeetupEndpoints {

  val meetupCreateEndpoint: Endpoint[Unit, Meetup, Unit, Unit, Any] =
    endpoint.post
      .in("meetups")
      .in(jsonBody[Meetup](Meetup.meetupEncoder, Meetup.meetupDecoder, Meetup.sMeetup))
      .out(statusCode(StatusCode.Created))

  val meetupUpdateEndpoint: Endpoint[Unit, Meetup, Unit, Unit, Any] =
    endpoint.patch
      .in("meetups")
      .in(jsonBody[Meetup](Meetup.meetupEncoder, Meetup.meetupDecoder, Meetup.sMeetup))
      .out(statusCode(StatusCode.Ok))

  val meetupRemoveEndpoint: Endpoint[Unit, Long, Unit, Unit, Any] =
    endpoint.delete
      .in("meetup")
      .in(path[Long]("id"))
      .out(statusCode(StatusCode.Ok))

  val meetupGetEndpoint: Endpoint[Unit, Long, String, Meetup, Any] =
    endpoint.get
      .in("meetup")
      .in(path[Long]("id"))
      .out(jsonBody[Meetup](Meetup.meetupEncoder, Meetup.meetupDecoder, Meetup.sMeetup))
      .out(statusCode(StatusCode.Ok))
      .errorOut(stringBody)

  private val meetupsEndpoints: List[AnyEndpoint] = List(meetupCreateEndpoint, meetupUpdateEndpoint, meetupGetEndpoint, meetupRemoveEndpoint)

  def getAllEndpoints: List[AnyEndpoint] = meetupsEndpoints
}
