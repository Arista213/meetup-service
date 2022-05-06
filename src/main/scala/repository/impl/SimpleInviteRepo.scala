package repository.impl

import cats.effect.IO
import domain.InviteStatus.InviteStatus
import domain.{Meetup, User}
import repository.InviteRepo
import service.MeetupService

case class SimpleInviteRepo(meetupService: MeetupService) extends InviteRepo {
  var usersOnMeetupMap: Map[Long, Meetup] = Map.empty

  override def invite(userId: Long, meetupId: Long): IO[Unit] = ???

  override def accept(userId: Long, meetupId: Long): IO[Unit] = ???

  override def cancel(userId: Long, meetupId: Long): IO[Unit] = ???

  override def getStatus(userId: Long, meetupId: Long): IO[InviteStatus] = ???

  override def getAllUsersId(meetupId: Long): IO[List[Long]] = ???
}
