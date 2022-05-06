package domain

import io.circe.generic.decoding.DerivedDecoder
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder, HCursor, Json}

object UserStatus extends Enumeration {
  type UserStatus = Value
  val Busy, Free = Value
}

case class User(id: Long, fullname: Fullname, email: Email)

case class Fullname(value: String)

case class Email(value: String)

object User {
  implicit val encodeUser: Encoder[User] = (user: User) => Json.obj(
    ("id", Json.fromLong(user.id)),
    ("fullname", Json.fromString(user.fullname.value)),
    ("email", Json.fromString(user.email.value))
  )

  implicit val decodeUser: Decoder[User] = (userJson: HCursor) => for {
    id <- userJson.downField("id").as[Long]
    fullname <- userJson.downField("fullname").as[Fullname]
    email <- userJson.downField("email").as[Email]
  } yield {
    User(id, fullname, email)
  }

  implicit val fullnameDecoder: Decoder[Fullname] = deriveDecoder[Fullname]
  implicit val fullnameEncoder: Encoder[Fullname] = deriveEncoder[Fullname]

  implicit val emailDecoder: Decoder[Email] = deriveDecoder[Email]
  implicit val emailEncoder: Encoder[Email] = deriveEncoder[Email]
}
