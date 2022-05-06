package repository

import cats.effect.IO
import domain.Meetup

trait MeetupRepo {
  def save(meetup: Meetup): IO[Unit]

  def update(meetup: Meetup): IO[Unit]

  def remove(id: Long): IO[Unit]

  def find(id: Long): IO[Option[Meetup]]
}
