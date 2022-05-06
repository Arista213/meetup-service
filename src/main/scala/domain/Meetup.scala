package domain

import io.circe.{Decoder, Encoder, HCursor, Json}
import sttp.tapir.Schema

import java.time.format.DateTimeFormatter

case class Meetup(id: Long, name: String, start: String, end: String)

object Meetup {
  implicit val meetupEncoder: Encoder[Meetup] = (meetup: Meetup) => Json.obj(
    ("id", Json.fromLong(meetup.id)),
    ("name", Json.fromString(meetup.name)),
    ("start", Json.fromString(meetup.start)),
    ("end", Json.fromString(meetup.end))
  )

  implicit val meetupDecoder: Decoder[Meetup] = (meetupJson: HCursor) => for {
    id <- meetupJson.downField("id").as[Long]
    name <- meetupJson.downField("name").as[String]
    start <- meetupJson.downField("start").as[String]
    end <- meetupJson.downField("end").as[String]
  } yield {
    Meetup(id, name, start, end)
  }

  implicit lazy val sMeetup: Schema[Meetup] = Schema.derived
}


object LocalDateTimeObject {

  import java.time.LocalDateTime

  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

  def stringToDateTime(string: String): LocalDateTime = LocalDateTime.parse(string, formatter)

  def dateTimeToString(dateTime: LocalDateTime): String = dateTime.format(formatter)

  implicit val dateTimeEncoder: Encoder[LocalDateTime] = (dateTime: LocalDateTime) => Json.obj {
    ("time", Json.fromString(dateTimeToString(dateTime)))
  }

  implicit val dateTimeDecoder: Decoder[LocalDateTime] = (dateTimeJson: HCursor) => for {
    string <- dateTimeJson.downField("time").as[String]
  } yield stringToDateTime(string)
}
