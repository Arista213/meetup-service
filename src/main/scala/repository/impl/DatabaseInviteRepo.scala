package repository.impl

import cats.effect.{ExitCode, IO, Resource}
import domain.InviteStatus
import domain.InviteStatus.InviteStatus
import doobie.hikari.HikariTransactor
import doobie.implicits._
import repository.InviteRepo

case class DatabaseInviteRepo(hikariTransactor: Resource[IO, HikariTransactor[IO]]) extends InviteRepo {
  override def invite(userId: Long, meetupId: Long): IO[Unit] =
    hikariTransactor.use { transactor =>
      for {
        _ <-
          sql"""
            insert into invites (user_id, meetup_id, status)
            values ($userId, $meetupId, ${InviteStatus.Questionable.toString})
       """.update.run.transact(transactor)
      } yield ExitCode.Success
    }

  override def accept(userId: Long, meetupId: Long): IO[Unit] =
    hikariTransactor.use { transactor =>
      for {
        _ <-
          sql"""
            UPDATE invites
            SET status = ${InviteStatus.Accepted.toString}
            WHERE user_id = $userId AND meetup_id = $meetupId
       """.update.run.transact(transactor)
      } yield ExitCode.Success
    }

  override def getStatus(userId: Long, meetupId: Long): IO[InviteStatus] =
    hikariTransactor.use { transactor =>
      for {
        status <-
          sql"""
              SELECT status FROM invites
              WHERE user_id = $userId AND meetup_id = $meetupId
         """.query[String].unique.transact(transactor)
      } yield InviteStatus.parse(status)
    }

  override def cancel(userId: Long, meetupId: Long): IO[Unit] =
    hikariTransactor.use { transactor =>
      for {
        _ <-
          sql"""
            DELETE FROM invites
            WHERE user_id = $userId AND meetup_id = $meetupId
       """.update.run.transact(transactor)
      } yield ExitCode.Success
    }

  override def getAllUsersId(meetupId: Long): IO[List[Long]] =
    hikariTransactor.use { transactor =>
      for {
        users <-
          sql"""
              SELECT user_id FROM invites
              WHERE  meetup_id = $meetupId
         """.query[Long].to[List].transact(transactor)
      } yield users
    }
}
