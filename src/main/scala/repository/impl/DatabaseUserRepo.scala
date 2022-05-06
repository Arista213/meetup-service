package repository.impl

import cats.effect.{ExitCode, IO, Resource}
import domain.User
import doobie.hikari.HikariTransactor
import doobie.implicits._
import repository.UserRepo

case class DatabaseUserRepo(hikariTransactor: Resource[IO, HikariTransactor[IO]]) extends UserRepo {
  override def save(user: User): IO[Unit] =
    hikariTransactor.use { transactor =>
      for {
        _ <-
          sql"""
            insert into users (id, fullname, email)
            values (${user.id}, ${user.fullname.value}, ${user.email.value})
       """.update.run.transact(transactor)
      } yield ExitCode.Success
    }


  override def update(user: User): IO[Unit] = hikariTransactor.use {
    transactor =>
      for {
        _ <-
          sql"""
            UPDATE users
            SET fullname = ${user.fullname},
                email = ${user.email.value}
            WHERE id = ${user.id}
       """.update.run.transact(transactor)
      } yield ExitCode.Success
  }

  override def remove(id: Long): IO[Boolean] = hikariTransactor.use {
    transactor =>
      for {
        rowsModified <-
          sql"""
            DELETE FROM users
            WHERE id = $id
       """.update.run.transact(transactor)
        result = if (rowsModified != 0) true else false
      } yield result
  }

  override def find(id: Long): IO[Option[User]] = hikariTransactor.use {
    transactor =>
      for {
        user <-
          sql"""
            SELECT * FROM users
            WHERE id = $id
       """.query[User].unique.transact(transactor)
      } yield Option(user)
  }
}
