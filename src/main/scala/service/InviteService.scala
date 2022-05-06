package service

import domain.InviteStatus.InviteStatus

trait InviteService {
  def inviteUser(userId: Long, meetupId: Long): Unit

  def accept(userId: Long, meetupId: Long): Unit

  def cancel(userId: Long, meetupId: Long): Unit

  def getAllUsers(meetupId: Long): List[Long]

  def getStatus(userId: Long, meetupId: Long): InviteStatus
}
