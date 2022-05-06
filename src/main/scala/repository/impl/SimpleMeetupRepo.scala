package repository.impl

import cats.effect.IO
import domain.Meetup
import repository.MeetupRepo

import scala.collection.immutable.HashMap

class SimpleMeetupRepo extends MeetupRepo {
  var meetups: Map[Long, Meetup] = new HashMap[Long, Meetup]()

  override def save(meetup: Meetup): IO[Unit] = IO.pure(meetups += ((meetup.id, meetup)))

  override def update(meetup: Meetup): IO[Unit] = IO.pure((meetups = meetups.updated(meetup.id, meetup)))

  override def remove(id: Long): IO[Unit] = IO.pure(meetups -= id)

  override def find(id: Long): IO[Option[Meetup]] = IO.pure(meetups.get(id))
}
