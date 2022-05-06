package repository.impl

import cats.effect.IO.asyncForIO
import cats.effect.{ExitCode, IO, Resource}
import domain.Meetup
import doobie.hikari.HikariTransactor
import doobie.implicits._
import repository.MeetupRepo

case class DatabaseMeetupRepo(hikariTransactor: Resource[IO, HikariTransactor[IO]]) extends MeetupRepo {
  override def save(meetup: Meetup): IO[Unit] =
    hikariTransactor.use { transactor =>
      for {
        _ <-
          sql"""
            insert into meetups (id, name, start, "end")
            values (${meetup.id}, ${meetup.name}, ${meetup.start}, ${meetup.end})
       """.update.run.transact(transactor)
      } yield ExitCode.Success
    }

  override def update(meetup: Meetup): IO[Unit] =
    hikariTransactor.use {
      transactor =>
        for {
          _ <-
            sql"""
            UPDATE meetups
            SET name = ${meetup.name},
                start = ${meetup.start},
                "end" = ${meetup.end}
            WHERE id = ${meetup.id}
       """.update.run.transact(transactor)
        } yield ExitCode.Success
    }

  override def remove(id: Long): IO[Unit] =
    hikariTransactor.use {
      transactor =>
        for {
          _ <-
            sql"""
            DELETE FROM meetups
            WHERE id = $id
       """.update.run.transact(transactor)
        } yield ExitCode.Success
    }

  override def find(id: Long): IO[Option[Meetup]] =
    hikariTransactor.use {
      transactor =>
        for {
          meetup <-
            sql"""
              SELECT * FROM meetups
              WHERE id = $id
         """.query[Meetup].unique.transact(transactor)
        } yield Option(meetup)
    }
}
