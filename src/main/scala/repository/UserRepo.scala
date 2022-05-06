package repository

import cats.effect.IO
import domain.User

trait UserRepo {
  def save(user: User): IO[Unit]

  def update(user: User): IO[Unit]

  def remove(id: Long): IO[Boolean]

  def find(id: Long): IO[Option[User]]
}
