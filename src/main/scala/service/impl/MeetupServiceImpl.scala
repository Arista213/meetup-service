package service.impl

import cats.effect.unsafe.implicits.global
import domain.Meetup
import org.joda.time.{DateTime, LocalDateTime}
import repository.MeetupRepo
import service.MeetupService

case class MeetupServiceImpl(meetupRepo: MeetupRepo) extends MeetupService {
  var autoIncrementCounter = 1

  override def save(meetup: Meetup): Unit =
    meetupRepo.save(meetup).unsafeRunSync()

  override def update(meetup: Meetup): Unit =
    meetupRepo.update(meetup).unsafeRunSync()

  override def remove(id: Long): Unit =
    meetupRepo.remove(id).unsafeRunSync()

  override def find(id: Long): Option[Meetup] =
    meetupRepo.find(id).unsafeRunSync()
}
