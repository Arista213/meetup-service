package repository

import cats.effect.IO
import domain.InviteStatus.InviteStatus

trait InviteRepo {
  def invite(userId: Long, meetupId: Long): IO[Unit]

  def accept(userId: Long, meetupId: Long): IO[Unit]

  def cancel(userId: Long, meetupId: Long): IO[Unit]

  def getStatus(userId: Long, meetupId: Long): IO[InviteStatus]

  def getAllUsersId(meetupId: Long): IO[List[Long]]
}
