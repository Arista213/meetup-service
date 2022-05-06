package domain

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class InviteRequest(userId: Long, meetupId: Long)

object InviteRequest {
  implicit val inviteRequestDecoder: Decoder[InviteRequest] = deriveDecoder[InviteRequest]
  implicit val inviteRequestEncoder: Encoder[InviteRequest] = deriveEncoder[InviteRequest]
}
