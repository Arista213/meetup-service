package service.impl

import cats.effect.unsafe.implicits.global
import domain.InviteStatus.InviteStatus
import domain.User
import repository.InviteRepo
import service.InviteService

class InviteServiceImpl(inviteRepo: InviteRepo) extends InviteService {
  override def inviteUser(userId: Long, meetupId: Long): Unit =
    inviteRepo.invite(userId, meetupId).unsafeRunSync()

  override def accept(userId: Long, meetupId: Long): Unit =
    inviteRepo.accept(userId, meetupId).unsafeRunSync()

  override def cancel(userId: Long, meetupId: Long): Unit =
    inviteRepo.cancel(userId, meetupId).unsafeRunSync()

  override def getAllUsers(meetupId: Long): List[Long] =
    inviteRepo.getAllUsersId(meetupId).unsafeRunSync()

  override def getStatus(userId: Long, meetupId: Long): InviteStatus =
    inviteRepo.getStatus(userId, meetupId).unsafeRunSync()
}
